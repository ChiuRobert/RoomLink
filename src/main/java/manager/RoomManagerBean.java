package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Bundle;
import entity.Room;

@ManagedBean(name="roomManagerBean")
public class RoomManagerBean implements Serializable{

	private static final long serialVersionUID = 3643421975790627267L;
	
	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
	
	public static List<Room> getAllRooms() {
		TypedQuery<Room> query = entityManager.createNamedQuery("Room_getAllRooms", Room.class);
		
		List<Room> result = query.getResultList();
		
		for(Room user : result) {
			entityManager.refresh(user);
		}
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public static String UpdateNumber(int id, String number) {
		entityManager.getTransaction().begin();
		
		TypedQuery<Room> query = entityManager.createNamedQuery("Room_pdateNumber", Room.class);
		query.setParameter("id", id);
		query.setParameter("number", number);
		
		query.executeUpdate();

		Room room = getById(id);
		entityManager.merge(room);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
        
		return "roomEdit.xhtml";
	}
	
	public static String UpdateBundle(int id, Bundle bundle) {
		entityManager.getTransaction().begin();
		
		TypedQuery<Room> query = entityManager.createNamedQuery("Room_updateBundle", Room.class);
		query.setParameter("id", id);
		query.setParameter("bundle", bundle);
		
		query.executeUpdate();

		Room room = getById(id);
		entityManager.merge(room);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
        
		return "roomEdit.xhtml";
	}
	
	public static Room getById(int id) {
		TypedQuery<Room> query = entityManager.createNamedQuery("Room_getById", Room.class);
		query.setParameter("id", id);
		
		List<Room> result = query.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public static void save(Room room) {
		entityManager.getTransaction().begin();
        entityManager.persist(room);
        entityManager.getTransaction().commit();
	}
	
	public static void remove(Room room)  {
		entityManager.getTransaction().begin();
		entityManager.remove(room);
		entityManager.getTransaction().commit();
	}
}