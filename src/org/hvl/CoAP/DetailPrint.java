package org.hvl.CoAP;

import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;

import org.hvl.CoAP.MediaTypeRegistery;


public class DetailPrint {
	
	
	
	/**
	 * Change a Request into a readable String representation. 
	 * 
	 * @param r the Request
	 * @return the pretty print
	 */
	public static String detailPrint(Request r) {
	
	        StringBuilder sd = new StringBuilder();
	        
	        sd.append("Request Details-------------------------\n");
	        sd.append(String.format("MID    : %d\n", r.getID()));
	        sd.append(String.format("Token  : %s\n", r.getToken()));
	        sd.append(String.format("Type   : %s\n", r.getType().toString()));
	        //sb.append(String.format("Method : %s\n", r.getCode().toString()));
	        //sd.append(String.format("Options: %s\n", r.getOptions(0).toString()));
	        sd.append(String.format("Payload: %d Bytes\n", r.getpayloadSize()));
	        if (r.getpayloadSize() > 0 && MediaTypeRegistery.isPrintable(r.getOptions().getContentFormat())) {
	        
	        	sd.append("---------------------------------------------------------------");
	        	sd.append(r.getPayloadString());
	        }
	        sd.append("-----------------------------------------------");
	
	        return sd.toString();
	}

	
	/**
	 * Change a Response into a readable String representation. 
	 * 
	 * @param r the Response
	 * @return the pretty print
	 */
	public static String detailPrint(Response r) {
	
	        StringBuilder sd = new StringBuilder();
	        
	        sd.append("Response Details ---------------------\n");
	        sd.append(String.format("MID    : %d\n", r.getID()));
	        sd.append(String.format("Token  : %s\n", r.getToken()));
	        sd.append(String.format("Type   : %s\n", r.getType().toString()));
	        //sb.append(String.format("Status : %s\n", r.getCode().toString()));
	        sd.append(String.format("Options: %s\n", r.getOptions().toString()));
	        sd.append(String.format("Payload: %d Bytes\n", r.getpayloadSize()));
	        if (r.getpayloadSize() > 0 && MediaTypeRegistery.isPrintable(r.getOptions().getContentFormat())) {
	        	sd.append("---------------------------------------------------------------\n");
	        	sd.append(r.getPayloadString());
	        	sd.append("\n");
	        }
	        sd.append("-----------------------------------------------------");
	
	        return sd.toString();
	}
	

}
