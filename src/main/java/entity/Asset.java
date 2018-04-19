package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="asset")
@NamedQueries({
	@NamedQuery(name="Asset_getAllAssets", query="SELECT asset FROM Asset asset"),
	@NamedQuery(name="Asset_getById", query="SELECT asset FROM Asset asset WHERE asset.id= :id"),
	@NamedQuery(name="Asset_updateName", query="UPDATE Asset asset SET asset.name= :name WHERE asset.id= :id")
})
public class Asset implements Serializable{

	private static final long serialVersionUID = -1479940881611392857L;
	
	private int id;
	private String name;
	private List<Bundle> bundleList = new ArrayList<Bundle>();
	
	public Asset() { }
	
	public Asset(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assetbundle", joinColumns = @JoinColumn(name = "assetId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "bundleId", referencedColumnName = "id"))
	public List<Bundle> getBundleList() {
		return bundleList;
	}
	public void setBundleList(List<Bundle> bundleList) {
		this.bundleList = bundleList;
	}
	
	public void addBundle(Bundle bundle) {
		bundleList.add(bundle);
		bundle.getAssetList().add(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundleList == null) ? 0 : bundleList.hashCode());
		result = prime * result + id;		
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		if (bundleList == null) {
			if (other.bundleList != null)
				return false;
		} else if (!bundleList.equals(other.bundleList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Asset [name=" + name + "]";
	}	
}
