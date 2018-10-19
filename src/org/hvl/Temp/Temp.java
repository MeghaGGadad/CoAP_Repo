package org.hvl.Temp;

import java.util.Scanner;

public class Temp {
	
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		double currentTemp = 10;

		int 
		    simulations = 0, 
		    InputTemp   = 0, 
		    choice      = 0;

		double
		    setTemp   = currentTemp, 
		    PreviousTemp    = currentTemp,
		    lowestTemp  = 0.0, 
		    highestTemp = 70.0;
		   // sum         = 0;

		 System.out.println("Select Season");
		 System.out.println("Press 1 for winter");
		 System.out.println("Press 2 for spring");
		 System.out.println("Press 3 for summer");
		 System.out.println("Press 4 for fall");
		 System.out.println("Press 5 to exit");
		 System.out.println("Enter number of choice:");
		 choice = input.nextInt();
		 

		 if(choice==5) System.exit(0);

		 System.out.println("Enter number of simulation:");

		 simulations = input.nextInt();

		 for(int i = 1; i <= simulations; i++){

		 System.out.println("Starting simulation " + i);


		if (choice == 1){
		                  do{
		                	  
		                	  
		                       System.out.printf("Current room Temperature in winter: %.2f\n ", +currentTemp);
		                       
		                       System.out.println("Set room Temperature in winter:");
		                       setTemp = input.nextInt();
		                       if (setTemp > currentTemp)
		                       System.out.printf("Room Temperature is increased to %.2f:\n ", +(currentTemp+setTemp)); 
		                       if (setTemp < currentTemp)
		                       System.out.printf("Room Temperature is decreased %.2f:\n ",+(currentTemp-setTemp));
		                       }while( setTemp > 20 && setTemp < 40);   //end while
		                }//end if


		if (choice == 2){
		                  do{
		                	  System.out.printf("Current room Temperature in the spring: %.2f\n ", +currentTemp);
		                       
		                       System.out.println("Set room Temperature in spring:");
		                       setTemp = input.nextInt();
		                       if (setTemp > currentTemp)
		                       System.out.printf("Room Temperature is increased to %.2f:\n ", +(currentTemp+setTemp)); 
		                       if (setTemp < currentTemp)
		                       System.out.printf("Room Temperature is decreased %.2f:\n ",+(currentTemp-setTemp));
		                       }while( setTemp > 40 && setTemp < 70);   //end while
		                        //end while
		                }//end if


		if (choice == 3){
		                  do{
		                	  System.out.printf("Current room Temperature in the summer: %.2f\n ", +currentTemp);
		                       
		                       System.out.println("Set room Temperature in summer:");
		                       setTemp = input.nextInt();
		                       if (setTemp > currentTemp)
		                       System.out.printf("Room Temperature is increased to %.2f:\n ", +(currentTemp+setTemp)); 
		                       if (setTemp < currentTemp)
		                       System.out.printf("Room Temperature is decreased %.2f:\n ",+(currentTemp-setTemp));
		                       }while( setTemp > 70 && setTemp < 90);   //end while
		                       
		                    
		                }//end if


		if (choice == 4){
		                  do{
		                	  System.out.printf("Current room Temperature in fall: %.2f\n ", +currentTemp);
		                       
		                       System.out.println("Set room Temperature in fall:");
		                       setTemp = input.nextInt();
		                       if (setTemp > currentTemp)
		                       System.out.printf("Room Temperature is increased to %.2f:\n ", +(currentTemp+setTemp)); 
		                       if (setTemp < currentTemp)
		                       System.out.printf("Room Temperature is decreased %.2f:\n ",+(currentTemp-setTemp));
		                       }while( setTemp > 40 && setTemp < 60);   //end while
		                    
		                }//end if   



		}//end for

		 System.out.println("Room Temperature set to: " +setTemp); 
		 System.out.println("Previous Temperature was: " +PreviousTemp);
		 System.out.println("Lowest Temperature: " +lowestTemp);
		 System.out.println("Highest Temperature: " +highestTemp);
		 //System.out.println("Sum: " +sum);
		 //System.out.println("Average: " +sum/simulations);


		 System.out.println("The program will now exit");

		}//end main

}
