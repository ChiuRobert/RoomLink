package manager;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Bundle;

@ManagedBean(name="bundleManagerBean")
public class BundleManagerBean implements Serializable {

	private static final long serialVersionUID = 2780587959696799231L;
	
	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
	
	public List<Bundle> getAllBundles() {
		TypedQuery<Bundle> query = entityManager.createNamedQuery("Bundle_getAllBundles", Bundle.class);
		
		List<Bundle> result = query.getResultList();
		
		for (Bundle bundle : result) {
			entityManager.refresh(bundle);
		}
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result;
	}
	
	public void save(Bundle bundle) {
		entityManager.getTransaction().begin();
        entityManager.persist(bundle);
        entityManager.getTransaction().commit();
	}
	
	public void remove(Bundle bundle)  {
		entityManager.getTransaction().begin();
		entityManager.remove(bundle);
		entityManager.getTransaction().commit();
	}	
}
