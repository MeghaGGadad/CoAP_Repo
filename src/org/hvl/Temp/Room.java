package org.hvl.Temp;

public class Room {

	
	 private double lossFactor,area,temp;
	 private Heater myHeater;
	 private Environment myEnvironment;

	 public Room(double roomFactor,double roomArea,double insidetemp,
	      Environment env,Heater heater)
	 {
	  lossFactor=roomFactor;
	  area=roomArea;
	  temp=insidetemp;
	  myEnvironment=env;
	  myHeater=heater;
	 }
	 
	 public void update()
	 {
	  temp-=(temp-myEnvironment.getTemp())*lossFactor;
	  temp+=myHeater.getOutput()/area;
	  myHeater.adjust(temp);
	 }

	 public String report()
	 {
	  return String.format("%7.2f",temp);
	 }

	}

