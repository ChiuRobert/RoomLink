package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import entity.Booking;
import entity.Building;
import entity.Bundle;
import entity.Room;
import entity.User;
import manager.BookingManagerBean;
import manager.BuildingManagerBean;
import manager.BundleManagerBean;
import manager.RoomManagerBean;

@ManagedBean
@SessionScoped
public class BookingBean implements Serializable {

	private static final long serialVersionUID = 7446247500769082051L;

	private Date day;
	private Date currentDate;
	private int startHour;
	private int endHour;

	private int roomStartHour;
	private int roomEndHour;

	private List<String> bundleList = new ArrayList<String>();
	private String selectedBundle;

	private List<String> buildingsList = new ArrayList<String>();
	private String selectedBuilding;

	private List<Room> roomsList = new ArrayList<Room>();
	private Room selectedRoom;

	private User user;

	private boolean roomIsSelected;

	@ManagedProperty(value = "#{bundleManagerBean}")
	private BundleManagerBean bundleManagerBean;

	@ManagedProperty(value = "#{buildingManagerBean}")
	private BuildingManagerBean buildingManagerBean;

	@ManagedProperty(value = "#{roomManagerBean}")
	private RoomManagerBean roomManagerBean;

	@ManagedProperty(value = "#{bookingManagerBean}")
	private BookingManagerBean bookingManagerBean;

	@ManagedProperty(value = "#{indexBean}")
	private IndexBean indexBean;

