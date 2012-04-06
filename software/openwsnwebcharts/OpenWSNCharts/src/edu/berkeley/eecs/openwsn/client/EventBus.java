package edu.berkeley.eecs.openwsn.client;

import com.google.gwt.event.shared.HandlerManager;
/**
 * 
 * @author Xavi Vilajosana
 *
 */
public class EventBus {
    private EventBus() {}
    
    private static final HandlerManager INSTANCE = new HandlerManager(null);
   
    public static HandlerManager getInstance() {
            return INSTANCE;
    }
}
