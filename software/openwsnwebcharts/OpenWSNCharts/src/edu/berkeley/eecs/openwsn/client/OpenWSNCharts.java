package edu.berkeley.eecs.openwsn.client;

import java.util.Date;
import java.util.List;

import java_cup.Main;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;

import sun.awt.VerticalBagLayout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import edu.berkeley.eecs.openwsn.shared.DataRequestEventHandler;
import edu.berkeley.eecs.openwsn.shared.RequestEvent;
import edu.berkeley.eecs.openwsn.shared.SensorStats.Types;
import edu.berkeley.eecs.openwsn.shared.TupleDateValue;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Xavi Vilajosana
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OpenWSNCharts implements DataRequestEventHandler ,EntryPoint  {


	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private Date lastseen=new Date(0);//1970
	
	private String ipAddress=null;
	private Long netId=null;
	private String appId=null;

	private Chart chart=null;
	private Series series=null;
	private MainWindow main=null;
	
	public Chart createChart() {  
		final Chart chart = new Chart()  
		.setType(Series.Type.AREA_SPLINE)  
		.setMarginRight(10)  
		.setChartTitleText("Live data")  
		.setBarPlotOptions(new BarPlotOptions()  
		.setDataLabels(new DataLabels()  
		.setEnabled(true)  
				)  
				)  
				.setLegend(new Legend()  
				.setEnabled(false)  
						)  
						.setCredits(new Credits()  
						.setEnabled(false)  
								)  
								.setToolTip(new ToolTip()  
								.setFormatter(new ToolTipFormatter() {  
									public String format(ToolTipData toolTipData) {  
										return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
												DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")  
												.format(new Date(toolTipData.getXAsLong())) + "<br/>" +  
												NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble());  
									}  
								})  
										);  

		chart.getXAxis()  
		.setType(Axis.Type.DATE_TIME)  
		.setTickPixelInterval(150);  

		chart.getYAxis()  
		.setAxisTitleText("Value")  
		.setPlotLines(  
				chart.getYAxis().createPlotLine()  
				.setValue(0)  
				.setWidth(1)  
				.setColor("#808080")  
				);  

		series = chart.createSeries();  
		chart.addSeries(series  
				.setName("Data")  
				);  


		Timer tempTimer = new Timer() {  
			@Override  
			public void run() {  
				if (netId!=null&ipAddress!=null&appId!=null){
				   getDataFromServer(chart, series);
				}//end if
			}

			private void getDataFromServer(final Chart chart,
					final Series series) {
				greetingService.getNewValues(netId,ipAddress,appId, lastseen, new AsyncCallback<List<TupleDateValue>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						Window.alert("Error");
					}

					public void onSuccess(List<TupleDateValue> result) {
						for (TupleDateValue tup:result){ 
							if (series.getPoints().length>40){
								Date d=tup.getDate();
								Double val=tup.getValue();
								series.addPoint( d.getTime(),  val,false, true, true );
							}else{
								Date d=tup.getDate();
								Double val=tup.getValue();
								series.addPoint( d.getTime(),  val,false, false, true );
							}
							lastseen=tup.getDate();
						}
						chart.redraw();
					}
				});
			}  
		};  
		tempTimer.scheduleRepeating(2000); 

		return chart;  
	} 

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		EventBus.getInstance().addHandler(RequestEvent.TYPE, this);

		RootPanel rootPanel = RootPanel.get("chart");
		chart=createChart();
		rootPanel.add(chart);
		//rootPanel.add(main);

		RootPanel resetPanel=RootPanel.get("header");
		Label label=new Label("Reset Data");
		label.setDirectionEstimator(true);
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		resetPanel.add(label);

		
		label.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//call server
				greetingService.resetData(new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						Window.alert("Reset Succesful!");
						main.reset();
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Something went wrong. Try again please.");

					}
				});
			}
		});
		
		RootPanel controlPanel=RootPanel.get("control");
		main=new MainWindow();
		controlPanel.add(main);

	}

	@Override
	public void requestData(RequestEvent event) {
		//	invoked by the eventbus producers.
	   //set global variables
		if (event.isEraseAll()){
			ipAddress=null;
		    netId=null;
		    appId=null;
			
		}else{
			ipAddress=event.getIpAddress();
		    netId=event.getNetworkID();
		    appId=event.getAppType();
		}
		 //clear the chart.
		 chart.removeAllSeries();
		 series=chart.createSeries();  
			chart.addSeries(series  
					.setName("Data")  
					);  
		 chart.redraw();
		 lastseen=new Date(0);
	}


}