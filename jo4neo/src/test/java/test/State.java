package test;

import java.util.ArrayList;
import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.Timeline;
import jo4neo.neo;


@Timeline
public class State {
	transient Nodeid neo;
	public static final String STATE_CODE_IDX = "test.State.code_INDEX";
	
	@neo(index=true) String code;
	@neo String name;	
	@neo Collection<City> cities = new ArrayList<City>();
	
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
	public Collection<City> getCities() {
		return cities;
	}
	public void setCities(Collection<City> cities) {
		this.cities = cities;
	}
}
