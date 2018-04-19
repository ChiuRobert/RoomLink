package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import entity.Building;
import manager.BuildingManagerBean;

@ManagedBean
@ViewScoped
public class BuildingBean implements Serializable{

	private static final long serialVersionUID = -8996765940364501163L;

	private Building building;
	
	@PostConstruct
	public void init() {
		building = new Building();
	}
	
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public List<Building> getAllBuildings() {
		return null;//BuildingManagerBean.getAllBuildings();		
	}

	public String addBuilding() {
		BuildingManagerBean.save(building);
		
		return "buildingList.xhtml?faces-redirect=true";	
	}

	public String deleteBuilding(Building building) {		
		BuildingManagerBean.remove(building);
		
		return "buildingList.xhtml?faces-redirect=true";
	}

	public String editBuildingDetailsById(Building building) {
		this.building = building;
		return "buildingEdit.xhtml";
	}

	public String updateBuildingDetails() {
		FacesContext.getCurrentInstance().addMessage("editBuildingForm:idInput", new FacesMessage("The building has been succesfully updated"));
		return BuildingManagerBean.UpdateName(building.getId(), building.getName());
	}
}
