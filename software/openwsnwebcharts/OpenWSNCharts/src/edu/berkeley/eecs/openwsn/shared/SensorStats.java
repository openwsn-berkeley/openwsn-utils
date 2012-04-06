package edu.berkeley.eecs.openwsn.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Xavi Vilajosana
 *
 */

public class SensorStats {

	public enum Types{
		RSSI,NUMRX,NUMTX,NUMACK,COST
	}

	private static final int MAX_SIZE = 30;
	
	private String ipAddress;
	private long networkId;
	private HashMap<String,List<TupleDateValue>> appData;
    	

	
	
	public SensorStats() {
		super();
		appData=new HashMap<String, List<TupleDateValue>>();
	}
	
	
	public SensorStats(String sensorId, long netid) {
		super();
		this.ipAddress = sensorId;
		this.networkId = netid;
		appData=new HashMap<String, List<TupleDateValue>>();
		
	}
		
	public long getNetworkID() {
		return networkId;
	}
	public void setNetworkID(long dagroot) {
		this.networkId = dagroot;
	}

	
	
	public void addValues(String application,List<TupleDateValue> values){
		if (appData.get(application)!=null){
			List<TupleDateValue> li=appData.get(application);
			private_add(li,values);
		}else{
			//is null, create the list
			List<TupleDateValue> li=new ArrayList<TupleDateValue>();
			private_add(li,values);
			appData.put(application, li);
		}
		
	}

	
	public List<TupleDateValue> getNewValues(String App, Date date){
	   
		if (appData.get(App)!=null){
			return private_getnew(appData.get(App),date);
		}else{
			return new ArrayList<TupleDateValue>();
		}
		   
	}

	private List<TupleDateValue> private_getnew(List<TupleDateValue> list,	Date date) {
	   List<TupleDateValue> result= new ArrayList<TupleDateValue>();
 
	  for (TupleDateValue tup:list){
		  if (tup.getDate().getTime()>date.getTime()){
			  result.add(tup);
		  }
	  }
	  Collections.sort(result); 
	  return result;	
	}


	private void private_add(List<TupleDateValue> list,List<TupleDateValue> values) {
	   list.addAll(values);
	   if (list.size()>MAX_SIZE){
		   System.out.println("Current size of data is " + list.size());
		   list.subList(0, list.size()-MAX_SIZE).clear();
		   System.out.println(list.size()-MAX_SIZE + "elements have been removed and now the size of the list is " + list.size());
		   //list=list.subList(list.size()-MAX_SIZE, list.size());//remove the n first elements.
	   }
	}


	public String getSensorId() {
		return ipAddress;
	}


	public void setSensorId(String sensorId) {
		this.ipAddress = sensorId;
	}


	public List<String> getListOfApps() {
		return new ArrayList<String>(appData.keySet());
	}
	
	
	
	
	
	
}
