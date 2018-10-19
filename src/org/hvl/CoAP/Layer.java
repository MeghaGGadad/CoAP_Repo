package org.hvl.CoAP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public abstract class Layer implements MessageReceiver {
	
	private List<MessageReceiver> receivers;
	private int numMessagesSent;
	private int numMessagesReceived;
	
	public void sendMessage(MessageFormat msg) throws IOException {

		if (msg != null) {
			doSendMessage(msg);
			++numMessagesSent;
		}
	}
	
	@Override
	public void receiveMessage(MessageFormat msg) {

		if (msg != null) {
			++numMessagesReceived;
			doReceiveMessage(msg);
		}
	}
	
	protected abstract void doSendMessage(MessageFormat msg)
		throws IOException; 
	
	protected abstract void doReceiveMessage(MessageFormat msg);
	
	protected void deliverMessage(MessageFormat msg) {

		// pass message to registered receivers
		if (receivers != null) {
			for (MessageReceiver receiver : receivers) {
				receiver.receiveMessage(msg);
			}
		}
	}
	
	public void registerReceiver(MessageReceiver receiver) {
		
		// check for valid receiver
		if (receiver != null && receiver != this) {
			
			// lazy creation of receiver list
			if (receivers == null) {
				receivers = new ArrayList<MessageReceiver>();
			}
			
			// add receiver to list
			receivers.add(receiver);
		}
	}
	
	public void unregisterReceiver(MessageReceiver receiver) {
		
		// remove receiver from list
		if (receivers != null) {
			receivers.remove(receiver);
		}
	}
	
	public int getNumMessagesSent() {
		return numMessagesSent;
	}
	
	public int getNumMessagesReceived() {
		return numMessagesReceived;
	}
	
	

}

