package edu.berkeley.eecs.openwsn.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.berkeley.eecs.openwsn.shared.RequestEvent;
/**
 * 
 * @author Xavi Vilajosana
 *
 */
public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT
			.create(MainWindowUiBinder.class);

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}


	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);


	@UiField
	Label netlistboxlabel;

	@UiField 
	ListBox netlistbox;

	@UiField
	Label applistboxlabel;

	@UiField 
	ListBox applistbox;

	@UiField
	Label iplistboxlabel;

	@UiField 
	ListBox iplistbox;



	public MainWindow() {
		initWidget(uiBinder.createAndBindUi(this));

		getNetworks();

	}

	private void getNetworks() {
		netlistbox.clear();
		netlistbox.addItem("--");
		greetingService.getNetworks(new AsyncCallback<List<Long>>() {

			@Override
			public void onSuccess(List<Long> result) {
				for (Long l:result){
					netlistbox.addItem(l.toString());
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("An error ocurred in the server.");

			}
		});
	}

	@UiHandler("netlistbox")
	void onChangeNetListBox(ChangeEvent sender) {
		if (netlistbox.getSelectedIndex()>0){
			String text=netlistbox.getItemText(netlistbox.getSelectedIndex());
			//iplistbox.clear();
			getSensors(text);
			
		}else if (netlistbox.getSelectedIndex()==0){
			/*iplistbox.clear();
			iplistbox.addItem("--");
			applistbox.clear();
			applistbox.addItem("--");
			getNetworks();//just in case there is an update.*/
		}

	}
	
	
	@UiHandler("netlistbox")
	void onClickNetListBox(ClickEvent sender) {
		if (netlistbox.getSelectedIndex()>0){
			//do nothing
		}else if (netlistbox.getSelectedIndex()==0){
			iplistbox.clear();
			iplistbox.addItem("--");
			applistbox.clear();
			applistbox.addItem("--");
			getNetworks();//just in case there is an update.
			
			//generate event to all listeners.
			EventBus.getInstance().fireEvent(new RequestEvent(null, null, null,true));
			
		}

	}
	

	private void getSensors(String text) {
		greetingService.getSensors(Long.parseLong(text),new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("An error occurred when searching sensors in the server.");

			}

			@Override
			public void onSuccess(List<String> result) {
				iplistbox.clear();
				iplistbox.addItem("--");
				for (String s:result){
					iplistbox.addItem(s);
				}

			}

		});
	}

	
	@UiHandler("iplistbox")
	void onChangeIPListBox(ChangeEvent sender) {
		if (iplistbox.getSelectedIndex()>0){
			String text=iplistbox.getItemText(iplistbox.getSelectedIndex());
			String netID=netlistbox.getItemText(netlistbox.getSelectedIndex());
			getApps(text, netID);
			
		}else if (iplistbox.getSelectedIndex()==0){
			applistbox.clear();
			applistbox.addItem("--");
		}

	}

	private void getApps(String text, String netID) {
		greetingService.getApps(Long.parseLong(netID), text,new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("An error occurred when searching Apps in the server.");

			}

			@Override
			public void onSuccess(List<String> result) {
				applistbox.clear();
				applistbox.addItem("--");
				for (String s:result){
					applistbox.addItem(s);
				}

			}

		});
	}
	
	
	@UiHandler("applistbox")
	void onChangeAppListBox(ChangeEvent sender) {
		if (iplistbox.getSelectedIndex()>0){
			String ip=iplistbox.getItemText(iplistbox.getSelectedIndex());
			String netID=netlistbox.getItemText(netlistbox.getSelectedIndex());
			String appID=applistbox.getItemText(applistbox.getSelectedIndex());
			//generate event to all listeners.
			EventBus.getInstance().fireEvent(new RequestEvent(Long.parseLong(netID), ip, appID,false));
			
		}else if (iplistbox.getSelectedIndex()==0){
			applistbox.clear();
			applistbox.addItem("--");
		}

	}

	public void reset() {
		// TODO Auto-generated method stub
		iplistbox.clear();
		iplistbox.addItem("--");
		applistbox.clear();
		applistbox.addItem("--");
		netlistbox.clear();
		netlistbox.addItem("--");
	}
	

}
