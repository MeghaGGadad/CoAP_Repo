package org.hvl.Interfaces;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAPClient.Request;

public interface ClientChannel extends Channel{
	
	public static Request createRequest(boolean reliable, CoAPCodeRegistries.Code requestCode) {
		// TODO Auto-generated method stub
		return null;
	}
    public void setTrigger(Object o);
    public Object getTrigger();

}
