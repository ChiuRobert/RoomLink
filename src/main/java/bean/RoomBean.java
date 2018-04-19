package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import entity.Room;
import manager.RoomManagerBean;

@ManagedBean
public class RoomBean implements Serializable{

	private static final long serialVersionUID = -5708091021375437920L;
	
	private Room room;
	
	@PostConstruct
	public void init() {
		room = new Room();
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public List<Room> getAllRooms() {
		return RoomManagerBean.getAllRooms();		
	}

	public String addRoom() {
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
}
