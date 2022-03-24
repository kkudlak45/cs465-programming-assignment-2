package kryzstofkudlak.assignment2.objects;

import java.util.HashSet;
import java.util.Set;

public class User {

	private String name;
	private String password;
	private Set<Group> groups;
	private boolean isPrivileged = false;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password; // TODO: persist to accounts.txt
		this.groups = new HashSet<>();
	}
	
	// username/password
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	
	// handle group interaction
	public void addToGroup(Group group) {
		this.groups.add(group);
	}
	
	public void removeFromGroup(Group group) {
		this.groups.remove(group);
	}
	
	public boolean isInGroup(Group group) {
		return this.groups.contains(group);
	}
	
	public Set<Group> getGroups() {
		return this.groups;
	}
	
	
	// accomplish sudo
	public void givePrivilege() {
		this.isPrivileged = true;
	}
	
	public void revokePrivilege() {
		this.isPrivileged = false;
	}
	
	public boolean isPrivileged() {
		return this.isPrivileged;
	}
	
}
