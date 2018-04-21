package bean;

import java.io.Serializable;

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
public class IndexBean implements Serializable{

	private static final long serialVersionUID = 5245102214017080288L;

	@ManagedProperty(value = "#{userManagerBean}")
	private UserManagerBean userManagerBean;
	
	private String userName;
	private String password;
	
	private boolean isAdmin;
	private boolean isUser;
	private boolean loggedIn;
	
	@PostConstruct
	public void init() {
		isAdmin = false;
		isUser = false;
		loggedIn = false;
	}
	
	public void checkUser() {
		User user = userManagerBean.findByName(userName);
		
		if (user == null) {
			showMessage("Incorrect user name.");
			logOut();
		} else {
			if (!user.getPassword().equals(password)) {
				showMessage("Password is incorrect.");
				logOut();
			} else {
				if (user.isAdmin()) {
					isAdmin = true;
					isUser = false;
					showMessage("Login successfully as admin.");
				} else {
					isAdmin = false;
					isUser = true;
					showMessage("Login successfully.");
				}
				loggedIn = true;
				userName = user.getPassword();
			}
		}
	}
	
	public void signOut() {
		logOut();
		userName = null;
		password = null;
		showMessage("Signed out successfully.");
	}
	
	private void logOut() {
		isAdmin = false;
		isUser = false;
		loggedIn = false;
	}

	public void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage("indexForm:idInput", new FacesMessage(message));
	}
	
	public UserManagerBean getUserManagerBean() {
		return userManagerBean;
	}

	public void setUserManagerBean(UserManagerBean userManagerBean) {
		this.userManagerBean = userManagerBean;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getIsUser() {
		return isUser;
	}

	public void setIsUser(boolean isUser) {
		this.isUser = isUser;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
