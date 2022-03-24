package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.Group;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.GroupService;
import kryzstofkudlak.assignment2.services.UserService;

public class ChangeGroupTest {

	ChangeGroup unit = new ChangeGroup();
	Group group;
	User rootUser;
	User otherUser;
	File testFile;
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
		GroupService.resetFieldsForTest();
		FileService.resetFieldsForTest();

		this.group = new Group("group");
		this.rootUser = new User("root", "1234");
		this.otherUser = new User("other", "1234");
		this.testFile = new File("test");
		
		this.rootUser.givePrivilege();
	}  
	
	@Test
	public void successCase_whenUserIsRoot_andNotInGroupOrOwner() {
		UserService.setActiveUser(this.rootUser);
		GroupService.addGroup(this.group);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test group");
		assertEquals(this.group, this.testFile.getGroup());
	}
	
	@Test
	public void successCase_whenUserIsRoot_andNotInGroupOrOwner_andSetsToNil() {
		UserService.setActiveUser(this.rootUser);
		this.testFile.setGroup(this.group);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test nil");
		assertEquals("nil", this.testFile.getGroup().getName());
	}
	
	@Test
	public void successCase_whenUserIsNotRoot_andIsInGroup_andOwnsFile() {
		this.otherUser.addToGroup(this.group);
		this.group.addUser(this.otherUser);
		this.testFile.setOwner(this.otherUser);
		
		UserService.setActiveUser(this.otherUser);
		GroupService.addGroup(this.group);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test group");
		assertEquals(this.group, this.testFile.getGroup());
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		GroupService.addGroup(this.group);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test group");
		assertNotEquals(this.group, this.testFile.getGroup());
	}
	
	@Test
	public void whenFileDoesntExist_thenReportError() {
		UserService.setActiveUser(this.rootUser);
		GroupService.addGroup(this.group);
		
		this.unit.parse("chgrp test group");
		assertNotEquals(this.group, this.testFile.getGroup());
	}
	
	@Test
	public void whenGroupDoesntExist_thenReportError() {
		UserService.setActiveUser(this.rootUser);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test group");
		assertNotEquals(this.group, this.testFile.getGroup());
	}
	
	@Test
	public void whenUserIsNotOwnerOrRoot_thenReportError() {
		UserService.setActiveUser(this.otherUser);
		GroupService.addGroup(this.group);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test group");
		assertNotEquals(this.group, this.testFile.getGroup());
	}
	
	@Test
	public void whenUserIsOwner_butNotInGroupOrRoot_thenReportError() {
		this.testFile.setOwner(this.otherUser);
		UserService.setActiveUser(this.otherUser);
		GroupService.addGroup(this.group);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chgrp test group");
		assertNotEquals(this.group, this.testFile.getGroup());
	}
	
}
