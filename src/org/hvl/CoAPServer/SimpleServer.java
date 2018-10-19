package org.hvl.CoAPServer;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.MediaTypeRegistery;
import org.hvl.CoAP.MessageFormat;
import org.hvl.Interfaces.ChannelManager;
import org.hvl.Interfaces.CoapChannelManager;
//import org.hvl.Interfaces.Message;
//import org.hvl.Interfaces.Request;
import org.hvl.CoAPClient.Request;
import org.hvl.Interfaces.Server;
import org.hvl.Interfaces.ServerChannel;




public class SimpleServer implements Server{
	
	private static final int PORT = 5683;
    static int counter = 0;

    public static void main(String[] args) {
        System.out.println("Start CoAP Server on port " + PORT);
        
        SimpleServer server = new SimpleServer();
        //ChannelManager channelManager = null;
        ChannelManager channelManager = CoapChannelManager.getInstance();
        channelManager.createServerListener(server, PORT);
        
    }

	public Server onAccept(Request request) {
		System.out.println("Accept connection...");
		return this;
	}

	
	public void onRequest(ServerChannel channel, Request request) {
		//while(true){
		System.out.println("Received message: " + request.toString()+ " URI: " + request.getURI());
		
		MessageFormat response = (MessageFormat) channel.createResponse(request,
				CoAPCodeRegistries.ResponseCode.CONTENT);
		response.setContentType(MediaTypeRegistery.PLAIN);
		
		response.setPayload("payload...".getBytes());
		
		
		
		channel.sendMessage(response);
		//try { //new
	        //Thread.sleep(10000); //new
	       //} catch (InterruptedException e) { //new
	         //e.printStackTrace();//new
	       }
    	//}//
	//}

	public void onSeparateResponseFailed(ServerChannel channel) {
		System.out.println("Separate response transmission failed.");
		
	}

	//@Override
	/**public Server onAccept(Request request) {
		// TODO Auto-generated method stub
		return null;
	}**/

	//@Override
	/**public void onRequest(ServerChannel channel, org.hvl.Interfaces.Request request) {
		// TODO Auto-generated method stub
		
	}**/

	
	}



