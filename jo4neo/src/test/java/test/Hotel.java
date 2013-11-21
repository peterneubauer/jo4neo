package test;

import java.util.Collection;

import jo4neo.Nodeid;
import jo4neo.neo;


public class Hotel {

	public static final String HOTEL_NAME_IDX = "test.Hotel.name_INDEX";
	transient Nodeid neo;
	
	@neo Collection<Ammenity> ammenities;
	@neo(index=true) String name;
	@neo(index=true) boolean test;

	public Collection<Ammenity> getAmmenities() {
		return ammenities;
	}
	public void setAmmenities(Collection<Ammenity> ammenities) {
		this.ammenities = ammenities;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
