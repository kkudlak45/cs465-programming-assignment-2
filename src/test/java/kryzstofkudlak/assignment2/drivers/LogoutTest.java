package kryzstofkudlak.assignment2.drivers;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import kryzstofkudlak.assignment2.objects.User;
import kryzstofkudlak.assignment2.services.UserService;

public class LogoutTest {

	LogoutUser unit = new LogoutUser();
	private static final String USERNAME = "tom";
	private static final String PASSWORD = "1234";
	private User testUser = new User(USERNAME, PASSWORD);
	
	@Before
	public void setup() {
		UserService.resetFieldsForTest();
	}
	
	@Test
	public void whenUserExists_thenLogoutUser() {
		UserService.setActiveUser(this.testUser);
		this.unit.parse("logout");
		assertNull(UserService.getActiveUser());
	}
	
	@Test
	public void whenNoUserLoggedIn_thenReportError() {
		this.unit.parse("logout");
		assertNull(UserService.getActiveUser());
	}
	
}
