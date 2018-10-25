package org.hvl.CoAP;

import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;

public interface HandleMessage {
	
    public void HandelRequest(Request request);
	
	public void HandelResponse(Response response);

}
