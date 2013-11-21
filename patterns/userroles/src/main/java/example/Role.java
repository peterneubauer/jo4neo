package example;

import java.util.Collection;
import java.util.LinkedList;
import jo4neo.Nodeid;
import jo4neo.neo;

public class Role {
	transient Nodeid id;
	@neo public Role parent;
	@neo(index=true) public String name;	
	@neo(inverse="parent") Collection<Role> children;	
	@neo(inverse="role") public Collection<User> directMembers;
	@neo(traverser=RoleMembers.class) public Collection<User> members;
	
	public Role() {}

	public Role(String name) { this.name = name; }
	
	public Role(String name, Role parent) { 
		this(name);
		this.parent = parent;
	}

	public boolean hasRole(Role r) {		
		for(Role c = this;c != null && c != c.parent;c = c.parent)
			if (c.equals(r))
				return true;
		return false;
	}
	
	public Collection<User> getMembers() {
		return getMembers(new LinkedList<User>(), this);
	}
	
	private Collection<User> getMembers(Collection<User> results, Role r) {
			results.addAll(r.directMembers);
			for (Role role : r.children)
				getMembers(results, role);
			return results;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Role)) return false;
		if (o == this) return true;
		if (name==null)
			return ((Role)o).name==null;
		return name.equals(((Role)o).name);
	}
}
