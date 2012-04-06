package edu.berkeley.eecs.openwsn.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import edu.berkeley.eecs.openwsn.shared.NetworkStats;
import edu.berkeley.eecs.openwsn.shared.SensorStats;
import edu.berkeley.eecs.openwsn.shared.SensorStats.Types;
import edu.berkeley.eecs.openwsn.shared.TupleDateValue;

/**
 * 
 * @author Xavi Vilajosana
 *
 */
@Path("/stats")
public class AddStats {
	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	// This method is called if TEXT_PLAIN is request
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String addStatsPlain() {
			proccessRequest();
			return "Hello OpenWSN";
		}

		
		// This method is called if XML is request
		@GET
		@Produces(MediaType.TEXT_XML)
		public String addStatsXML() {
			proccessRequest();
			return "<?xml version=\"1.0\"?>" + "<hello> Hello OpenWSN" + "</hello>";
		}

		// This method is called if HTML is request
		@GET
		@Produces(MediaType.TEXT_HTML)
		public String addStatsHtml() {
			String text=proccessRequest();
			return "<html> " + "<title>" + "Hello OpenWSN" + "</title>"
					+ "<body><h1>" + "Hello OpenWSN "+ text + "</body></h1>" + "</html> ";
		}


		private String proccessRequest() {
			MultivaluedMap<String,String> map=uriInfo.getQueryParameters();
			String ip=map.get("ip").get(0); //ipv6
			Long nid=Long.parseLong(map.get("nid").get(0));//net id
			Double value=Double.parseDouble(map.get("data").get(0));//value
			String app=map.get("app").get(0);//app
			
			String text="";
			NetworkStats net=DomainController.getInstance().getNetworkStats(nid);
			if (net==null){
				net=DomainController.getInstance().addNetworkStats(nid);//create the network
			}
			if (net.getSensorStats(ip)==null){
				SensorStats se= new SensorStats();
				se.setNetworkID(nid);
				se.setSensorId(ip);
				Date date=new Date();
				
		
			    TupleDateValue tup= new TupleDateValue(date, value);
				List<TupleDateValue> li=new ArrayList<TupleDateValue>();
				li.add(tup);
				se.addValues(app, li);
				text=":Sensor Stats updated ID:"+ se.getSensorId();
				net.addSensorStats(se);	
			}else{
				SensorStats se=net.getSensorStats(ip);
				Date date=new Date();
				
				//rssi
			    TupleDateValue tup= new TupleDateValue(date, value);
				List<TupleDateValue> li=new ArrayList<TupleDateValue>();
				li.add(tup);
				se.addValues(app, li);
				text=":Sensor Stats updated ID"+ se.getSensorId();
			}
			return text;
		}
	
	
}
