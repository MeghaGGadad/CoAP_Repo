package org.hvl.CoAPClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.hvl.CoAPServer.Response;


public class SimpleClient {
	
	    // To check whether user entered required info through command line parameters
		private static final int METHOD_INDEX  = 0;
		private static final int URI_INDEX     = 1;
		private static final int PAYLOAD_INDEX = 2;
		
		private static final String DISCOVERY_RESOURCE = "/.well-known/core";
		
		Response response = null;
		
		/*
		 * Main method of this SIMPLE client.
		 * This method check for missing arguments
		 */
		public static void main(String[] args) throws IOException, InterruptedException {
			
			String method  = null;
			String uri     = null;
			String payload = null;
			boolean loop   = false;

			
		      
			// display information  if no arguments specified
			if (args.length == 0) {
				printInfo();
				return;
			}

			
			int index = 0;
			for (String arg : args) {
				if (arg.startsWith("-")) {
					if (arg.equals("-l")) {
						loop = true;
					} else {
						System.out.println("Unrecognized option: " + arg);
					}
				} else {
					switch (index) {
					case METHOD_INDEX:	
						method = arg.toUpperCase();
						break;
					case URI_INDEX:	
						uri = arg;
						break;
					case PAYLOAD_INDEX: 
						payload = arg;
						break;
					default:
						System.out.println("Unexpected argument: " + arg);
					}
					++index;
				}
			}
				
			// create request according to parameter passed 
			if (method == null) {
				System.err.println("Method not specified");
				return;
			}
			Request request = newRequest(method);
			if (request == null) {
				System.err.println("Unknown method: " + method);
				return;
			}
			// set request URI
			if (uri == null) {
				System.err.println("URI not specified");
			}
			try {
				request.setURI(new URI(uri));
			} catch (URISyntaxException e) {
				System.err.println("Failed to parse URI: " + e.getMessage());
				return;
			}
		    while(true){
		    	
		    
			System.out.println("Request sent...");
			request.setPayload(payload);
		   // enable response queue in order to use blocking I/O
			request.ResponseQueueEnable(true);
			
			request.send();
			
		    // loop for receiving multiple responses
				
				// receive response
				
				//Request request = newRequest(method);
				
				System.out.println("Receiving response...");
				Response response = null;
				//response.Printlog();
				System.out.println("Received message: " + request.getNewID() + " Message Type: " + request.getType());
				System.out.println("Response recived..."+request.responseReceive());
				
				
				try {
					response = request.responseReceive();
					
					
					// check for indirect response
					if (response != null && response.isEmptyACK()) {
						response.Printlog();
						System.out.println("Request acknowledged, waiting for separate response...");
						
						response = request.responseReceive();
					}
					
					
				} catch (InterruptedException e) {
					System.err.println("Failed to receive response: " + e.getMessage());
					return;
				}
		    
				
				// to output the response
				
				if (response != null) {
					
					response.Printlog();
					System.out.println("Total Time (ms): " + response.getTime());
					
				}
				
					
					
				else {
					
					// no response received
					// calculate time elapsed 
					long elapsed = System.currentTimeMillis() - request.getTimestamp();
					
					System.out.println("Request timed out (ms): " + elapsed);
					//break;
				}
				try { //new
			        Thread.sleep(10000); //new
			       } catch (InterruptedException e) { //new
			         e.printStackTrace();//new
			       }
			
		    }
		    
	    	}
		
    	
					  
		
		/* If the user is not aware of what the arguments should pass
		 * 
		 * 
		 */
		public static void printInfo() {
			System.out.println("Java Coap Simple Client");
			System.out.println();
			System.out.println("Usage: SimpleClient [-l] METHOD URI [PAYLOAD]");
			System.out.println("  METHOD  : {GET, POST, PUT, DELETE}");
			System.out.println("  URI     : The URI to the remote endpoint or resource");
			System.out.println("  PAYLOAD : The data to send with the request");
			System.out.println("Options:");
			System.out.println("  -l      : Wait for multiple responses");
			System.out.println();
			System.out.println("Examples:");
			System.out.println("  SimpleClient GET coap://10.0.1.97:5683/temp");
			System.out.println("  SimpleClient POST coap://example.com:5683/~sensors/readings.xml");
		}

		/* This method 
		 * instantiates a new request based on a method.
		 * 
		 * @return A new request object depending on method passed, or null if method not correct
		 */
		private static Request newRequest(String method) {
			if (method.equals("GET")) {
				return new GETRequest();
			} else if (method.equals("POST")) {
				return new POSTRequest();
			} else if (method.equals("PUT")) {
				return new PUTRequest();
			} else if (method.equals("DELETE")) {
				return new DELETERequest();
			} else {
				return null;
			}
		}

		

		

		}		

		
	
		
	
