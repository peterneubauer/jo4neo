package test.specific;

import jo4neo.Nodeid;
import jo4neo.neo;

public class Article {
	
	transient Nodeid id;
	
	@neo(fulltext=true) String content;
	@neo String author;
	@neo String[] tags;
	
	public Article() {}


}
