package org.hvl.Interfaces;

import org.hvl.Interfaces.ClientChannel;
import org.hvl.CoAPServer.Response;

public interface Client{
	
	public void onResponse(ClientChannel channel, Response response);
    public void onConnectionFailed(ClientChannel channel, boolean notReachable, boolean resetByServer);
	//void onResponse(ClientChannel channel, Response response);

}
