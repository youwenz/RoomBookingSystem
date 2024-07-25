package hotelRoomBookingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.inOrder;

@RunWith(JUnitParamsRunner.class)
public class HotelRoomBookingIntegrationTest {
	static WaitingList waitingList;
	static Printer printer;
	static Scanner inputStream = null;
	static ArrayList<String[]> linesRead;
	static Room room;
	
	@Before
	public static void setupForAllTest() {
		waitingList = new WaitingList();
		room = mock(Room.class);
		
	}

	
	private static Object[] parametersForBookingOperationValidV1() {
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Bob", "VIP", false, 2);
		User user3 = new User("Eve", "VIP", false, 1);
		User user4 = new User("Alice", "Member", false, 2);
		User user5 = new User("Bob", "Member", false, 1);
		User user6 = new User("Eve", "Member", true, 2);
		User user7 = new User("Eve", "Member", true, 1);
		User user8 = new User("Alice", "Normal", false, 1);
		//a VIP book three room
		int[] roomAvailable1 = {3, 3, 3};
		int[] roomExpected1 = {3, 0, 0};
	
		//a VIP book two room
		int[] roomAvailable2 = {3, 3, 3};
		int[] roomExpected2 = {2, 0, 0};
		 
		//a VIP book a room
		int[] roomAvailable3 = {3, 3, 3};
		int[] roomExpected3 = {1, 0, 0};
		
		//a member book 2 room
		int[] roomAvailable4 = {3, 3, 3};
		int[] roomExpected4 = {0, 2, 0};
		
		//a member book 1 room
		int[] roomAvailable5 = {3, 3, 3};
		int[] roomExpected5 = {0, 1, 0};
		
		
		//a member with exclusive reward book 2 rooms
		int[] roomAvailable6 = {3, 3, 3};
		int[] roomExpected6 = {1, 1, 0};
		
		//a member with exclusive reward book 1 room
		int[] roomAvailable7 = {3, 3, 3};
		int[] roomExpected7 = {1, 0, 0};
		
		//a non member book 1 room
		int[] roomAvailable8 = {3, 3, 3};
		int[] roomExpected8 = {0, 0, 1};
		
	    return new Object[] {
	    		new Object[] {user1, roomAvailable1, roomExpected1},
	    		new Object[] {user2,roomAvailable2, roomExpected2},
	    		new Object[] {user3, roomAvailable3, roomExpected3},
	    		new Object[] {user4,roomAvailable4, roomExpected4},
	    		new Object[] {user5,roomAvailable5, roomExpected5},
	    		new Object[] {user6,roomAvailable6, roomExpected6},
	    		new Object[] {user7,roomAvailable7, roomExpected7},
	    		new Object[] {user8,roomAvailable8, roomExpected8},
	        };
	}

	@Parameters(method ="parametersForBookingOperationValidV1")
	@Test
	public void testBookingOperationValidV1(User user, int[] roomAvailable, int[] roomExpected) {
		Booking booking = new Booking(user);
	    
        when(room.checkRoom("VIP")).thenReturn(roomAvailable[0]--);
        when(room.checkRoom("Deluxe")).thenReturn(roomAvailable[1]--);
        when(room.checkRoom("Standard")).thenReturn(roomAvailable[2]--);
        booking.setBooking(waitingList, room);
        verify(room, times(roomExpected[0])).setVipRoom(-1);
        verify(room, times(roomExpected[1])).setDeluxeRoom(-1);
        verify(room, times(roomExpected[2])).setStandardRoom(-1);
	}
	
	private static Object[] parametersForAddWaitingGetWaitingValid() {
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Bob", "Member", false, 1);
		User user3 = new User("Eve", "Member", true, 2);
		User user4 = new User("Felix", "Normal", false, 1);
		String memberType1 = "VIP";
		String memberType2 = "Member";
		String memberType3 = "Normal";
		
		 return new Object[] {
		    	new Object[] {user1, memberType1},
		    	new Object[] {user2, memberType2},
		    	new Object[] {user3, memberType2},
		    	new Object[] {user4, memberType3},
		 };
	}
	@Test
	@Parameters(method="parametersForAddWaitingGetWaitingValid")
	public void testAddGetWaitingValid(User user, String memberType) {
		waitingList.addWaiting(user);
		List<User> actualList = waitingList.getWaiting(memberType); 
		assertTrue(actualList.contains(user));	
	}
	
