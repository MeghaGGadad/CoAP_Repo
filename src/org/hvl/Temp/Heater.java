package org.hvl.Temp;

public class Heater {
	
	
	 private boolean on;
	 private double output,onTemp,overheat;

	 public Heater(double heaterOutput,double heaterOnTemp,double heaterOverheat)
	 {
	  on=false;
	  output=heaterOutput;
	  onTemp=heaterOnTemp;
	  overheat=heaterOverheat;
	 }

	 public double getOutput()
	 {
	  if(on)
	     return output;
	  else
	     return 0.0;
	 }
	 
	 public void adjust(double temp)
	 {
	  if(temp>onTemp+overheat)
	     on=false;
	  else if(temp<onTemp)
	     on=true;
	 }

	 public String report()
	 {
	  if(on)
	     return "Heater is ON";
	  else
	     return "Heater is OFF";
	 }
	}


