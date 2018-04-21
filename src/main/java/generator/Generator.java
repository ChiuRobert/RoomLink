package generator;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import entity.Asset;
import entity.Booking;
import entity.Building;
import entity.Bundle;
import entity.Room;
import entity.User;

public class Generator {

	private static final String PERSISTENCE_UNIT_NAME = "roomlink";	
	private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
	
	private static void createConnection(Asset asset, Bundle bundle) {
		bundle.addAsset(asset); 
		//asset.addBundle(bundle); 
	}
	
	private static void save(Object o) {
        entityManager.persist(o);
	}
	
	public static void main(String[] args) {
		// <-- Begin transaction --> \\
		entityManager.getTransaction().begin();
			
		// <-- Create Bundles --> \\S
		Bundle b1 = new Bundle();
		Bundle b2 = new Bundle();
		Bundle b3 = new Bundle();

		// <-- Create Assets --> \\
		Asset a1 = new Asset("video");
		Asset a2 = new Asset("audio");
		Asset a3 = new Asset("chairs");
		Asset a4 = new Asset("table");
		Asset a5 = new Asset("Scotch");
		Asset a6 = new Asset("Board");
		
		// <-- Connect bundles and assets --> \\
		createConnection(a1, b1);
		createConnection(a1, b2);
		createConnection(a1, b3);
		createConnection(a2, b1);
		createConnection(a3, b2);
		createConnection(a4, b1);
		createConnection(a4, b2);
		createConnection(a5, b2);
		createConnection(a5, b3);
		createConnection(a6, b1);
		createConnection(a6, b2);
		createConnection(a6, b3);
		
		// <-- Create users --> \\
		User u1 = new User("u1", "p1");
		User u2 = new User("u2", "p2");
		User u3 = new User("u3", "p3");

		// <-- Create buildings --> \\
		Building bd1 = new Building("A");
		Building bd2 = new Building("B");
		
		// <-- Create rooms --> \\
		Room r1 = new Room("1", bd1);
		Room r2 = new Room("2", bd1);
		Room r3 = new Room("3", bd2);
		
		// <-- Set Bundles for rooms --> \\
		r1.setBundle(b1);
		r2.setBundle(b2);
		r3.setBundle(b3);
		
		// <-- Create start dates --> \\
		Date sd1 = new Date(2018, 4, 16, 10, 00);
		Date sd2 = new Date(2018, 4, 15, 18, 00);
		Date sd3 = new Date(2018, 4, 18, 10, 00);
		Date sd4 = new Date(2018, 5, 1, 12, 00);
		Date sd5 = new Date(2018, 4, 16, 14, 00);
		Date sd6 = new Date(2018, 4, 21, 16, 00);
		
		// <-- Create end dates --> \\
		Date ed1 = new Date(2018, 4, 16, 11, 00);
		Date ed2 = new Date(2018, 4, 15, 20, 00);
		Date ed3 = new Date(2018, 4, 18, 11, 00);
		Date ed4 = new Date(2018, 5, 1, 15, 00);
		Date ed5 = new Date(2018, 4, 16, 20, 00);
		Date ed6 = new Date(2018, 4, 21, 29, 00);
		
		// <-- Create Booking (each one is the date the user booked that room) --> \\
		Booking ur1 = new Booking(u1, r1, sd1, ed1);
		Booking ur2 = new Booking(u2, r2, sd2, ed2);
		Booking ur3 = new Booking(u3, r3, sd3, ed3);
		Booking ur4 = new Booking(u1, r2, sd4, ed4);
		Booking ur5 = new Booking(u2, r3, sd5, ed5);
		Booking ur6 = new Booking(u3, r1, sd6, ed6);
		
		// <-- Save in db --> \\
		save(b1);
		save(b2);
		save(b3);

		save(a1);
		save(a2);
		save(a3);
		save(a4);
		save(a5);
		save(a6);

		save(u1);
		save(u2);
		save(u3);
		
		save(bd1);
		save(bd2);

		save(r1);
		save(r2);
		save(r3);

		save(ur1);
		save(ur2);
		save(ur3);
		save(ur4);
		save(ur5);
		save(ur6);
		
		// <-- End transaction --> \\
        entityManager.getTransaction().commit();
	}
}
