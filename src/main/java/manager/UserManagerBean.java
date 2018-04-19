package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.User;

@ManagedBean(name="userManagerBean")
public class UserManagerBean implements Serializable {

	private static final long serialVersionUID = -2546690392764947202L;
	
	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
	
	public static List<User> getAllUsers() {
		TypedQuery<User> query = entityManager.createNamedQuery("User_getAllUsers", User.class);
		
		List<User> result = query.getResultList();
		
		for(User user : result) {
			entityManager.refresh(user);
		}
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public static String updatePassword(int id, String password) {
		entityManager.getTransaction().begin();
		
		Query query = entityManager.createNamedQuery("User_updatePassword", User.class);
		query.setParameter("id", id);
		query.setParameter("password", password);
		
		query.executeUpdate();

		User user = getById(id);
		entityManager.merge(user);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
        
		return "userEdit.xhtml";
	}
	
	public static User getById(int id) {
		TypedQuery<User> query = entityManager.createNamedQuery("User_getById", User.class);
		query.setParameter("id", id);
		
		List<User> result = query.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public static void save(User user) {
		entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
	}
	
	public static void remove(User user) {
		entityManager.getTransaction().begin();
		entityManager.remove(user);
		entityManager.getTransaction().commit();
	}
}
