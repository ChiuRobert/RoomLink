package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import entity.Asset;
import manager.AssetManagerBean;

@ManagedBean
@ViewScoped
public class AssetBean implements Serializable{

	private static final long serialVersionUID = -1944861435384696664L;

	private Asset asset;
	
	@PostConstruct
	public void init() {
		asset = new Asset();
	}
	
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	public List<Asset> getAllAssets() {
		return AssetManagerBean.getAllBuildings();		
	}

	public String addAsset() {
		AssetManagerBean.save(asset);
		
		return "assetList.xhtml?faces-redirect=true";	
	}

	public String deleteAsset(Asset asset) {		
		AssetManagerBean.remove(asset);
		
		return "assetList.xhtml?faces-redirect=true";
	}

	public String editAssetDetailsById(Asset asset) {
		this.asset = asset;
		return "assetEdit.xhtml";
	}

	public String updateAssetDetails() {
		FacesContext.getCurrentInstance().addMessage("editAssetForm:idInput", new FacesMessage("The asset has been succesfully updated"));
		return AssetManagerBean.UpdateName(asset.getId(), asset.getName());
	}
}
