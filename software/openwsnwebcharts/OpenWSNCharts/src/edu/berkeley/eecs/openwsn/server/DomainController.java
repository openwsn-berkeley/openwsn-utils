package edu.berkeley.eecs.openwsn.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.berkeley.eecs.openwsn.shared.NetworkStats;
/**
 * 
 * @author Xavi Vilajosana
 *
 */
public class DomainController {
	
	public static DomainController instance;//singleton
	public HashMap<Long,NetworkStats> netstats;
	
	
	
	public DomainController(){
		super();
		if (instance==null){
			instance=this;
			netstats=new HashMap<Long, NetworkStats>();
		}
	}
	
	public static DomainController getInstance(){
		if (instance==null) {
			instance=new DomainController();
			instance.netstats=new HashMap<Long, NetworkStats>();
		}
		return instance;
	}
	
	public NetworkStats getNetworkStats(Long id){
		if (netstats.get(id)!=null){
			return netstats.get(id);
		}else{
			return null;
		}
	}
	
	public NetworkStats addNetworkStats(Long id){
		if (netstats.get(id)!=null){
			//do nothing. it already exists
		}else{
			netstats.put(id, new NetworkStats(id));
			
		}
		return netstats.get(id);
	}
	
	
	public void resetAll(){
		this.netstats.clear();
	}

	public List<Long> getListOfNetworks() {
		
		return new ArrayList<Long>(this.netstats.keySet());
	}
	

}
