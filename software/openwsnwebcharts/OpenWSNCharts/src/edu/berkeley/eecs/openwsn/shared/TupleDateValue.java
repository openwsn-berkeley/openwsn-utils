package edu.berkeley.eecs.openwsn.shared;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * 
 * @author Xavi Vilajosana
 *
 */
public class TupleDateValue implements Comparable,IsSerializable,Serializable{

	
	private Date date;
	private Double value;
	
	public TupleDateValue(){
		super();
	}
	
	public TupleDateValue(Date date, Double value) {
		super();
		this.date = date;
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object arg0) {
		TupleDateValue newval=(TupleDateValue) arg0;
		return date.compareTo(newval.getDate());
	}	
	
	
}
