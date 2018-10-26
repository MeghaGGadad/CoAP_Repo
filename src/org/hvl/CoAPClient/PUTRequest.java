package org.hvl.CoAPClient;

import org.hvl.CoAP.CoAPCodeRegistries;

public class PUTRequest extends Request {
	public PUTRequest(){
		super(CoAPCodeRegistries.PUT, true);
	}
	public void dispatch(HandleRequest handle) {
		handle.performPUT(this);
	}
}
