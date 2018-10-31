package org.hvl.Interfaces;

import java.net.InetAddress;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPClient.Request;



public interface Channel {
	
	public void sendMessage(MessageFormat response);

	/*TODO: close when finished*/
    public void close();
    
    public InetAddress getRemoteAddress();

    public int getRemotePort();
    
    /* handles an incomming message */
    public void handleMessage(MessageFormat message);
    
    
	public void lostConnection(boolean notReachable, boolean resetByServer);
	
	public static Request createRequest(boolean reliable, CoAPCodeRegistries.Code requestCode) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
