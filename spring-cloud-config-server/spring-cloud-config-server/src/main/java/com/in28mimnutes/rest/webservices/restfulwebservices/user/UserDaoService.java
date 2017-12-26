package com.in28mimnutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;



import com.in28mimnutes.rest.webservices.restfulwebservices.user.User;;

// import com.minutes.learning.jpa.jpain10steps.entity.User;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	private static int usersCount = 3;
	
	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public long insert(User user) {
		
		if(user != null) {
			entityManager.persist(user);
			System.out.println("\n\nUserDAOService.insert() - USER Added");

		} else {
			System.out.println("\n\nUserDAOService.insert() - USER IS NULL - FAILED INSERT!");
		}
		
		return user.getId();
		
	}
	
	
	
	
	 public List<User> findAll() {
		 return users;
	 }
	 public User save(User user) {
		 if(user.getId() == null) {
			 user.setId(++usersCount);
		 }
		 users.add(user);
		 return user;
	 }
	 
	 public User findOne(int id) {
		for(User user:users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	 }
	
	 public User deleteById(int id) {
		 
	/**	 
			if(user != null) {
				entityManager.persist(user);
				System.out.println("\n\nUserDAOService.insert() - USER Added");

			} else {
				System.out.println("\n\nUserDAOService.insert() - USER IS NULL - FAILED INSERT!");
			}
	**/		
		 
		 
		 
		
		 Iterator<User> iterator = users.iterator();
		 
			while(iterator.hasNext()) {
				User user = iterator.next();
				 if(user.getId() == id) {
					 iterator.remove();
					 
System.out.println("UserDAOService.deleteById() - user deleted: " + user.toString() );			 
					 
					 return user;
				 }
			 }
			
		 return null;
	 }
	 
	 
}	// end of class
