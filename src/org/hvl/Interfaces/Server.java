package org.hvl.Interfaces;

import org.hvl.CoAPClient.Request;

public interface Server{
	
	public Server onAccept(Request request);
    public void onRequest(ServerChannel channel, Request request);
	public void onSeparateResponseFailed(ServerChannel channel);

}
