package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import entity.Building;
import entity.Bundle;
import entity.Room;
import manager.BuildingManagerBean;
import manager.BundleManagerBean;
import manager.RoomManagerBean;

@ManagedBean
public class RoomBean implements Serializable{

	private static final long serialVersionUID = -5708091021375437920L;
	
	private Room room;
    
	@ManagedProperty(value = "#{bundleManagerBean}")
	private BundleManagerBean bundleManagerBean;
	
	@ManagedProperty(value = "#{buildingManagerBean}")
	private BuildingManagerBean buildingManagerBean;
	
    private List<Bundle> bundleList;
    private Bundle bundle;
    
    private List<Building> buildingList;
    private Building building;
    
	@PostConstruct
	public void init() {
		room = new Room();
		
		//FacesContext context = FacesContext.getCurrentInstance();
		//BundleManagerBean bundleManagerBean = context.getApplication().evaluateExpressionGet(context, "#{stuff}", BundleManagerBean.class);

		//bundleList = new ArrayList<Bundle>(bundleManagerBean.getAllBundles());
		//buildingList = new ArrayList<Building>(BuildingManagerBean.getAllBuildings());
	}
	
	public List<Room> getAllRooms() {
		return RoomManagerBean.getAllRooms();		
	}

	public String addRoom() {
		room.setBuilding(building);
		room.setBundle(bundle);
		RoomManagerBean.save(room);
		
		return "roomList.xhtml?faces-redirect=true";	
	}

	public String deleteRoom(Room room) {		
		RoomManagerBean.remove(room);
		
		return "roomList.xhtml?faces-redirect=true";
	}

	public String editRoomDetailsById(Room room) {
		this.room = room;
		return "roomEdit.xhtml";
	}

	public String updateRoomDetails() {
		FacesContext.getCurrentInstance().addMessage("editRoomForm:idInput", new FacesMessage("The room has been succesfully updated"));
		if (room.getBundle() != null) {
			return RoomManagerBean.UpdateBundle(room.getId(), room.getBundle());
		} else {
			return RoomManagerBean.UpdateNumber(room.getId(), room.getNumber());
		}
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Bundle> getBundleList() {
		bundleList = new ArrayList<Bundle>(bundleManagerBean.getAllBundles());
		return bundleList;
	}

	public void setBundleList(List<Bundle> bundleList) {
		this.bundleList = bundleList;
	}

	public List<Building> getBuildingList() {
		buildingList = new ArrayList<Building>(buildingManagerBean.getAllBuildings()); 
		return buildingList;
	}

	public void setBuildingList(List<Building> buildingList) {
		this.buildingList = buildingList;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
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
}
