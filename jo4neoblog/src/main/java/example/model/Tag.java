package example.model;

import java.util.Collection;

import jo4neo.neo;

public class Tag extends NeoBean<Tag> {
	
	@neo(inverse="hasTag") public Collection<Post> posts;
	@neo(index=true) public String name;

	public Tag() {}
	
	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public Collection<Post> getPosts() {
		return posts;
	}
	

	
	public boolean equals(Object o) {
		if (! (o instanceof Tag)) return false;
		if (this == o) return true;
		return (name.equals(((Tag)o).name));
	}

}
