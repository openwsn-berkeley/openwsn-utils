package edu.berkeley.eecs.openwsn.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


import edu.berkeley.eecs.openwsn.shared.SensorStats.Types;
import edu.berkeley.eecs.openwsn.shared.TupleDateValue;

/**
 * @author Xavi Vilajosana
 * 
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getNewValues(Long netId, String sensorIP, String App, Date date,
			AsyncCallback<List<TupleDateValue>> callback);

	void resetData(AsyncCallback<Void> callback);

	void getApps(Long netID, String ip, AsyncCallback<List<String>> callback);

	void getSensors(Long netID, AsyncCallback<List<String>> callback);

	void getNetworks(AsyncCallback<List<Long>> callback);
	
}
