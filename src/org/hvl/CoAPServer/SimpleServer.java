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
        ChannelManager chManager = CoapChannelManager.getInstance();
        chManager.createServerListener(server, PORT);
        
    }

	public Server onAccept(Request req) {
		System.out.println("Accept connection...");
		return this;
	}

	
	public void onRequest(ServerChannel ch, Request req) {
		
		System.out.println("Received message: " + req.toString()+ " URI: " + req.getURI());
		
		MessageFormat response = (MessageFormat) ch.createResponse(req,
				CoAPCodeRegistries.ResponseCode.CONTENT);
		response.setContentType(MediaTypeRegistery.PLAIN);
		
		response.setPayloadByte("payload...".getBytes());
		ch.sendMessage(response);
		
	       }
    	

	public void onSeparateResponseFailed(ServerChannel ch) {
		System.out.println("Separate response transmission failed.");
		
	}

	

	
	}



