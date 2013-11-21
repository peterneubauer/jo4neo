package example;

import java.util.Collection;
import java.util.LinkedList;

import jo4neo.Nodeid;
import jo4neo.neo;;

public class User {
	transient Nodeid neoid;
	@neo(index=true) public String id;
	@neo public String firstName;
	@neo public String lastName;
	@neo("role") public Collection<Role> directRoles;
	
	public User() {
		directRoles = new LinkedList<Role>();
	}

	public boolean hasRole(Role r) {
		for (Role role : directRoles)
			if (role.hasRole(r))
				return true;
		return false;		
	}

	public boolean equals(Object o) {
		if (!(o instanceof User)) return false;
		if (o == this) return true;
		if (id==null)
			return ((User)o).id==null;
		return id.equals(((User)o).id);
	}
}