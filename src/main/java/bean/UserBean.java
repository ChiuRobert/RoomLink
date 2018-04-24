package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entity.User;
import manager.UserManagerBean;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable{

	private static final long serialVersionUID = 4101854810116731756L;
	
	private User user;
	
	@ManagedProperty(value = "#{userManagerBean}")
	private UserManagerBean userManagerBean;
	
	private String userNameString;
	private String passwordString;
	
	@PostConstruct
	public void init() {
		user = new User();
	}
 
	public List<User> getAllUsers() {
		return userManagerBean.getAllUsers();		
	}

	public String addUser() {	
		user = new User();
		user.setPassword(passwordString);
		user.setUserName(userNameString);
		
		userManagerBean.save(user);
		showMessage("User added successfully.");
		
		return "usersList.xhtml?faces-redirect=true";	
	}

	public String deleteUser(User user) {		
		try {
			userManagerBean.remove(user);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return "usersList.xhtml?faces-redirect=true";
	}

	public String editUserDetailsById(User user) {
		this.user = user;
		userNameString = user.getUserName();
		passwordString = user.getPassword();
		
		return "userEdit.xhtml";
	}

	public String updateUserDetails() {		
		boolean canUpdate = true;
			
		if (passwordString != null) {
			userManagerBean.updatePassword(user.getId(), passwordString);
		} else {
			showMessage("You need to choose a password.");
			canUpdate = false;
		}
		
		if (canUpdate) {
			showMessage("The user has been successfully updated.");
		}		

		userNameString = null;
		passwordString = null;
		
		return "usersList.xhtml?faces-redirect=true";
	}
	
	public void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage("editUserForm:idInput", new FacesMessage(message));
	}

    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
	
	public UserManagerBean getUserManagerBean() {
		return userManagerBean;
	}

	public void setUserManagerBean(UserManagerBean userManagerBean) {
		this.userManagerBean = userManagerBean;
	}

	public String getUserNameString() {
		return userNameString;
	}

	public void setUserNameString(String userNameString) {
		this.userNameString = userNameString;
	}

	public String getPasswordString() {
		return passwordString;
	}

	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}
}