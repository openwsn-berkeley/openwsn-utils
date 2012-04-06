package edu.berkeley.eecs.openwsn.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Xavi Vilajosana
 *
 */

public class NetworkStats {
	
	private long networkId;
	private HashMap<String,SensorStats> sensorStats;
	
	
	public NetworkStats(){
		super();
		sensorStats=new HashMap<String, SensorStats>();
	}
	
	
	
	public NetworkStats(Long id){
		super();
		sensorStats=new HashMap<String, SensorStats>();
		this.networkId=id;
	}
	
	public void addSensorStats(SensorStats se){
		if (sensorStats.get(se.getSensorId())==null){
			sensorStats.put(se.getSensorId(), se);
		}else{
			//do nothing
		}
	}
	
	public SensorStats getSensorStats(String ipaddress){
		return sensorStats.get(ipaddress);
	}
	
	
	public long getNetworkId() {
		return networkId;
	}
	
	
	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}


	public void resetStats() {
		sensorStats.clear();
		
	}



	public List<String> getListOfSensors() {
		return new ArrayList<String>(this.sensorStats.keySet());
	}
	
	
	
}
