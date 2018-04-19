package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Asset;

@ManagedBean(name="assetManagerBean")
@ViewScoped
public class AssetManagerBean implements Serializable{

	private static final long serialVersionUID = 6749096133416010689L;

	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

	public static List<Asset> getAllBuildings() {
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_getAllAssets", Asset.class);
		
		List<Asset> result = query.getResultList();
		
		for(Asset asset : result) {
			entityManager.refresh(asset);
		}
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public static String UpdateName(int id, String name) {
		entityManager.getTransaction().begin();
		
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_updateName", Asset.class);
		query.setParameter("id", id);
		query.setParameter("name", name);
		
		query.executeUpdate();

		Asset asset = getById(id);
		entityManager.merge(asset);
		entityManager.flush();
		
        entityManager.getTransaction().commit();
        
		return "roomEdit.xhtml";
	}
	
	public static Asset getById(int id) {
		TypedQuery<Asset> query = entityManager.createNamedQuery("Asset_getById", Asset.class);
		query.setParameter("id", id);
		
		List<Asset> result = query.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
	public static void save(Asset asset) {
		entityManager.getTransaction().begin();
        entityManager.persist(asset);
        entityManager.getTransaction().commit();
	}
	
	public static void remove(Asset asset)  {
		entityManager.getTransaction().begin();
		entityManager.remove(asset);
		entityManager.getTransaction().commit();
	}
}
