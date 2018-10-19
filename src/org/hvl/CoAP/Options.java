package org.hvl.CoAP;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;



public class Options {
    
	//The current option's number
		private int optionNum;
		
   //The current option's data
		private ByteBuffer buf;
	
	//constructors

		/*This is a constructor for a new option with a given number, based on a
		 * given byte array
		 * 
		 * @param random The byte array
		 * @param num The option number
		 * 
		 * @return A new option with a given number based on a byte array
		 */
		public Options (byte[] random, int num) {
			setValue(random);
			setOptionNum(num);
		}
		
		/*
		 * This is a constructor for a new option with a given number, based on a
		 * given string
		 * @param str The string
		 * @param nr The option number
		 * 
		 * @return A new option with a given number based on a string
		 */
		public Options (String str, int nr) {
			setStringVal(str);
			setOptionNum(nr);
		}
		
		/*
		 * This is a constructor for a new option with a given number, based on a
		 * given integer value
		 * 
		 * @param val The integer value
		 * @param nr The option number
		 * 
		 * @return A new option with a given number based on a integer value
		 */
		public Options (int val, int nr) {
			setIntegerVal(val);
			setOptionNum(nr);
		}
		
		/*
		 * This method sets the current option's data to a given byte array
		 * 
		 * @param value The byte array.
		 */
		public void setValue (byte[] value) {
			this.buf = ByteBuffer.wrap(value);
		}
		
		/*
		 * This method sets the number of the current option
		 * 
		 * @param nr The option number.
		 */
		public void setOptionNum (int num) {
			optionNum = num;
		}
		
		/*
		 * This method sets the data of the current option based on a string input
		 * 
		 * @param str The string representation of the data which is stored in the
		 *            current option.
		 */
		public void setStringVal(String str) {
		  buf = ByteBuffer.wrap(str.getBytes());	
		}
		
		/*
		 * This method sets the data of the current option based on a integer value
		 * 
		 * @param val The integer representation of the data which is stored in the
		 *            current option.
		 */
		public void setIntegerVal(int val) {
			int neededBytes = 4;
			if (val == 0) {
				buf = ByteBuffer.allocate(1);
				buf.put((byte) 0);
			} else {ByteBuffer aux = ByteBuffer.allocate(4);//allocates new buffer
				aux.putInt(val);//writes int val
				for (int i=3; i >= 0; i--) {
					if (aux.get(3-i) == 0x00) {
						neededBytes--;
					} else {
						break;
					}
				}
				buf = ByteBuffer.allocate(neededBytes);
				for (int i = neededBytes - 1; i >= 0; i--) {
					buf.put(aux.get(3-i));
				}
			}
		}
		
		
		/*
		 * This method returns the value of the option's data as integer
		 * 
		 * @return The integer representation of the current option's data
		 */
		public int getIntegerVal () {
			int byteLength = buf.capacity();
			ByteBuffer temp = ByteBuffer.allocate(4);
			for (int i=0; i < (4-byteLength); i++) {
				temp.put((byte)0);
			}
			for (int i=0; i < byteLength; i++) {
				temp.put(buf.get(i));
			}
			
			int val = temp.getInt(0);
			return val;
		}
		
		
		public int getOptionNum() {
		return optionNum;
	}
		
		/*
		 * This method returns the name that corresponds to the option number.
		 * 
		 * @return The name of the option
		 */
		public String getName() {
			return CoAPOptionRegistry.toString(optionNum);
		}

		public Object getDisplayVal() {
			// TODO Auto-generated method stub
			return null;
		}

		/*This method returns the length of the option's data in the ByteBuffer
		 *
		 */ 
		 
		public int getLength() {
			return buf.capacity();
		}

		public String getStringVal() {
			String str = "";
			try {
				str = new String(buf.array(), "UTF8");
			} catch (UnsupportedEncodingException e) {
				System.err.println("String conversion error");
			}
			return str;
		}

	
	

	

	
	

}
