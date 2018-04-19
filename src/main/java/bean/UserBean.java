package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import entity.User;
import manager.UserManagerBean;

@ManagedBean
@ViewScoped
public class UserBean implements Serializable{

	private static final long serialVersionUID = 4101854810116731756L;
	
	private User user;
	
	@PostConstruct
	public void init() {
		user = new User();
	}

    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
 
	public List<User> getAllUsers() {
		return UserManagerBean.getAllUsers();		
	}

	public String addUser() {
		UserManagerBean.save(user);
		
		return "usersList.xhtml?faces-redirect=true";	
	}

	public String deleteUser(User user) {		
		UserManagerBean.remove(user);
		
		return "usersList.xhtml?faces-redirect=true";
	}

	// Navigate user to the edit details page and pass selected user 
	public String editUserDetailsById(User user) {
		this.user = user;
		return "userEdit.xhtml";
	}

	public String updateUserDetails() {
		FacesContext.getCurrentInstance().addMessage("editUserForm:idInput", new FacesMessage("The password has been succesfully updated"));
		return UserManagerBean.updatePassword(user.getId(), user.getPassword());		
	}
}