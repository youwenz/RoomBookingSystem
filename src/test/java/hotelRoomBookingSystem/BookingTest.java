package hotelRoomBookingSystem;

import static org.junit.Assert.*;
import org.junit.*;
import junitparams.*;
import java.util.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class BookingTest {
	private static Booking booking;
    private static User user;
    private static Room room;
    private static WaitingList waitingList;

	
	@Before
    public static void setUp() {
        user = mock(User.class);
        room = mock(Room.class);
        waitingList = mock(WaitingList.class);
        booking = new Booking(user);
    }
	
	public static Object[] parametersForBookingTestValid() {
		int[] roomAvailable1 = {3,3,3}; 
		int[] roomAllocated1 = {1,0,0}; //how many times the set room is called
		
		int[] roomAvailable2 = {3,3,3}; 
		int[] roomAllocated2 = {2,0,0}; 

		int[] roomAvailable3 = {3,3,3}; 
		int[] roomAllocated3 = {3,0,0};
		
		int[] roomAvailable4 = {3,3,3}; 
		int[] roomAllocated4 = {1,0,0};
		
		int[] roomAvailable5 = {3,3,3}; 
		int[] roomAllocated5 = {0,1,0};
		
		int[] roomAvailable6 = {3,3,3}; 
		int[] roomAllocated6 = {1,1,0};
		
		int[] roomAvailable7 = {3,3,3}; 
		int[] roomAllocated7 = {0,0,1};

		return new Object[] {
				//VIP request 1 room
				new Object[] {"VIP",false, 1, roomAvailable1, roomAllocated1},
				//VIP request 2 rooms
				new Object[] {"VIP",false, 2, roomAvailable2, roomAllocated2},
				//VIP request 3 rooms
				new Object[] {"VIP",false, 3, roomAvailable3, roomAllocated3},
				//Member with exclusive reward request 1 room
				new Object[] {"Member",true, 1, roomAvailable4, roomAllocated4},
				//Member without exclusive reward request 1 rooms
				new Object[] {"Member",false, 1, roomAvailable5, roomAllocated5},
				//Member with exclusive reward request 1 room
				new Object[] {"Member",true, 2, roomAvailable6, roomAllocated6},
				//non-member request 1 room
				new Object[] {"Normal",false, 1, roomAvailable7, roomAllocated7},
		};
	}
	
	@Parameters(method = "parametersForBookingTestValid")
	@Test
	public static void testBookingTestValid(String memberType, boolean hasExclReward, int roomsBooked, int[] roomAvailable, int[] roomAllocated) {
		when(user.getMemberType()).thenReturn(memberType);
		when(user.hasExclReward()).thenReturn(hasExclReward).thenReturn(false);
		when(user.getNumRoomsBooked()).thenReturn(roomsBooked);
		
        when(room.checkRoom("VIP")).thenReturn(roomAvailable[0]--);
        when(room.checkRoom("Deluxe")).thenReturn(roomAvailable[1]--);
        when(room.checkRoom("Standard")).thenReturn(roomAvailable[2]--);
        
        
        booking.setBooking(waitingList, room);
        verify(room, times(roomAllocated[0])).setVipRoom(-1);
        verify(room, times(roomAllocated[1])).setDeluxeRoom(-1);
        verify(room, times(roomAllocated[2])).setStandardRoom(-1);
	}
	
	
	public static Object[] parametersForBookingTestInvalid() {
		int[] roomAvailable = {3,3,3}; 
	
		
		return new Object[] {
				//VIP request has exclusive reward
				new Object[] {"VIP",true, 1, roomAvailable},
				//VIP request has exclusive reward
				new Object[] {"VIP",true, 2, roomAvailable},
				//VIP request has exclusive reward
				new Object[] {"VIP",true, 3, roomAvailable},
				//VIP member request 4 rooms
				new Object[] {"VIP",false, 4, roomAvailable},
				//VIP member request 0 room
				new Object[] {"VIP",false, 0, roomAvailable},
				//member request 3 rooms
				new Object[] {"Member",false, 3, roomAvailable},
				//Member request 0 room
				new Object[] {"Member",false, 0, roomAvailable},
				//Non-member has exclusive reward
				new Object[] {"Normal",true, 1, roomAvailable},
				//Non-member request 0 room
				new Object[] {"Normal",false, 0, roomAvailable},
				//non-member request 2 rooms
				new Object[] {"Normal",false, 2, roomAvailable},
		};
	}
	
	@Parameters(method = "parametersForBookingTestInvalid")
	@Test(expected = IllegalArgumentException.class)
	public static void testBookingInvalid(String memberType, boolean hasExclReward, int roomsBooked, int[] roomAvailable) {
		when(user.getMemberType()).thenReturn(memberType);
		when(user.hasExclReward()).thenReturn(hasExclReward).thenReturn(false);
		when(user.getNumRoomsBooked()).thenReturn(roomsBooked);
		
        when(room.checkRoom("VIP")).thenReturn(roomAvailable[0]--);
        when(room.checkRoom("Deluxe")).thenReturn(roomAvailable[1]--);
        when(room.checkRoom("Standard")).thenReturn(roomAvailable[2]--);
        
        
        booking.setBooking(waitingList, room);
	}
	
	
}