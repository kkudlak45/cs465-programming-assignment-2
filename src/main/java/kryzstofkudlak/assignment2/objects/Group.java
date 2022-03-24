package kryzstofkudlak.assignment2.objects;

import java.util.HashSet;
import java.util.Set;

public class Group {

	private String name;
	private Set<User> usersInGroup; // can maybe get rid of this
	
	public Group(String name) {
		this.name = name;
		this.usersInGroup = new HashSet<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addUser(User userToAdd) {
		this.usersInGroup.add(userToAdd);
	}
	
	public boolean countainsUser(User userToCheck) {
		return this.usersInGroup.contains(userToCheck);
	}
	
	public Set<User> getUsers() {
		return this.usersInGroup;
	}
	
}
