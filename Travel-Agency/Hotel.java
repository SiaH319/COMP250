/**
 * COMP250_Assignment 1
 * Reserve or cancel the room
 * @author Sia Ham (260924883)
 */

public class Hotel {
	private String hotleName; // the name of the hotel
	private Room[] rooms;	  // the rooms in the hotel.
	
	public Hotel(String hotleName,Room[] rooms) {
		this.hotleName=hotleName;
		this.rooms = new Room[rooms.length];
		//deep copy of the input array
		for(int i=0; i<rooms.length;i++) {
			this.rooms[i]=new Room(rooms[i]); 
		}
	}

	/* changes the availability of the first available room of the specified type in the hotel */
	public int reserveRoom(String roomType) {
		Room availableRoom= Room.findAvailableRoom(this.rooms, roomType);
		if(availableRoom == null) throw new IllegalArgumentException("no such room is available");
	
		else {
			availableRoom.changeAvailability();
			return availableRoom.getPrice();
		}
	}

	/* makes a room of the given room type available again */
	public boolean cancelRoom(String roomType) {
		Boolean availableAgain = Room.makeRoomAvailable(this.rooms, roomType);
		//  returns true if it operation was possible, false otherwise
		return availableAgain;
		
	}
}
