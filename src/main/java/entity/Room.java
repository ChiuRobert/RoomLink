package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="room")
@NamedQueries({
	@NamedQuery(name="Room_getAllRooms", query="SELECT room FROM Room room"),
	@NamedQuery(name="Room_getById", query="SELECT user FROM User user WHERE user.id= :id"),
	@NamedQuery(name="Room_updateNumber", query="UPDATE Room room SET room.number= :number WHERE room.id= :id"),
	@NamedQuery(name="Room_updateBundle", query="UPDATE Room room SET room.bundle= :bundle WHERE room.id= :id")
})
public class Room implements Serializable{

	private static final long serialVersionUID = 7531827474304486574L;
	
	private int id;
	private String number;
	private Bundle bundle;
	private List<Booking> userRoomList = new ArrayList<Booking>();
	private Building building;
	
	public Room() { }
	
	public Room(String number, Building building) {
		this.number = number;
		this.building = building;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="number")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle")
	public Bundle getBundle() {
		return bundle;
	}
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
    @OneToMany(mappedBy = "room")
	public List<Booking> getUserRoomList() {
		return userRoomList;
	}
	public void setUserRoomList(List<Booking> userRoomList) {
		this.userRoomList = userRoomList;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)   
	@JoinColumn(name = "building")
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + id;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((userRoomList == null) ? 0 : userRoomList.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.equals(other.bundle))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (userRoomList == null) {
			if (other.userRoomList != null)
				return false;
		} else if (!userRoomList.equals(other.userRoomList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Room [number=" + number + ", bundle=" + bundle + "]";
	}
}
