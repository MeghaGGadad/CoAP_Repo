package org.hvl.CoAP;

import java.io.IOException;



public abstract class UpperLayer extends Layer {
	
	
	private Layer lowlayer;

	public void sendMessageOverLowLayer(MessageFormat msg) throws IOException {
		
		// check if layer assigned
		if (lowlayer != null) {
			
			lowlayer.sendMessage(msg);
		} //else {
			
			
			//System.out.printf("[%s] ERROR: No layer present", 
				//getClass().getName());
		//}
	}
	
	public void setLowLayer(Layer layer) {
		
		// unsubscribe from old layer
		if (lowlayer != null) {
			lowlayer.unregisterReceiver(this);
		}
		
		// set new lower layer
		lowlayer = layer;
		
		// subscribe to new lower layer
		if (layer != null) {
			lowlayer.registerReceiver(this);
		}
	}
	
	public Layer getLowLayer() {
		return lowlayer;
	}
	
	
}
