package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="bundle")
public class Bundle implements Serializable{

	private static final long serialVersionUID = 2762114681087257980L;

	private int id;	
	private List<Asset> assetList = new ArrayList<Asset>();
	
	public Bundle() { }
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToMany(mappedBy = "bundleList")
	public List<Asset> getAssetList() {
		return assetList;
	}
	public void setAssetList(List<Asset> assetList) {
		this.assetList = assetList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assetList == null) ? 0 : assetList.hashCode());
		result = prime * result + id;		
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
		Bundle other = (Bundle) obj;
		if (assetList == null) {
			if (other.assetList != null)
				return false;
		} else if (!assetList.equals(other.assetList))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String temp = "";
		for(Asset asset : assetList) {
			temp += asset.getName();
			temp += " | ";
		}
		return "Bundle [id=" + id + "Asset: " + temp + "]";
	}
}
