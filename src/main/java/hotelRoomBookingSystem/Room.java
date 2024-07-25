package hotelRoomBookingSystem;

public class Room {
	private int vipRoom;
	private int deluxeRoom;
	private int standardRoom;
	
	public Room(int vipRoom, int deluxeRoom,int standardRoom) {
		this.vipRoom=vipRoom;
		this.deluxeRoom=deluxeRoom;
		this.standardRoom=standardRoom;
	}
	
	public Room() {
		this.vipRoom=0;
		this.deluxeRoom=0;
		this.standardRoom=0;
	}
	
	public int getVipRoom() {
		return vipRoom;
	}
	public void setVipRoom(int value) {
		vipRoom+= value;
	}
	public int getDeluxeRoom() {
		return deluxeRoom;
	}
	public void setDeluxeRoom(int value) {
		deluxeRoom+= value;
	}
	public int getStandardRoom() {
		return standardRoom;
	}
	public void setStandardRoom(int value) {
		standardRoom+= value;
	}
	public void clearRoom() {
		this.vipRoom=0;
		this.deluxeRoom=0;
		this.standardRoom=0;
	}
	
	public int checkRoom(String room_type) {
        switch (room_type) {
            case "VIP":
                return vipRoom;
            case "Deluxe":
                return deluxeRoom;
            case "Standard":
                return standardRoom;
            default:
            	throw new IllegalArgumentException("invalid member type.");
        }
    }
	}
