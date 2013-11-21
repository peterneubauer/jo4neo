package test.uri;

import java.net.URI;

import jo4neo.Nodeid;
import jo4neo.neo;

@neo(recency=true)
public class Review {

	transient Nodeid node;
	@neo public String content;
	@neo public URI link;
	@neo(index=true) public int stars;

}
