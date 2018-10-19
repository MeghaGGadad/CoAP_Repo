package org.hvl.Temp;

public class Environment {
	
	 protected double temp;

	 public Environment(double outsideTemp)
	 {
	  temp = outsideTemp;
	 }

	 public double getTemp()
	 {
	  return temp;
	 }

	 public void update()
	 {
	 }

	 public String report()
	 {
	  return String.format("%7.2f",temp);
	 }
	}


