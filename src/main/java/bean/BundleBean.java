package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entity.Asset;
import entity.Bundle;
import manager.AssetManagerBean;
import manager.BundleManagerBean;

@ManagedBean
@SessionScoped
public class BundleBean implements Serializable{

	private static final long serialVersionUID = 1274522873188716956L;

	private Bundle bundle;
	
	private List<String> assets;
	private List<String> selectedAssets;
	
	@ManagedProperty(value = "#{bundleManagerBean}")
	private BundleManagerBean bundleManagerBean;
	
	@ManagedProperty(value = "#{assetManagerBean}")
	private AssetManagerBean assetManagerBean;
	
	@PostConstruct
	public void init() {
		bundle = new Bundle();
		
		assets = new ArrayList<String>();
		selectedAssets = new ArrayList<String>();
		
		List<Asset> assetsList = new ArrayList<Asset>(assetManagerBean.getAllAssets());
		for (Asset asset : assetsList) {
			assets.add(asset.toString());
		}
	}
	
	public List<Bundle> getAllBundles() {
		return bundleManagerBean.getAllBundles();		
	}

	public String addBundle() {
		boolean canAdd = true;
		boolean canSave = true;
		
		bundle = new Bundle();
		List<Asset> assetsList = new ArrayList<Asset>();
		
		try {
			if (selectedAssets == null || selectedAssets.size() == 0) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			showMessage("You need to choose an asset.");
			canAdd = false;
			canSave = false;
		}
		
		if (canAdd) {
			for (String assetName : selectedAssets) {
				Asset asset = assetManagerBean.findByName(assetName);
				assetsList.add(asset);
				
				bundle.addAsset(asset);
			}
			
			try {
				bundleManagerBean.save(bundle);
				
				for (Asset asset : assetsList) {
					assetManagerBean.merge(asset);
				}
			} catch(StackOverflowError e) {
				canSave = true;
			} catch(Exception e) {
				showMessage("There was an error, please try again");
				canSave = false;
			}
		}
		
		if (canSave) {
			showMessage("Bundle added successfully.");
		}
		return "bundleList.xhtml?faces-redirect=true";	
	}
	
	public String deleteBundle(Bundle bundle) {		
		bundleManagerBean.remove(bundle);
		
		return "roomList.xhtml?faces-redirect=true";
	}

	public String editBundleDetailsById(Bundle bundle) {
		this.bundle = bundle;
		
		List<Asset> assetsList = bundle.getAssetList();
		
		for (Asset asset : assetsList) {
			selectedAssets.add(asset.toString());
		}
		
		return "bundleEdit.xhtml";
	}

	public String updateBundleDetails() {
		boolean canUpdate = true;
		
		if (selectedAssets != null || selectedAssets.size() != 0) {
			List<Asset> assetList = new ArrayList<Asset>(bundle.getAssetList());
			for (Asset asset : assetList) {
				bundle.removeAsset(asset);
			}
			bundleManagerBean.merge(bundle);
			for (Asset asset : assetList) {
				asset.removeBundle(bundle);
				assetManagerBean.merge(asset);
			}
			
			List<Asset> newAssets = new ArrayList<Asset>();
			for (String assetName : selectedAssets) {
				Asset asset = assetManagerBean.findByName(assetName);
				//assetList.add(asset);
				newAssets.add(asset);
			}
			for(Asset asset : newAssets) {
				bundle.addAsset(asset);
			}
			bundleManagerBean.merge(bundle);
			
			for (Asset asset : newAssets) {
				assetManagerBean.merge(asset);
			}
			
		} else {
			showMessage("You need to choose an asset.");
			canUpdate = false;
		}
		
		if (canUpdate) {
			showMessage("The bundle has been successfully updated.");
		}
		
		return "bundleEdit.xhtml";
	}
	
	public void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage("editBundleForm:idInput", new FacesMessage(message));
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public BundleManagerBean getBundleManagerBean() {
		return bundleManagerBean;
	}

	public void setBundleManagerBean(BundleManagerBean bundleManagerBean) {
		this.bundleManagerBean = bundleManagerBean;
	}

	public List<String> getAssets() {
		return assets;
	}

	public void setAssets(List<String> assets) {
		this.assets = assets;
	}

	public List<String> getSelectedAssets() {
		return selectedAssets;
	}

	public void setSelectedAssets(List<String> selectedAssets) {
		this.selectedAssets = selectedAssets;
	}

	public AssetManagerBean getAssetManagerBean() {
		return assetManagerBean;
	}

	public void setAssetManagerBean(AssetManagerBean assetManagerBean) {
		this.assetManagerBean = assetManagerBean;
	}
}
