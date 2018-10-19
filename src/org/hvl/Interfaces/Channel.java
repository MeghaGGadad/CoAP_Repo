package org.hvl.Interfaces;

import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;



public interface Channel {
	
	public void sendMessage(MessageFormat response);

	/*TODO: close when finished, & abort()*/
    public void close();
    
    public InetAddress getRemoteAddress();

    public int getRemotePort();
    
    /* handles an incomming message */
    public void handleMessage(MessageFormat message);
    
    
	public void lostConnection(boolean notReachable, boolean resetByServer);
	
	
	


}