	private static Object[] parametersForAddWaitingInvalid() {
		User user1 = new User("Alice", null, false, 3);
		User user2 = new User("Bob", "", false, 3);
		User user3 = new User("Eve", "InvalidType", false, 1);
		String memberType1 = null;
		String memberType2 = "";
		String memberType3 = "InvalidType";
		
		 return new Object[] {
		    	new Object[] {user1, memberType1}, //add user into null memberType
		    	new Object[] {user2, memberType2}, //add user into empty memberType
		    	new Object[] {user3, memberType2}, //add user into invalid memberType
		 };
	}
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForAddWaitingInvalid")
	public void testAddWaitingInvalid(User user, String memberType) {
		waitingList.addWaiting(user);	
	}
	 
	private static Object[] parametersForGetWaitingInvalid() {
		User user1 = new User("Alice", null, false, 3);
		User user2 = new User("Bob", "", false, 3);
		User user3 = new User("Eve", "InvalidType", false, 1);
		String memberType1 = null;
		String memberType2 = "";
		String memberType3 = "InvalidType";
		
		 return new Object[] {
		    	new Object[] {user1, memberType1}, //add user into null memberType
		    	new Object[] {user2, memberType2}, //add user into empty memberType
		    	new Object[] {user3, memberType2}, //add user into invalid memberType
		 };
	}
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForGetWaitingInvalid")
	public void testGetWaitingInvalid(User user, String memberType) {
		waitingList.getWaiting(memberType); 	
	}
	
	private static Object[] parametersForRemoveWaitingValid() {
		String filename = "UserData.txt";
		String[] tokens = null;
		
		try {
			inputStream = new Scanner(new File(filename));
		}catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + filename);
			System.exit(0);
		}
		while(inputStream.hasNext()) {
			String singleLine = inputStream.next();
			tokens = singleLine.split(",");
		}
		User user1 = new User("Alice", "VIP", false, 3);
		User user2 = new User("Bob", "Member", false, 1);
		User user3 = new User("Eve", "Member", true, 2);
		User user4 = new User("Felix", "Normal", false, 1);
		String memberType1 = "VIP";
		String memberType2 = "Member";
		String memberType3 = "Normal";
		
		 return new Object[] {
		    	new Object[] {user1, memberType1},
		    	new Object[] {user2, memberType2},
		    	new Object[] {user3, memberType2},
		    	new Object[] {user4, memberType3},
		 };
	}
	@Test
	@Parameters(method="parametersForRemoveWaitingValid")
	public void testRemoveWaitingValid(User user, String memberType) {
		waitingList.addWaiting(user);
		waitingList.removeWaiting(user, memberType);
		List<User> actualList = waitingList.getWaiting(memberType); 
		assertFalse(actualList.contains(user));	
	}
	
	private static Object[] parametersForRemoveWaitingInvalid() {
		User user1 = new User("Alice", null, false, 3);
		User user2 = new User("Bob", "", false, 3);
		User user3 = new User("Eve", "InvalidType", false, 1);
		String memberType1 = null;
		String memberType2 = "";
		String memberType3 = "InvalidType";
		
		 return new Object[] {
		    	new Object[] {user1, memberType1}, //add user into null memberType
		    	new Object[] {user2, memberType2}, //add user into empty memberType
		    	new Object[] {user3, memberType2}, //add user into invalid memberType
		 };
	}
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForRemoveWaitingInvalid")
	public void testRemoveWaitingInvalid(User user, String memberType) {
		waitingList.addWaiting(user);
		waitingList.removeWaiting(user, memberType);	
	}
	
}

