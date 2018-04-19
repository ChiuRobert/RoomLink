package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Building;

@ManagedBean(name="buildingManagerBean")
public class BuildingManagerBean implements Serializable{

	private static final long serialVersionUID = -8910868015867550517L;
	
	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

	public static List<Building> getAllBuildings() {
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_getAllBuildings", Building.class);
		
		List<Building> result = query.getResultList();
		
		for(Building user : result) {
			entityManager.refresh(user);
		}
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public static String UpdateName(int id, String name) {
		entityManager.getTransaction().begin();
		
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_pdateName", Building.class);
		query.setParameter("id", id);
		query.setParameter("name", name);
		
		query.executeUpdate();

		Building building = getById(id);
		entityManager.merge(building);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
        
		return "roomEdit.xhtml";
	}
	
	public static Building getById(int id) {
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_getById", Building.class);
		query.setParameter("id", id);
		
		List<Building> result = query.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public static void save(Building building) {
		entityManager.getTransaction().begin();
        entityManager.persist(building);
        entityManager.getTransaction().commit();
	}
	
	public static void remove(Building building)  {
		entityManager.getTransaction().begin();
		entityManager.remove(building);
		entityManager.getTransaction().commit();
	}
}
