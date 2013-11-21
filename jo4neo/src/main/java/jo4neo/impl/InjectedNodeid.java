package jo4neo.impl;

import org.neo4j.graphdb.Node;

import jo4neo.Nodeid;

interface InjectedNodeid extends Nodeid {
	public abstract Node mirror(IndexedNeo neo);

}
