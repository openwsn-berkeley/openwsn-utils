package edu.berkeley.eecs.openwsn.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.berkeley.eecs.openwsn.shared.SensorStats.Types;
import edu.berkeley.eecs.openwsn.shared.TupleDateValue;

/**
 * 
 * @author Xavi Vilajosana
 *
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	List<Long> getNetworks();
	List<String> getSensors(Long netID);
	List<String> getApps(Long netID,String ip);
	List<TupleDateValue> getNewValues(Long netId,String sensorIP,String App,Date date);
	void resetData();
}
