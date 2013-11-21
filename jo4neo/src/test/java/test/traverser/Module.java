package test.traverser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import jo4neo.Nodeid;
import jo4neo.neo;

public class Module {

	transient Nodeid node;
	@neo("HAS_BASIC_ITEM")
	private Collection<BasicItem> basicItems = new ArrayList<BasicItem>();
	@neo("HAS_MODULE")
	private Collection<Module> modules = new ArrayList<Module>();
	@neo(index = true)
	private String name;
	@neo(traverser = ModuleTraverser.class)
	private Collection<Object> elements;

	
	// --------------------------- CONSTRUCTORS

	public Module() {
	}

	public Module(String name) {
		this.name = name;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Collection<BasicItem> getBasicItems() {
		return Collections.unmodifiableCollection(basicItems);
	}

	public Collection<Object> getElements() {
		return Collections.unmodifiableCollection(elements);
	}

	public Collection<Module> getModules() {
		return Collections.unmodifiableCollection(modules);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Nodeid getNode() {
		return node;
	}

	// ------------------------ CANONICAL METHODS ------------------------

	@Override
	public String toString() {
		return "Module{" + "name='" + name + '\'' + ", node=" + node.id() + '}';
	}

	// -------------------------- OTHER METHODS --------------------------

	public void addBasicItem(BasicItem bi) {
		basicItems.add(bi);
	}

	public void addModule(Module m) {
		modules.add(m);
	}




}
