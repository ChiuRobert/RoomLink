package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entity.Building;
import manager.BuildingManagerBean;

@ManagedBean
@SessionScoped
public class BuildingBean implements Serializable{

	private static final long serialVersionUID = -8996765940364501163L;

	private Building building;
	
	@ManagedProperty(value = "#{buildingManagerBean}")
	private BuildingManagerBean buildingManagerBean;
	
	@PostConstruct
	public void init() {
		building = new Building();
	}
	
	public List<Building> getAllBuildings() {
		return buildingManagerBean.getAllBuildings();		
	}

	public String addBuilding() {
		buildingManagerBean.save(building);
		
		return "buildingList.xhtml?faces-redirect=true";	
	}

	public String deleteBuilding(Building building) {	
		try {
			buildingManagerBean.remove(building);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return "buildingList.xhtml?faces-redirect=true";
	}

	public String editBuildingDetailsById(Building building) {
		this.building = building;
		return "buildingEdit.xhtml";
	}

	public String updateBuildingDetails() {
		FacesContext.getCurrentInstance().addMessage("editBuildingForm:idInput", new FacesMessage("The building has been succesfully updated"));
		buildingManagerBean.UpdateName(building.getId(), building.getName());
		
		return "buildingEdit.xhtml";
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public BuildingManagerBean getBuildingManagerBean() {
		return buildingManagerBean;
	}

	public void setBuildingManagerBean(BuildingManagerBean buildingManagerBean) {
		this.buildingManagerBean = buildingManagerBean;
	}
}
