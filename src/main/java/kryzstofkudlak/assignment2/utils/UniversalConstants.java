package kryzstofkudlak.assignment2.utils;

import java.util.Set;

public class UniversalConstants {

	private UniversalConstants() {};
	
	public static final String NIL = "nil";
	
	public static final String PRIV_USER_NAME = "root";
	
	public static final String ACCOUNTS_FILE = "accounts.txt";
	public static final String GROUPS_FILE = "groups.txt";
	public static final String AUDIT_FILE = "audit.txt";
	public static final String FILES_FILE = "files.txt";
	public static final Set<String> RESTRICTED_FILENAMES = Set.of(ACCOUNTS_FILE, GROUPS_FILE, AUDIT_FILE, FILES_FILE);
	
}
