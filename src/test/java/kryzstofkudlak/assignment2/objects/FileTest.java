package kryzstofkudlak.assignment2.objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class FileTest {

	File testFile;
	User user;
	Group group;
	
	@Before
	public void setup() {
		this.testFile = new File("test");
		this.user = new User("user", "password");
		this.group = new Group("group");
	}
	
	@Test
	public void userIsOwner_andOwnerReadEnabled() {
		this.testFile.setOwner(this.user);
		this.testFile.setOwnerAccess("r--");
		assertTrue(this.testFile.canRead(this.user));
	}
	
	@Test
	public void userNotOwner_butIsInGroup_andGroupReadEnabled() {
		this.user.addToGroup(this.group);;
		this.group.addUser(this.user);
		this.testFile.setGroup(this.group);
		this.testFile.setGroupAccess("r--");
		assertTrue(this.testFile.canRead(this.user));
	}
	
	@Test
	public void userNotOwner_norInGroup_butGlobalReadEnabled() {
		this.testFile.setGlobalAccess("r--");
		assertTrue(this.testFile.canRead(this.user));
	}
	
	@Test
	public void writeAccess_whenOwner() {
		this.testFile.setOwnerAccess("-w-");
		this.testFile.setOwner(this.user);
		assertTrue(this.testFile.canWrite(this.user));
	}
	
	@Test
	public void writeAccess_whenInGroup() {
		this.user.addToGroup(this.group);
		this.group.addUser(this.user);
		this.testFile.setGroup(this.group);
		this.testFile.setGroupAccess("-w-");
		assertTrue(this.testFile.canWrite(this.user));
	}
	
	@Test
	public void writeAccess_whenGlobal() {
		this.testFile.setGlobalAccess("-w-");
		assertTrue(this.testFile.canWrite(this.user));
	}
	
	@Test
	public void executeAccess_whenOwner() {
		this.testFile.setOwner(this.user);
		this.testFile.setOwnerAccess("--x");
		assertTrue(this.testFile.canExecute(this.user));
	}
	
	@Test
	public void executeAccess_whenInGroup() {
		this.user.addToGroup(this.group);
		this.group.addUser(this.user);
		this.testFile.setGroup(this.group);
		this.testFile.setGroupAccess("--x");
		assertTrue(this.testFile.canExecute(this.user));
	}
	
	@Test 
	public void executeAccess_whenGlobal() {
		this.testFile.setGlobalAccess("--x");
		assertTrue(this.testFile.canExecute(this.user));
	}
	
	@Test
	public void ownerAccessDenied_butGlobalAccessExists_thenAccept() {
		this.testFile.setOwnerAccess("---");
		this.testFile.setGlobalAccess("rwx");
		this.testFile.setOwner(this.user);
		
		assertTrue(this.testFile.canExecute(this.user));
		assertTrue(this.testFile.canRead(this.user));
		assertTrue(this.testFile.canWrite(this.user));
	}
	
	@Test
	public void groupAccess_andOwnerAccessDenied_butGlobalAccessExists_thenAccept() {
		this.testFile.setOwnerAccess("---");
		this.testFile.setGroupAccess("---");
		this.testFile.setGlobalAccess("rwx");
		
		this.user.addToGroup(this.group);
		this.group.addUser(this.user);
		this.testFile.setOwner(this.user);
		this.testFile.setGroup(this.group);
		
		assertTrue(this.testFile.canExecute(this.user));
		assertTrue(this.testFile.canRead(this.user));
		assertTrue(this.testFile.canWrite(this.user));
	}
	
	@Test
	public void ownerAndGlobalDenied_butGroupExists_thenAccept() {
		this.testFile.setOwnerAccess("---");
		this.testFile.setGroupAccess("rwx");
		this.testFile.setGlobalAccess("---");
		
		this.user.addToGroup(this.group);
		this.group.addUser(this.user);
		this.testFile.setOwner(this.user);
		this.testFile.setGroup(this.group);
		
		assertTrue(this.testFile.canExecute(this.user));
		assertTrue(this.testFile.canRead(this.user));
		assertTrue(this.testFile.canWrite(this.user));
	}
	
	@Test
	public void userInEverything_butDenyAllAccess() {
		this.testFile.setOwnerAccess("---");
		this.testFile.setGroupAccess("---");
		this.testFile.setGlobalAccess("---");
		
		this.user.addToGroup(this.group);
		this.group.addUser(this.user);
		this.testFile.setOwner(this.user);
		this.testFile.setGroup(this.group);
		
		assertFalse(this.testFile.canExecute(this.user));
		assertFalse(this.testFile.canRead(this.user));
		assertFalse(this.testFile.canWrite(this.user));
	}
	
}
