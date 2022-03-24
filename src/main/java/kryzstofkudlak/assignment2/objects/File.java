package kryzstofkudlak.assignment2.objects;

import kryzstofkudlak.assignment2.services.UserService;

public class File {

	User owner;
	String name = "";
	Group associatedGroup = new Group("nil"); // optional
	String ownerAccess  = "rw-"; // maybe change these defaults? // rwx order
	String groupAccess  = "---";
	String globalAccess = "---"; 
	
	public File(String name) {
		this.owner = UserService.getActiveUser();
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public User getOwner() {
		return this.owner;
	}
	
	public Group getGroup() {
		return this.associatedGroup;
	}
	
	public String getOwnerAccess() {
		return this.ownerAccess;
	}
	
	public String getGroupAccess() {
		return this.groupAccess;
	}
	
	public String getGlobalAccess() {
		return this.globalAccess;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public boolean isOwnedBy(User user) {
		return user.equals(this.owner);
	}
	
	// set permissions
	public void setOwnerAccess(String access) {
		this.ownerAccess = access;
	}
	
	public void setGroupAccess(String access) {
		this.groupAccess = access;
	}
	
	public void setGlobalAccess(String access) {
		this.globalAccess = access;
	}
	
	// group ownership mgmt
	public void setGroup(Group group) {
		this.associatedGroup = group;
	}
	
	public void clearGroup(Group group) {
		this.associatedGroup = new Group("nil");
	}
	

	
	// access ctrl ops
	public boolean canRead(User user) {
		return canAccess(0, user);
	}
	
	public boolean canWrite(User user) {
		return canAccess(1, user);
	}
	
	public boolean canExecute(User user) {
		return canAccess(2, user);
	}
	
	private boolean canAccess(int index, User user) {
		if (this.isOwnedBy(user) && !this.ownerAccess.substring(index, index+1).equals("-")) {
			return true;
		}
		if (user.isInGroup(this.associatedGroup) && !this.groupAccess.substring(index, index+1).equals("-")) {
			return true;
		}
		return !this.globalAccess.substring(index, index+1).equals("-");
	}
	
}
