package test;

import jo4neo.Nodeid;
import jo4neo.neo;

public class City {
	transient Nodeid neo;
	@neo(index=true) String name;
	@neo State state;
	@neo double lat;
	@neo double lon;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
}
