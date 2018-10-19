package org.hvl.CoAP;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;


public class Communicator extends UpperLayer{
	
	// Constants ///////////////////////////////////////////////////////////////
	
		public final static int DEFAULT_PORT       = 5683;
		
		
		
		// Constructors ////////////////////////////////////////////////////////////
		
		/*
		 * Constructor for a new Communicator
		 * 
		 * @param port The local UDP port to listen for incoming messages
		 */	
		public Communicator(int port, boolean daemon) throws SocketException {
			
			
		}

		/*
		 * Constructor for a new Communicator
		 * 
		 */
		public Communicator() throws SocketException {
			this(0, true);
		}
		
		
		
		
		
		// I/O implementation //////////////////////////////////////////////////////
		
		@Override
		protected void doSendMessage(MessageFormat msg) throws IOException {

			// delegate to first layer
			sendMessageOverLowLayer(msg);
		}	
		
		@Override
		protected void doReceiveMessage(MessageFormat msg) {
			
			if (msg instanceof Response) {
				Response response = (Response) msg;
				
				// initiate custom response handling
				response.handle();
				
			} else if (msg instanceof Request) {
				Request request = (Request) msg;
				
				request.setCommunicator(this);
			}	
			
			// pass message to registered receivers
			deliverMessage(msg);
			
		}
		
}


