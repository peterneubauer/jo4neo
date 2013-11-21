package jo4neo.impl;

import java.net.URI;


import org.neo4j.graphdb.Node;

class URINodeId extends DefaultNodeid{

	
	URI uri;
	public static final String uriconst = "uri";
	
	public URINodeId(URI o) {
		uri = o;
	}

	@Override
	public Node mirror(IndexedNeo ns) {
		return ns.getURINode(uri);
	}
	
	

}
