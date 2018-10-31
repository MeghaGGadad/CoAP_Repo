package org.hvl.Interfaces;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Random;

import org.hvl.CoAP.MessageFormat;

import org.hvl.CoAPServer.SimpleServer;


public class CoapChannelManager implements ChannelManager{
	
	
		
	    private int MessageId;
	    private static CoapChannelManager instance;
	    private HashMap<Integer, SocketInformation> socketMap = new HashMap<Integer, SocketInformation>();
	    Server serverListener = null;
	
	
	    

	    public synchronized static ChannelManager getInstance() {
	        if (instance == null) {
	            instance = new CoapChannelManager();
	        }
	        return instance;
	    }
	    
	    

	    public final static int MESSAGE_ID_MIN = 0;
	    public final static int MESSAGE_ID_MAX = 65535;
	    
	    /**
	     * Creates a new, message id for a new COAP message
	     */
	    @Override
	    public synchronized int getNewMID() {
	        if (MessageId < MESSAGE_ID_MAX) {
	            ++MessageId;
	        } else
	            MessageId = MESSAGE_ID_MIN;
	        return MessageId;
	    }

	    @Override
	    public synchronized void initRandom() {
	        // generate random 16 bit messageId
	        Random random = new Random();
	        MessageId = random.nextInt(MESSAGE_ID_MAX + 1);
	    }

	   
	    //@Override
	    public void createServerListener(Server serverListener, int localPort) {
	        if (!socketMap.containsKey(localPort)) {
	            
				SocketInformation socketInfo = null;
				socketMap.put(localPort, socketInfo);
	        } else {
	        	/*TODO: raise exception: address already in use */
	        	throw new IllegalStateException();
	        }
	    }

	    @Override
	    public Channel connect(Client client, InetAddress addr, int port) {
	    	CoapSocketHandler socketHandler = null;
			try {
				socketHandler = new BasicSocketHandler(this);
				SocketInformation sockInfo = new SocketInformation(socketHandler, null); 
				//socketMap.put(socketHandler.getLocalPort());
				return socketHandler.connect(client, addr, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private class SocketInformation {
			public CoapSocketHandler socketHandler = null;
			public SimpleServer serverListener = null;
			public SocketInformation(CoapSocketHandler socketHandler,
					SimpleServer serverListener) {
				super();
				this.socketHandler = socketHandler;
				this.serverListener = serverListener;
			}
		}

		@Override
		public void setMessageId(int MId) {
			this.MessageId = MId;
		}

		

		public ServerChannel createServerChannel(CoapSocketHandler socketHandler, MessageFormat message, InetAddress addr,
				int port) {
			// TODO Auto-generated method stub
			return null;
		}

		

	    
}  
	    
	


