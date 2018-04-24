package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entity.Asset;
import manager.AssetManagerBean;

@ManagedBean
@SessionScoped
public class AssetBean implements Serializable{

	private static final long serialVersionUID = -1944861435384696664L;

	private Asset asset;
	
	@ManagedProperty(value = "#{assetManagerBean}")
	private AssetManagerBean assetManagerBean;
	
	@PostConstruct
	public void init() {
		asset = new Asset();
	}
	
	public List<Asset> getAllAssets() {
		return assetManagerBean.getAllAssets();		
	}

	public String addAsset() {
		assetManagerBean.save(asset);
		
		return "assetList.xhtml?faces-redirect=true";	
	}

	public String deleteAsset(Asset asset) {	
		try {
			assetManagerBean.remove(asset);
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return "assetList.xhtml?faces-redirect=true";
	}

	public String editAssetDetailsById(Asset asset) {
		this.asset = asset;
		return "assetEdit.xhtml";
	}

	public String updateAssetDetails() {
		FacesContext.getCurrentInstance().addMessage("editAssetForm:idInput", new FacesMessage("The asset has been succesfully updated"));
		assetManagerBean.UpdateName(asset.getId(), asset.getName());
		
		return "assetEdit.xhtml";
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public AssetManagerBean getAssetManagerBean() {
		return assetManagerBean;
	}

	public void setAssetManagerBean(AssetManagerBean assetManagerBean) {
		this.assetManagerBean = assetManagerBean;
	}
}
