package org.hvl.Interfaces;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.hvl.CoAP.MessageFormat;




public class CoapBasicSocketHandler implements CoapSocketHandler{
	
	private final static Logger logger = Logger.getLogger(CoapBasicSocketHandler.class);
	private ChannelManager channelManager = null;
	private int localPort;
	private DatagramChannel dgramChannel = null;
	 //protected HashMap<ChannelKey, ClientChannel> clientChannels = new HashMap<ChannelKey, ClientChannel>();
	public CoapBasicSocketHandler(ChannelManager channelManager, int port) throws IOException {
        logger.addAppender(new ConsoleAppender(new SimpleLayout()));
        // ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
        logger.setLevel(Level.WARN);
    	
    	this.channelManager = channelManager;
        //dgramChannel = DatagramChannel.open();
       	//dgramChannel.socket().bind(new InetSocketAddress(port)); //port can be 0, then a free port is chosen 
        this.localPort = 9876;
        //dgramChannel.configureBlocking(false);
       
        //workerThread = new WorkerThread();
       // workerThread.start();
    }
	
	public CoapBasicSocketHandler(ChannelManager channelManager) throws IOException {
        this(channelManager, 9876);
    }
   
	@Override
    public int getLocalPort() {
		return localPort;
	}
	
	//@Override
    /**public void removeClientChannel(ClientChannel channel) {
        clientChannels.remove(new ChannelKey(channel.getRemoteAddress(), channel.getRemotePort()));
    }**/
	
	@Override
    public ClientChannel connect(Client client, InetAddress remoteAddress,
            int remotePort) {
    	if (client == null){
    		return null;
    	}

    	/**if (clientChannels.containsKey(new ChannelKey(remoteAddress, remotePort))){
    		/* channel already exists */
    		//logger.warn("Cannot connect: Client channel already exists");
    		//return null;
    	//}
    	ClientChannel channel = null;
         //ClientChannel channel = new CoapBasicClientChannel(this, client, remoteAddress, remotePort);
    	
    	
        //addClientChannel(channel);
        return channel;
    }

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(MessageFormat msg) {
		// TODO Auto-generated method stub
		
	}
}
