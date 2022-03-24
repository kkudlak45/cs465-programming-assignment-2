package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class ChangeOwnerTest {

	ChangeOwner unit = new ChangeOwner();
	User rootUser = new User("root", "1234");
	User otherUser = new User("other", "1234");
	File testFile;
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
		FileService.resetFieldsForTest();
		this.rootUser.givePrivilege();
		this.testFile = new File("test");
	}  
	
	@Test
	public void successCase() {
		FileService.addFile(this.testFile);
		UserService.setActiveUser(this.rootUser);
		UserService.addUser(this.otherUser);
		
		this.unit.parse("chown test other");
		assertTrue(this.testFile.isOwnedBy(this.otherUser));
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		FileService.addFile(this.testFile);
		UserService.addUser(this.otherUser);
		
		this.unit.parse("chown test other");
		assertFalse(this.testFile.isOwnedBy(this.otherUser));
	}
	
	@Test
	public void whenFileNameRestricted_thenDenyAccess() {
		UserService.setActiveUser(this.rootUser);
		UserService.addUser(this.otherUser);
		
		FileService.addFile(new File(UniversalConstants.ACCOUNTS_FILE));
		FileService.addFile(new File(UniversalConstants.AUDIT_FILE));
		FileService.addFile(new File(UniversalConstants.FILES_FILE));
		FileService.addFile(new File(UniversalConstants.GROUPS_FILE));

		this.unit.parse("chown " + UniversalConstants.ACCOUNTS_FILE + " other");
		this.unit.parse("chown " + UniversalConstants.AUDIT_FILE    + " other");
		this.unit.parse("chown " + UniversalConstants.FILES_FILE    + " other");
		this.unit.parse("chown " + UniversalConstants.GROUPS_FILE   + " other");
		
		for (File file : FileService.getFiles()) {
			assertFalse(file.isOwnedBy(this.otherUser));
		}
	}
	
	@Test
	public void whenFileDoesntExist_thenDenyAcccess() {
		UserService.setActiveUser(this.rootUser);
		UserService.addUser(this.otherUser);
		
		this.unit.parse("chown test other");
		assertFalse(this.testFile.isOwnedBy(this.otherUser));
	}
	
	@Test
	public void whenUserDoesntExist_thenDenyAcccess() {
		UserService.setActiveUser(this.rootUser);
		FileService.addFile(this.testFile);
		
		this.unit.parse("chown test other");
		assertFalse(this.testFile.isOwnedBy(this.otherUser));
	}
	
}
