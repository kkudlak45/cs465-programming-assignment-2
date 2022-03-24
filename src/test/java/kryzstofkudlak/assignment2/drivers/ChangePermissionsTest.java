package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.File;
import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.FileService;
import kryzstofkudlak.assignment2.services.UserService;
import kryzstofkudlak.assignment2.utils.UniversalConstants;

public class ChangePermissionsTest {
	
	ChangePermissions unit = new ChangePermissions();
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
	public void successCase_whenPrivileged() {
		FileService.addFile(this.testFile);
		UserService.setActiveUser(this.rootUser);
		
		this.unit.parse("chmod test rwx rwx rwx");
		assertEquals("rwx", this.testFile.getOwnerAccess());
		assertEquals("rwx", this.testFile.getGroupAccess());
		assertEquals("rwx", this.testFile.getGlobalAccess());
	}
	
	@Test
	public void successCase_whenNotPrivileged_butIsOwner() {
		this.testFile.setOwner(this.otherUser);
		FileService.addFile(this.testFile);
		UserService.setActiveUser(this.otherUser);
		
		this.unit.parse("chmod test rwx rwx rwx");
		assertEquals("rwx", this.testFile.getOwnerAccess());
		assertEquals("rwx", this.testFile.getGroupAccess());
		assertEquals("rwx", this.testFile.getGlobalAccess());
	}
	
	@Test
	public void whenNoUserLoggedIn_thenDenyAccess() {
		FileService.addFile(this.testFile);
		
		this.unit.parse("chmod test rwx rwx rwx");
		assertEquals("rw-", this.testFile.getOwnerAccess());
		assertEquals("---", this.testFile.getGroupAccess());
		assertEquals("---", this.testFile.getGlobalAccess());
	}
	
	@Test
	public void whenFilenameIsRestricted_thenDenyAccess() {
		UserService.setActiveUser(this.rootUser);
		
		FileService.addFile(new File(UniversalConstants.ACCOUNTS_FILE));
		FileService.addFile(new File(UniversalConstants.AUDIT_FILE));
		FileService.addFile(new File(UniversalConstants.FILES_FILE));
		FileService.addFile(new File(UniversalConstants.GROUPS_FILE));

		this.unit.parse("chmod " + UniversalConstants.ACCOUNTS_FILE + " rwx rwx rwx");
		this.unit.parse("chmod " + UniversalConstants.AUDIT_FILE    + " rwx rwx rwx");
		this.unit.parse("chmod " + UniversalConstants.FILES_FILE    + " rwx rwx rwx");
		this.unit.parse("chmod " + UniversalConstants.GROUPS_FILE   + " rwx rwx rwx");
		
		for (File file : FileService.getFiles()) {
			assertEquals("rw-", file.getOwnerAccess());
			assertEquals("---", file.getGroupAccess());
			assertEquals("---", file.getGlobalAccess());
		}
	}
	
	@Test
	public void whenFileDoesntExist_thenDenyAccess() {
		UserService.setActiveUser(this.rootUser);
		this.unit.parse("chmod test rwx rwx rwx");
		assertEquals(0, FileService.getFiles().size());
	}
	
	@Test
	public void whenNotRootOrOwner_thenDenyAccess() {
		FileService.addFile(this.testFile);
		UserService.setActiveUser(this.otherUser);
		
		this.unit.parse("chmod test rwx rwx rwx");
		assertEquals("rw-", this.testFile.getOwnerAccess());
		assertEquals("---", this.testFile.getGroupAccess());
		assertEquals("---", this.testFile.getGlobalAccess());
	}

}