	@PostConstruct
	public void init() {
		user = indexBean.getUser();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);	
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		currentDate = calendar.getTime();
	}

	private void clearSelected() {
		roomsList = new ArrayList<Room>();
		bundleList = new ArrayList<String>();
		buildingsList = new ArrayList<String>();
		selectedBundle = null;
		selectedBuilding = null;
		selectedRoom = null;
	}

	public List<Booking> getAllBookings() {
		clearSelected();

		user = indexBean.getUser();

		List<Bundle> tempBundleList = bundleManagerBean.getAllBundles();
		for (Bundle bundle : tempBundleList) {
			bundleList.add(bundle.toString());
		}

		List<Building> tempBuildingList = buildingManagerBean.getAllBuildings();
		for (Building building : tempBuildingList) {
			buildingsList.add(building.toString());
		}

		List<Booking> temp = new ArrayList<Booking>();
		if (user.isAdmin()) {
			temp = bookingManagerBean.getAllBookings();
		} else {
			temp =  bookingManagerBean.getUserBookings(user);
		}
		
		return temp;
	}

	public void addLaterBooking() {
		if (selectedBundle == null) {
			showMessage("You need to choose an asset bundle.");
			return;
		}
		if (selectedBuilding == null) {
			showMessage("You need to choose a building.");
			return;
		}
		if (selectedRoom == null) {
			showMessage("You need to choose a room.");
			return;
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('modal').show();");
	}

	public void book() {
	    if (day == null) {
	    	showMessage("Please choose a day.");
	    	return;
	    }
	    if (day.before(currentDate)) {
	    	showMessage("Please choose a valid day.");
	    	return;
	    }
	    if (roomStartHour >= roomEndHour) {
	    	showMessage("Please choose valid dates.");
	    	return;
	    }
	    
		Calendar startCalendar = Calendar.getInstance(); 
	    startCalendar.setTime(day); 
	    startCalendar.add(Calendar.HOUR_OF_DAY, roomStartHour); 
	    Date start = startCalendar.getTime(); 
		
		Calendar endCalendar = Calendar.getInstance(); 
	    endCalendar.setTime(day);
	    endCalendar.add(Calendar.HOUR_OF_DAY, roomEndHour);
	    Date end = endCalendar.getTime(); 
	    
	    Calendar dayCalendar = Calendar.getInstance();
	    dayCalendar.setTime(day);
	    
	    List<Booking> bookings = bookingManagerBean.getByRoom(selectedRoom);    	
	    if (bookings != null) {
		    for (Booking book : bookings) {
		    	Calendar startBooking = Calendar.getInstance();
		    	startBooking.setTime(book.getStartDate());
		    	
		    	Calendar endBooking = Calendar.getInstance();
		    	endBooking.setTime(book.getEndDate());
		    	
		    	if (startBooking.get(Calendar.DAY_OF_YEAR) != dayCalendar.get(Calendar.DAY_OF_YEAR)) {
		    		continue;
		    	}
			    if (endBooking.get(Calendar.DAY_OF_YEAR) != dayCalendar.get(Calendar.DAY_OF_YEAR)) {
			    	continue;
		    	}
		    	
			    if ((book.getStartDate().before(start) || book.getStartDate().equals(start))
			    		&& (book.getEndDate().after(end) || book.getEndDate().equals(end))) {
			    	showMessage(String.format("The room is already booked in the time interval: %d - %d.", startBooking.get(Calendar.HOUR_OF_DAY), endBooking.get(Calendar.HOUR_OF_DAY)));
			    	return;
	    		}
		    	if (start.before(book.getStartDate()) 
		    			&& end.after(book.getStartDate())) {
			    	showMessage(String.format("The room is already booked in the time interval: %d - %d.", startBooking.get(Calendar.HOUR_OF_DAY), endBooking.get(Calendar.HOUR_OF_DAY)));
		    		return;
		    	}
		    	if ((book.getStartDate().before(start) || book.getStartDate().equals(start))
		    			&& book.getEndDate().after(start)) {
			    	showMessage(String.format("The room is already booked in the time interval: %d - %d.", startBooking.get(Calendar.HOUR_OF_DAY), endBooking.get(Calendar.HOUR_OF_DAY)));
		    		return;
		    	}
		    } 
	    }
	    
	    Booking booking = new Booking(user,selectedRoom,start,end);
	    bookingManagerBean.save(booking);
	    
		showMessage("Room booked successfully.");
	}

	public void showRooms() {
		if (selectedBundle == null) {
			showMessage("You need to choose an asset bundle.");
			return;
		}
		if (selectedBuilding == null) {
			showMessage("You need to choose a building.");
			return;
		}

		roomsList = new ArrayList<Room>();
		selectedRoom = null;

		Building building = buildingManagerBean.findByName(selectedBuilding);
		Bundle bundle = bundleManagerBean
				.getById(Integer.parseInt(selectedBundle.substring(0, selectedBundle.indexOf(' '))));

		List<Room> allRooms = roomManagerBean.findByBuildingBundle(building, bundle);

		if (allRooms != null) {
			roomsList = allRooms;
		} else {
			showMessage("No room meets the requirements.");
			return;
		}
	}

	public String deleteBooking(Booking booking) {
		try {
			bookingManagerBean.remove(booking);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return "bookingList.xhtml?faces-redirect=true";
	}

	private void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage("bookingBeanForm:idInput", new FacesMessage(message));
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public List<String> getBundleList() {
		return bundleList;
	}

	public void setBundleList(List<String> bundleList) {
		this.bundleList = bundleList;
	}

	public String getSelectedBundle() {
		return selectedBundle;
	}

	public void setSelectedBundle(String selectedBundle) {
		this.selectedBundle = selectedBundle;
	}

	public List<String> getBuildingsList() {
		return buildingsList;
	}

	public void setBuildingsList(List<String> buildingsList) {
		this.buildingsList = buildingsList;
	}

	public String getSelectedBuilding() {
		return selectedBuilding;
	}

	public void setSelectedBuilding(String selectedBuilding) {
		this.selectedBuilding = selectedBuilding;
	}

	public List<Room> getRoomsList() {
		return roomsList;
	}

	public void setRoomsList(List<Room> roomsList) {
		this.roomsList = roomsList;
	}

	public Room getSelectedRoom() {
		return selectedRoom;
	}

	public void setSelectedRoom(Room selectedRoom) {
		this.selectedRoom = selectedRoom;
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

	public IndexBean getIndexBean() {
		return indexBean;
	}

	public void setIndexBean(IndexBean indexBean) {
		this.indexBean = indexBean;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BookingManagerBean getBookingManagerBean() {
		return bookingManagerBean;
	}

	public void setBookingManagerBean(BookingManagerBean bookingManagerBean) {
		this.bookingManagerBean = bookingManagerBean;
	}

	public int getRoomStartHour() {
		return roomStartHour;
	}

	public void setRoomStartHour(int roomStartHour) {
		this.roomStartHour = roomStartHour;
	}

	public int getRoomEndHour() {
		return roomEndHour;
	}

	public void setRoomEndHour(int roomEndHour) {
		this.roomEndHour = roomEndHour;
	}

	public boolean getRoomIsSelected() {
		return roomIsSelected;
	}

	public void setRoomIsSelected(boolean roomIsSelected) {
		this.roomIsSelected = roomIsSelected;
	}
}
