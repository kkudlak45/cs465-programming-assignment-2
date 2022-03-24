package kryzstofkudlak.assignment2.services;

import java.util.LinkedList;
import java.util.List;

import kryzstofkudlak.assignment2.objects.User;

public class UserService {
	
	private static User activeUser = null;
	private static List<User> users = new LinkedList<>();
	
	
	public static void resetFieldsForTest() {
		activeUser = null;
		users = new LinkedList<>();
	}
	
	
	// public utility methods
	
	/**
	 * Returns a user object if it exists, null otherwise
	 * 
	 * @param username - name of user to search for
	 * @return
	 */
	public static User getUser(String username) {
		for (User user : users) {
			if (user.getName().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public static List<User> getUsers() {
		return users;
	}
	
	public static User getActiveUser() {
		return activeUser;
	}
	
	public static boolean activeUserExists() {
		return activeUser != null;
	}
	
	public static boolean activeUserIsRoot() {
		return (activeUser != null && activeUser.isPrivileged());
	}
	
	public static void addUser(User user) {
		users.add(user);
	}
	
	public static void setActiveUser(User user) {
		activeUser = user;
	}
	
}
