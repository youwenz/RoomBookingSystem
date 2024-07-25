package hotelRoomBookingSystem;
import java.util.*;


public class Printer {
	
	public void printInfo(User user) 
	{
		 System.out.println("\nBooking Information:");
	        System.out.println("Name: " + user.getName());
	        System.out.println("Member Type: " + user.getMemberType());
	        
	}
}
