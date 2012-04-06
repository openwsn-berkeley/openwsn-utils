package edu.berkeley.eecs.openwsn.shared;

import com.google.gwt.event.shared.EventHandler;
/**
 * 
 * @author Xavi Vilajosana
 *
 */
public interface DataRequestEventHandler extends EventHandler {

    void requestData(RequestEvent event);
    
}

	
