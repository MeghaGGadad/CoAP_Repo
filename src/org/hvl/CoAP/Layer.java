package org.hvl.CoAP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public abstract class Layer implements MessageReceiver {
	
	private List<MessageReceiver> receivers;
	private int incMessagesSent;
	private int incMessagesReceived;
	
	public void sendMessage(MessageFormat msg) throws IOException {

		if (msg != null) {
			performSendMessage(msg);
			++incMessagesSent;
		}
	}
	
	@Override
	public void receiveMessage(MessageFormat msg) {

		if (msg != null) {
			++incMessagesReceived;
			performReceiveMessage(msg);
		}
	}
	
	protected abstract void performSendMessage(MessageFormat msg)
		throws IOException; 
	
	protected abstract void performReceiveMessage(MessageFormat msg);
	
	protected void deliverMessage(MessageFormat msg) {

		// pass message to registered receivers
		if (receivers != null) {
			for (MessageReceiver receiver : receivers) {
				receiver.receiveMessage(msg);
			}
		}
	}
	
   public int getNumMessagesSent() {
		return incMessagesSent;
	}
	
	public int getNumMessagesReceived() {
		return incMessagesReceived;
	}
	
	private Layer layer;

	public void sendMessageOverLayer(MessageFormat msg) throws IOException {
		
		// check if layer assigned
		if (layer != null) {
			
			layer.sendMessage(msg);
		} //else {
			
			
			//System.out.printf("[%s] ERROR: No layer present", 
				//getClass().getName());
		//}
	}

}

