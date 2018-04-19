package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="building")
@NamedQueries({
	@NamedQuery(name="Building_getAllBuilding", query="SELECT building FROM Building building"),
	@NamedQuery(name="Building_getById", query="SELECT building FROM Building building WHERE building.id= :id"),
	@NamedQuery(name="Building_updateName", query="UPDATE Building building SET building.name= :name WHERE building.id= :id")
})
public class Building implements Serializable{

	private static final long serialVersionUID = 415825379363922566L;

	private int id;
	private String name;
	private List<Room> roomList;
	
	public Building() { }
	
	public Building(String name) {
		this.name = name;
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

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="building", orphanRemoval = true)
    @JoinColumn(name = "bulding")
	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;		
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Building other = (Building) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Building [name=" + name + "]";
	}
}
