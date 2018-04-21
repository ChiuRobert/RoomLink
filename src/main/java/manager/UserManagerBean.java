package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.User;

@ManagedBean(name="userManagerBean")
@SessionScoped
public class UserManagerBean implements Serializable {

	private static final long serialVersionUID = -2546690392764947202L;
	
	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
	
	public List<User> getAllUsers() {
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
	
	public User findByName(String userName) {
		TypedQuery<User> query = entityManager.createNamedQuery("User_findByName", User.class);
		query.setParameter("userName", userName);
		
		List<User> result = query.getResultList();
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public void updatePassword(int id, String password) {
		entityManager.getTransaction().begin();
		
		Query query = entityManager.createNamedQuery("User_updatePassword", User.class);
		query.setParameter("id", id);
		query.setParameter("password", password);
		
		query.executeUpdate();

		User user = getById(id);
		entityManager.merge(user);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
	}
	
	public User getById(int id) {
		TypedQuery<User> query = entityManager.createNamedQuery("User_getById", User.class);
		query.setParameter("id", id);
		
		List<User> result = query.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public void save(User user) {
		entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
	}
	
	public void remove(User user) {
		entityManager.getTransaction().begin();
		entityManager.remove(user);
		entityManager.getTransaction().commit();
	}
}
