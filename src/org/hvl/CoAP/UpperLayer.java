package org.hvl.CoAP;

import java.io.IOException;



public abstract class UpperLayer extends Layer {
	
	
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
	
	public void setLowLayer(Layer layer) {
		
		// unsubscribe from old layer
		if (layer != null) {
			layer.unregisterReceiver(this);
		}
		
		// set new lower layer
	     layer = layer;
		
		// subscribe to new lower layer
		if (layer != null) {
			layer.registerReceiver(this);
		}
	}
	
	public Layer getLowLayer() {
		return layer;
	}
	
	
}
