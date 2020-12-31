/**
 * COMP250_Assignment 1
 * Retrieve the type and the cost of the Room
 * Check, change, find the availability of the room
 * @author Sia Ham (260924883)
 */

public class Room {
	private String roomType; // the type of the room
	private int roomPrice;   // the price of the room (in cents)
	private boolean isRoom;  // indicates whether or not the room is available

	/* check whether the input match one of supported room types (double, queen, king) 
	and assign the corresponding value of the price*/
	public Room(String roomType) {
		this.roomType = roomType;

		if(this.roomType.equalsIgnoreCase("double") || 
				this.roomType.equalsIgnoreCase("queen")  || 
				this.roomType.equalsIgnoreCase("king")) 
		{	
			this.isRoom=true;
			if(this.roomType.equalsIgnoreCase("double") )  roomPrice=90*100; // in cents
			else if(this.roomType.equalsIgnoreCase("queen")) roomPrice=110*100; // in cents
			else if(this.roomType.equalsIgnoreCase("king")) roomPrice=150*100; // in cents
		}
		else throw new IllegalArgumentException(roomType+" cant'be be created");
	}

	public Room(Room room) {
		this.roomType = room.roomType;
		this.isRoom = room.isRoom;
		this.roomPrice = room.roomPrice;
	}

	/* retrieve the type of the room */
	public String getType() {
		return this.roomType;
	}

	/* retrieve the price of the room */
	public int getPrice() {
		return this.roomPrice;
	}

	/* change the availability of the room */
	public void changeAvailability() {
		if(this.isRoom)  isRoom=false;
		// if room is available, make it unavailable 

		else this.isRoom=true;
		// if room is unavailable, make it available
	}

	/* find first available room in the array of the indicated type */
	public static Room findAvailableRoom(Room[] rooms, String roomType) {
		// traverse in the array
		for (int i=0 ; i < rooms.length; i++) {
			if (rooms[i].getType() == roomType && 
					rooms[i].isRoom == true) { 
				// check whether such room exists (such type of room exists and it is not occupied)
				return rooms[i];
			}
		}
		return null; //no such room exists
	}

	/* make the first unavailable room in the array of the indicated type available again */
	public static boolean makeRoomAvailable (Room[] rooms, String roomType) {
		int i;
		// traverse in the array
		for (i=0 ; i < rooms.length; i++) {
			if (rooms[i].roomType == roomType && 
					rooms[i].isRoom == false) { 
				// if such room exists (such type of room exists and it is not occupied)
				break;
			}
		}
		if (i == rooms.length) return false; // if i has traversed all array = no room available

		else {
			rooms[i].changeAvailability();
			return true;}
	}
}
