package test;

import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.neo;


public class Airport {
	transient Nodeid neo;

	@neo(index=true) String code;
	@neo String name;
	@neo Collection<City> citiesServed;
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
	public Collection<City> getCitiesServed() {
		return citiesServed;
	}
	public void setCitiesServed(Collection<City> citiesServed) {
		this.citiesServed = citiesServed;
	}
	
	
}
