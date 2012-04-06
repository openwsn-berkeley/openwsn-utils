package edu.berkeley.eecs.openwsn.shared;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
/**
 * 
 * @author Xavi Vilajosana
 *
 */
public class RequestEvent extends GwtEvent {

	public static final GwtEvent.Type TYPE = new GwtEvent.Type();

	private Long networkID;
	private String ipAddress;
	private String appType;
	private boolean eraseAll;

	public RequestEvent(Long networkID, String ipAddress, String appType,boolean erase) {
		super();
		this.networkID = networkID;
		this.ipAddress = ipAddress;
		this.appType = appType;
		this.eraseAll=erase;
	}

	@Override
	protected void dispatch(EventHandler handler) {
		((DataRequestEventHandler) handler).requestData(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type getAssociatedType() {
		return TYPE;
	}

	public Long getNetworkID() {
		return networkID;
	}

	public void setNetworkID(Long networkID) {
		this.networkID = networkID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public boolean isEraseAll() {
		return eraseAll;
	}

	public void setEraseAll(boolean eraseAll) {
		this.eraseAll = eraseAll;
	}

}
