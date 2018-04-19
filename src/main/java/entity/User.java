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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User_getAllUsers", query="SELECT user FROM User user"),
	@NamedQuery(name="User_getById", query="SELECT user FROM User user WHERE user.id= :id"),
	@NamedQuery(name="User_updatePassword", query="UPDATE User user SET user.password= :password WHERE user.id= :id")
})
public class User implements Serializable{

	private static final long serialVersionUID = 4014462898967273136L;
	
	private int id;
	private String userName;
	private String password;
	private boolean admin;
	private List<Booking> userRoomList = new ArrayList<Booking>();
	
	public User() { }
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
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
	
	@Column(name = "userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "admin")
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Booking> getUserRoomList() {
		return userRoomList;
	}

	public void setUserRoomList(List<Booking> userRoomList) {
		this.userRoomList = userRoomList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userRoomList == null) ? 0 : userRoomList.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (admin != other.admin)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userRoomList == null) {
			if (other.userRoomList != null)
				return false;
		} else if (!userRoomList.equals(other.userRoomList))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", admin=" + admin + "]";
	}
}
