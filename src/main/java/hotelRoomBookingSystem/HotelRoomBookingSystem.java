package hotelRoomBookingSystem;

import java.io.*;
import java.util.*;

public class HotelRoomBookingSystem {
	 static User user = new User("Alice", "Member", false, 1);
	 static Room rooms = new Room(3,3,3);
	 static WaitingList waitingList = new WaitingList();
     static Booking booking;
     static Printer printer = new Printer();
     
    public static void main(String[] args) throws IOException{
    	processBooking();

    	menu(true);
    	
    }

    //to be implemented in test classes
//    public static List<User> readUserData(String fileName) throws IOException {
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//        	String line;
//            int lineNumber = 0;
//            while ((line = br.readLine()) != null) {
//                lineNumber++;
//                String[] data = line.split(",");
//
//                if (data.length != 4) {
//                    throw new IllegalArgumentException("Invalid number of fields in line " + lineNumber + ": " + line);
//                }
//
//                try {
//                    String username = data[0].trim();
//                    String userType = data[1].trim();
//                    boolean hasExclusiveReward = Boolean.parseBoolean(data[2].trim());
//                    int numRoomsBooked = Integer.parseInt(data[3].trim());
//
//                    // Validate userType
//                    
//
//                    User user = new User(username, userType, hasExclusiveReward, numRoomsBooked);
//                    users.add(user);
//                } catch (NumberFormatException e) {
//                    throw new IllegalArgumentException("Invalid number of rooms booked in line " + lineNumber + ": " + line);
//                }
//            }
//        } catch (IOException e) {
//            throw new IOException("Error reading file: " + e.getMessage());
//        }
//
//
//        return users;
//    }
    
    //temporary testing only
    public static void processBooking() throws IOException {
    	
      
       booking = new Booking(user);
       rooms = booking.setBooking(waitingList, rooms);
        System.out.println("Available VIP rooms: " + rooms.getVipRoom());
        System.out.println("Available Deluxe rooms: " + rooms.getDeluxeRoom());
        System.out.println("Available Standard rooms: " + rooms.getStandardRoom());
    }
    
    public static void cancelBooking(String name){
		if(user.getName().equals(name)) {
			booking.cancelBooking(waitingList, rooms, user);
		}
	
    }
    
    public static void printBookingInfo() {
    	
    }
    	
    
    
    public static void menu(boolean continueRunning){
        while (continueRunning) {
            System.out.println("\nWelcome to the Hotel Room Booking System!");
            System.out.println("1. Print booking info");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Exit");
            System.out.print("Please choose an option (1-3): ");
            
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nPrint booking info:");
                    printBookingInfo();
                    break;
                case 2:
                    System.out.println("\nCanceling a Booking:");
                    System.out.println("\nEnter your name:");
                    String name = sc.nextLine();
                    cancelBooking(name);
                    break;
                case 3:
                    System.out.println("\nExiting the Program.");
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
    }
    }
    
    
}


