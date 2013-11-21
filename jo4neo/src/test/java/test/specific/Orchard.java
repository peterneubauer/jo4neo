package test.specific;

import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.neo;

public class Orchard {
	transient Nodeid id;
	@neo public Collection<Apple> grows;
	@neo(index=true) public String name;

}
