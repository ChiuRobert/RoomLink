package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Building;

@ManagedBean(name="buildingManagerBean")
@SessionScoped
public class BuildingManagerBean implements Serializable{

	private static final long serialVersionUID = -8910868015867550517L;
	
	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

	public List<Building> getAllBuildings() {
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_getAllBuildings", Building.class);
		
		List<Building> result = query.getResultList();
		
		for(Building building : result) {
			entityManager.refresh(building);
		}
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public Building findByName(String name) {
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_findByName", Building.class);
		query.setParameter("name", name);
		
		List<Building> result = query.getResultList();
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public void UpdateName(int id, String name) {
		entityManager.getTransaction().begin();
		
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_updateName", Building.class);
		query.setParameter("id", id);
		query.setParameter("name", name);
		
		query.executeUpdate();

		Building building = getById(id);
		entityManager.merge(building);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
	}
	
	public Building getById(int id) {
		TypedQuery<Building> query = entityManager.createNamedQuery("Building_getById", Building.class);
		query.setParameter("id", id);
		
		List<Building> result = query.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public void save(Building building) {
		entityManager.getTransaction().begin();
        entityManager.persist(building);
        entityManager.getTransaction().commit();
	}
	
	public void remove(Building building)  {
		entityManager.getTransaction().begin();
		entityManager.remove(building);
		entityManager.getTransaction().commit();
	}
}
