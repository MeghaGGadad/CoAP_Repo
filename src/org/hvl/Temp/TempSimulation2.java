package org.hvl.Temp;

public class TempSimulation2 {

	public static void main(String[] args)
	 {
	  double outsideTemp=18.0,insideTemp=20.0;
	  double heaterOutput=30.0;
	  double heaterOnTemp=22.0,overheat=2.0;
	  double roomFactor=0.05;
	  double roomArea=50.0;
	  int simulationtime=10;

	  Environment env = new Environment(outsideTemp);
	  Heater heater = new Heater(heaterOutput,heaterOnTemp,overheat);
	  Room room = new Room(roomFactor,roomArea,insideTemp,env,heater);
	  
	  for(int i=0;i<simulationtime;i++)
	     {
	      room.update();
	      env.update();
	      System.out.printf(" %3d",i);
	      System.out.print(" Environment Temp--"+env.report());
	      System.out.print(" Room Temp--"+room.report());
	      System.out.println("  "+heater.report());
	     }
	  
	 }
}
