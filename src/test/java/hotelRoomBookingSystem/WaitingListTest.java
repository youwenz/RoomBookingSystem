package hotelRoomBookingSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class WaitingListTest {
	
	private WaitingList wl;
	private static User usermock;
	
	@Before
	public void setUp() {
		wl = new WaitingList();
		usermock = mock(User.class);	
	}
	
	private Object[] parametersForAddWaitingValidValue() {
        return new Object[] {
        		 new Object[] { "VIP"}, // VIP member without exclusive reward
                 new Object[] { "Member"}, // Member with exclusive reward
                 new Object[] { "Member"}, // Member without exclusive reward
                 new Object[] { "Normal"}, // Non-member
        };
    }
	@Test
	@Parameters(method="parametersForAddWaitingValidValue")
	public void testAddWaitingValidValue(String memberType) {
		when(usermock.getMemberType()).thenReturn(memberType);
		wl.addWaiting(usermock);
		List<User> actualList = wl.getWaiting(memberType);
	    assertTrue(actualList.contains(usermock));
	}
	
	
	private Object[] parametersForAddWaitingInvalidValue() {
		return new Object[] {
        		new Object[] {null}, //null member type
        		new Object[] {"" }, //empty member type
        		new Object[] {"unknown" }, //member type other than "VIP", "Member", "Normal"
        };
	}
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method="parametersForAddWaitingInvalidValue")
    public void testAddWaitingInvalidValue(String memberType) {
    	when(usermock.getMemberType()).thenReturn(memberType);
		wl.addWaiting(usermock);
    }

	
	private Object[] parametersForGetWaitingValidValue() {
	    return new Object[] {
	        new Object[] {"VIP"},//VIP member
	        new Object[] {"Member"}, // Member with and without exclusive reward
	        new Object[] {"Normal"} // Non-member
	    };
	}
	@Test
	@Parameters(method="parametersForGetWaitingValidValue")
	public void testGetWaitingValidValue(String memberType) {
		when(usermock.getMemberType()).thenReturn(memberType);
		wl.addWaiting(usermock);
		List<User> actualList = wl.getWaiting(memberType);
	    assertTrue(actualList.contains(usermock));
	}
	
	
	private Object[] parametersForGetWaitingInvalidValue() {
		return new Object[] {
        		new Object[] {null}, //null member type
        		new Object[] {"" }, //empty member type
        		new Object[] {"unknown" }, //member type other than "VIP", "Member", "Normal"
        };
    }
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForGetWaitingInvalidValue")
	public void testGetWaitingInvalidValue(String memberType) {
		when(usermock.getMemberType()).thenReturn(memberType);
		wl.getWaiting(memberType);	 
	}
	
	private Object[] parametersForRemoveWaitingValidValue() {
        // Define different user data along with the expected waiting lists
        return new Object[] {
        		new Object[] {"VIP"}, //VIP member
        		new Object[] {"Member"}, //member without exclusive reward
        		new Object[] {"Member"}, //member with exclusive reward
        		new Object[] {"Normal"} //non-member
        };
    }
	@Test
	@Parameters(method="parametersForRemoveWaitingValidValue")
	public void testRemoveWaitingValidValue(String memberType) {
		when(usermock.getMemberType()).thenReturn(memberType);
		wl.addWaiting(usermock);
		wl.removeWaiting(usermock, memberType);
		assertFalse(wl.getWaiting(memberType).contains(usermock));
	}
	
	
	private Object[] parametersForRemoveWaitingInvalidValue() {
        // Define different user data along with the expected waiting lists
        return new Object[] {
        		new Object[] {null}, //null member type
        		new Object[] {"" }, //empty member type
        		new Object[] {"unknown" }, //member type other than "VIP", "Member", "Normal"
        };
    }
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="parametersForRemoveWaitingInvalidValue")
	public void testRemoveWaitingInvalidValue(String memberType) {
		when(usermock.getMemberType()).thenReturn(memberType);
		wl.addWaiting(usermock);
		wl.removeWaiting(usermock, memberType);
	}
	
	
	
	
}