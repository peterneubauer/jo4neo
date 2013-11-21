package test;

import jo4neo.Nodeid;
import jo4neo.neo;

public class Ammenity {
	transient Nodeid neo;
	
	@neo String name;
	@neo String code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
