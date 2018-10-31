package org.hvl.Interfaces;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.MediaTypeRegistery;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;;


public interface ServerChannel extends Channel{
	
	/* creates a normal response */
    public Response createResponse(MessageFormat request, CoAPCodeRegistries.ResponseCode responseCode);

    
    
	/* creates a separate response and acknowledges the current request with an empty ACK in case of a CON.
	 * The separate response can be sent later using sendSeparateResponse()  */
	public Response createSeparateResponse(Request request,
			CoAPCodeRegistries.ResponseCode responseCode);

	/* used by a server to send a separate response */
	public void sendSeparateResponse(Response response);
	
	void handleResponse(Response response);


}
