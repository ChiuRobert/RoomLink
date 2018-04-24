package manager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Booking;
import entity.Building;
import entity.Bundle;
import entity.Room;
import entity.User;

@ManagedBean(name="bookingManagerBean")
@SessionScoped
public class BookingManagerBean implements Serializable{

	private static final long serialVersionUID = 998868594933774968L;

	private final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

	public List<Booking> getAllBookings() {
		TypedQuery<Booking> query = entityManager.createNamedQuery("Booking_getAllBookings", Booking.class);
		
		List<Booking> result = query.getResultList();
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}

	public List<Booking> getUserBookings(User user) {
		TypedQuery<Booking> query = entityManager.createNamedQuery("Booking_getUserBookings", Booking.class);
		query.setParameter("user", user);
		
		List<Booking> result = query.getResultList();	
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public List<Booking> getByRoom(Room room) {
		TypedQuery<Booking> query = entityManager.createNamedQuery("Booking_getByRoom", Booking.class);
		query.setParameter("room", room);
		
		List<Booking> result = query.getResultList();	
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public List<Booking> getByBundleBuilding(Building building, Bundle bundle) {
		TypedQuery<Booking> query = entityManager.createNamedQuery("Booking_getByBundleBuilding", Booking.class);
		query.setParameter("building", building);
		query.setParameter("bundle", bundle);
		
		List<Booking> result = query.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public List<Booking> getByDate(Date startDate, Date endDate) {
		TypedQuery<Booking> query = entityManager.createNamedQuery("Booking_getByDate", Booking.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		
		List<Booking> result = query.getResultList();

		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public void save(Booking booking) {
		entityManager.getTransaction().begin();
        entityManager.persist(booking);
        entityManager.getTransaction().commit();
	}
	
	public void merge(Booking booking) {
		entityManager.getTransaction().begin();
        entityManager.merge(booking);
        entityManager.getTransaction().commit();	
	}
	
	public void remove(Booking booking)  {
		entityManager.getTransaction().begin();
		entityManager.remove(booking);
		entityManager.getTransaction().commit();
	}
}
