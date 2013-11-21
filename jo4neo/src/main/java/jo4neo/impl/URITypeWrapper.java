package jo4neo.impl;

import java.net.URI;

import jo4neo.Nodeid;

import org.neo4j.graphdb.Node;



class URITypeWrapper extends TypeWrapper {

	@Override
	public FieldContext[] getFields(Object o) {
		return new FieldContext[0];
	}

	@Override
	public Class<?> getWrappedType() {
		return URI.class;
	}

	@Override
	public InjectedNodeid id(Object o) {
		return new URINodeId((URI)o);
	}

	@Override
	public String name() {
		return URI.class.getName();
	}

	@Override
	public Object newInstance(Object o) {
		Node node = (Node)o;
		String uri = (String)node.getProperty("uri");
		return URI.create(uri);
	}

	@Override
	public void setId(Object bean, Nodeid n) {
		//noop
	}


}
