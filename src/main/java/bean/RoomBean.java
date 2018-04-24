package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entity.Building;
import entity.Bundle;
import entity.Room;
import manager.BuildingManagerBean;
import manager.BundleManagerBean;
import manager.RoomManagerBean;

@ManagedBean
@SessionScoped
public class RoomBean implements Serializable{

	private static final long serialVersionUID = -5708091021375437920L;
	
	private Room room;
    
	@ManagedProperty(value = "#{bundleManagerBean}")
	private BundleManagerBean bundleManagerBean;
	
	@ManagedProperty(value = "#{buildingManagerBean}")
	private BuildingManagerBean buildingManagerBean;
	
	@ManagedProperty(value = "#{roomManagerBean}")
	private RoomManagerBean roomManagerBean;
	
    private List<String> bundleList = new ArrayList<String>();
    private String bundleString;
    
    private List<String> buildingList = new ArrayList<String>();
    private String buildingString;
    
    private String number;
    
	@PostConstruct
	public void init() {
		room = new Room();
	}
	
	public List<Room> getAllRooms() {
		return roomManagerBean.getAllRooms();		
	}

	public String addRoom() {
		boolean canAdd = true;
		Building building = null;
		Bundle bundle = null;
		
		try {
			if (buildingString == null) {
				throw new NullPointerException();
			}
			building = buildingManagerBean.findByName(buildingString);
		} catch (Exception e) {
			showMessage("You need to choose a building.");
			canAdd = false;
		}
		try {
			bundle = bundleManagerBean.getById(Integer.parseInt(bundleString.substring(0, bundleString.indexOf(' '))));
		} catch (Exception e) {
			showMessage("You need to choose a bundle.");
			canAdd = false;
		}
			
		if (canAdd) {
			room.setBuilding(building);
			room.setBundle(bundle);
			room.setNumber(number);
			
			roomManagerBean.save(room);
			
			showMessage("Room added successfully.");
		}
		
		return "roomList.xhtml?faces-redirect=true";	
	}
	
	public String deleteRoom(Room room) {		
		roomManagerBean.remove(room);
		
		return "roomList.xhtml?faces-redirect=true";
	}

	public String editRoomDetailsById(Room room) {
		this.room = room;
		buildingString = room.getBuilding().toString();
		bundleString = room.getBundle().toString();
		number = room.getNumber();
		
		return "roomEdit.xhtml";
	}

	public String updateRoomDetails() {
		boolean canUpdate = true;
		
		if (bundleString != null) {
			Bundle bundle = bundleManagerBean.getById(Integer.parseInt(bundleString.substring(0, bundleString.indexOf(' '))));
			roomManagerBean.UpdateBundle(room.getId(), bundle);
		} else {
			showMessage("You need to choose a bundle.");
			canUpdate = false;
		}
		
		if (number != null) {
			roomManagerBean.UpdateNumber(room.getId(), number);
		}
		
		if (canUpdate) {
			showMessage("The room has been successfully updated.");
		}
		return "roomEdit.xhtml";
	}
	
	public void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage("editRoomForm:idInput", new FacesMessage(message));
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<String> getBundleList() {
		bundleList = new ArrayList<String>();
		List<Bundle> bundleTempList = new ArrayList<Bundle>(bundleManagerBean.getAllBundles());
		
		for (Bundle temp : bundleTempList) {
				bundleList.add(temp.toString());
		}
		
		return bundleList;
	}

	public void setBundleList(List<String> bundleList) {
		this.bundleList = bundleList;
	}

	public List<String> getBuildingList() {
		buildingList = new ArrayList<String>();
		List<Building> buildingTempList = new ArrayList<Building>(buildingManagerBean.getAllBuildings());
		
		for (Building temp : buildingTempList) {
			buildingList.add(temp.toString());
		}
		
		return buildingList;
	}

	public void setBuildingList(List<String> buildingList) {
		this.buildingList = buildingList;
	}

	public String getBundle() {
		return bundleString;
	}

	public void setBundle(String bundle) {
		this.bundleString = bundle;
	}

	public String getBuilding() {
		return buildingString;
	}

	public void setBuilding(String building) {
		this.buildingString = building;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BundleManagerBean getBundleManagerBean() {
		return bundleManagerBean;
	}

	public void setBundleManagerBean(BundleManagerBean bundleManagerBean) {
		this.bundleManagerBean = bundleManagerBean;
	}

	public BuildingManagerBean getBuildingManagerBean() {
		return buildingManagerBean;
	}

	public void setBuildingManagerBean(BuildingManagerBean buildingManagerBean) {
		this.buildingManagerBean = buildingManagerBean;
	}

	public RoomManagerBean getRoomManagerBean() {
		return roomManagerBean;
	}

	public void setRoomManagerBean(RoomManagerBean roomManagerBean) {
		this.roomManagerBean = roomManagerBean;
	}
}
