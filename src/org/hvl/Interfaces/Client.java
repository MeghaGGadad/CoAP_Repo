package org.hvl.Interfaces;

//import org.hvl.Interfaces.ClientChannel;
import org.hvl.CoAPServer.Response;

public interface Client{
	
	public void uponResponse(Channel channel, Response response);
    public void uponConnectionFailed(Channel channel, boolean notReachable, boolean resetByServer);
	

}
