package edu.berkeley.eecs.openwsn.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.berkeley.eecs.openwsn.client.GreetingService;
import edu.berkeley.eecs.openwsn.shared.TupleDateValue;

/**
 * 
 * @author Xavi Vilajosana
 *
 */

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {


	
	@Override
	public void resetData() {
		DomainController.getInstance().resetAll();
		
	}

	@Override
	public List<Long> getNetworks() {
		return DomainController.getInstance().getListOfNetworks();
	}

	@Override
	public List<String> getSensors(Long netID) {
		return DomainController.getInstance().getNetworkStats(netID).getListOfSensors();
	}

	@Override
	public List<String> getApps(Long nid,String ip) {
		List<String> apps= new ArrayList<String>();
		apps= DomainController.getInstance().getNetworkStats(nid).getSensorStats(ip).getListOfApps();
		return apps;
	}

	@Override
	public List<TupleDateValue> getNewValues(Long netId, String sensorIP,String App, Date date) {
				
		try{
		List<TupleDateValue> li=  DomainController.getInstance().getNetworkStats(netId).getSensorStats(sensorIP).getNewValues(App, date);
		return  li;
		}catch (Exception e) {
			return new ArrayList<TupleDateValue>();
		}
		
	}
}
