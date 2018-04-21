package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Asset;

@ManagedBean(name="assetManagerBean")
@SessionScoped
public class AssetManagerBean implements Serializable{

	private static final long serialVersionUID = 6749096133416010689L;

	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

	public List<Asset> getAllAssets() {
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_getAllAssets", Asset.class);
		
		List<Asset> result = query.getResultList();
		
		for (Asset asset : result) {
			entityManager.refresh(asset);
		}
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public Asset findByName(String name) {
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_findByName", Asset.class);
		query.setParameter("name", name);
		
		List<Asset> result = query.getResultList();
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public void UpdateName(int id, String name) {
		entityManager.getTransaction().begin();
		
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_updateName", Asset.class);
		query.setParameter("id", id);
		query.setParameter("name", name);
		
		query.executeUpdate();

		Asset asset = getById(id);
		entityManager.merge(asset);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
	}
	
	public Asset getById(int id) {
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_getById", Asset.class);
		query.setParameter("id", id);
		
		List<Asset> result = query.getResultList();
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public void save(Asset asset) {
		entityManager.getTransaction().begin();
        entityManager.persist(asset);
        entityManager.getTransaction().commit();
	}
	
	public void merge(Asset asset) {
		entityManager.getTransaction().begin();
        entityManager.merge(asset);
        entityManager.getTransaction().commit();	
	}
	
	public void remove(Asset asset)  {
		entityManager.getTransaction().begin();
		entityManager.remove(asset);
		entityManager.getTransaction().commit();
	}
}
