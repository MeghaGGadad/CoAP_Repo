package org.hvl.Interfaces;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.MediaTypeRegistery;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;;


public interface ServerChannel extends Channel{
	
	/* creates a normal response */
    public Response createResponse(MessageFormat request, CoAPCodeRegistries.ResponseCode responseCode);

    /* creates a normal response */
    public Response createResponse(MessageFormat request, CoAPCodeRegistries.ResponseCode responseCode, MediaTypeRegistery contentType);
    
	/* creates a separate response and acks the current request witch an empty ACK in case of a CON.
	 * The separate response can be send later using sendSeparateResponse()  */
	public Response createSeparateResponse(Request request,
			CoAPCodeRegistries.ResponseCode responseCode);

	/* used by a server to send a separate response */
	public void sendSeparateResponse(Response response);
	
	
	/* used by a server to create a notification (observing resources),  reliability is base on the request packet type (con or non) */
	public Response createNotification(Request request, CoAPCodeRegistries.ResponseCode responseCode, int sequenceNumber);
	
	/* used by a server to create a notification (observing resources) */
	public Response createNotification(Request request, CoAPCodeRegistries.ResponseCode responseCode, int sequenceNumber, boolean reliable);
	
	/* used by a server to send a notification (observing resources) */
	public void sendNotification(Response response);



}
