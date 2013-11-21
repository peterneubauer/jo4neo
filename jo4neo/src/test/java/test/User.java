package test;

import java.util.Collection;
import java.util.LinkedList;

import jo4neo.Nodeid;
import jo4neo.neo;


/**
 * 
 */
public class User {
	transient Nodeid neoid;
	
	/**
	 * this field is indexed.  This provides the ability 
	 * to retrieve the node given 
	 */
	@neo(index=true) public String id;
	@neo public String firstName;
	@neo public String lastName;
	
	/**
	 * Direct roles are explicit.  A user may be a role member
	 * based on the parent role of a direct role.  In other words,
	 * the contents of directRoles may indicate a user is a member of 
	 * a given role, but it cannot determine if the user is definitely 
	 * NOT a member of a given role.  Use #hashRole().
	 * 
	 */
	@neo("role") Collection<Role> directRoles;
	
	public User() {
		directRoles = new LinkedList<Role>();
	}
	
	/**
	 * true if user is a member of given role.  
	 * We simply ask each direct role if the given role is either
	 * itself, or one of its parents, i.e., a CEO_ROLE is also an EMPLOYEE_ROLE.
	 * 
	 * @param r a role to test for membership
	 * @return true if user is a member of the given role.
	 */
	public boolean hasRole(Role r) {
		for (Role role : directRoles)
			if (role.hasRole(r))
				return true;
		return false;		
	}
	

}
