package org.hvl.CoAP;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;


public class DefaultConnector extends Layer{
	
	// Constants ///////////////////////////////////////////////////////////////
	
		public final static int DEFAULT_PORT       = 5683;
		
		
        // I/O implementation //////////////////////////////////////////////////////
		
		@Override
		protected void performSendMessage(MessageFormat msg) throws IOException {

			// delegate to first layer
			sendMessageOverLayer(msg);
		}	
		
		@Override
		protected void performReceiveMessage(MessageFormat msg) {
			
			if (msg instanceof Response) {
				Response response = (Response) msg;
				
				// initiate custom response handling
				response.handle();
				
			} else if (msg instanceof Request) {
				Request request = (Request) msg;
				
				request.setConnector(this);
			}	
			
			// deliver message to registered receivers
			deliverMessage(msg);
			
		}
		
}


