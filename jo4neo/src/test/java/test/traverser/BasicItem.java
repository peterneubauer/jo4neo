package test.traverser;

import jo4neo.Nodeid;
import jo4neo.neo;

public class BasicItem {
	// ------------------------------ FIELDS

	@neo(index = true)
	private String name;

	private transient Nodeid node;

	// --------------------------- CONSTRUCTORS

	@Override
	public String toString() {
		return "BasicItem{" + "node=" + node.id() + ", name='" + name + '\''
				+ '}';
	}

	public BasicItem() {
	}

	public BasicItem(String name) {
		this.name = name;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Nodeid getNode() {
		return node;
	}
}
