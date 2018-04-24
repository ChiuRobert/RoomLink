package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="booking")
@NamedQueries({
	@NamedQuery(name="Booking_getAllBookings", query="SELECT booking FROM Booking booking"),
	@NamedQuery(name="Booking_getUserBookings", query="SELECT booking FROM Booking booking WHERE booking.user= :user"),
	@NamedQuery(name="Booking_getByBundleBuilding", query="SELECT booking FROM Booking booking WHERE booking.room.building= :building AND booking.room.bundle= :bundle"),
	@NamedQuery(name="Booking_getByDate", query="SELECT booking FROM Booking booking WHERE booking.startDate>= :startDate AND booking.endDate<= :endDate"),
	@NamedQuery(name="Booking_getByRoom", query="SELECT booking FROM Booking booking WHERE booking.room= :room")
})
public class Booking implements Serializable{

	private static final long serialVersionUID = 1612790064226038851L;

	private int id;
	private User user;
	private Room room;
	private Date startDate;
	private Date endDate;
	
	public Booking() { }
	
    public Booking(User user, Room room, Date startDate, Date endDate) {
		this.user = user;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
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

    @ManyToOne
    @JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
    @ManyToOne
    @JoinColumn(name = "roomId")
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	
	@Column(name = "startDate")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "endDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Booking other = (Booking) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Booking [user=" + user + ", room=" + room + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
