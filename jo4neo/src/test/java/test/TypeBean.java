package test;

import java.util.Collection;
import java.util.Date;

import jo4neo.Nodeid;
import jo4neo.neo;


public class TypeBean {
	
	Nodeid neo;
	
	@neo Date date;
	@neo int intItem;
	@neo long longItem;
	@neo int[] ages;
	@neo String[] names;
	@neo Collection<String> tags;
	@neo Collection<Double> values;
	
	@neo boolean waar;
	@neo boolean onwaar;
	@neo boolean ookwaar;

	public Boolean getWaar() {
		return waar;
	}
	
	public void setWaar(boolean val) {
		this.waar = val;
	}

	public Boolean getOnwaar() {
		return onwaar;
	}
	
	public void setOnwaar(boolean val) {
		this.onwaar = val;
	}

	public Boolean getOokwaar() {
		return ookwaar;
	}
	
	public void setOokwaar(boolean val) {
		this.ookwaar = val;
	}
	
	public Collection<Double> getValues() {
		return values;
	}
	public void setValues(Collection<Double> values) {
		this.values = values;
	}
	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIntItem() {
		return intItem;
	}
	public void setIntItem(int intItem) {
		this.intItem = intItem;
	}
	public long getLongItem() {
		return longItem;
	}
	public void setLongItem(long longItem) {
		this.longItem = longItem;
	}
	public int[] getAges() {
		return ages;
	}
	public void setAges(int[] ages) {
		this.ages = ages;
	}
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	
	

}
