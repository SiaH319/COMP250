/**
 * COMP250_Assignment 1
 * Retrieve the number of nights and the cost of the hotel reservation
 * Check the equality of two reservations
 * @author Sia Ham (260924883)
 */

public class HotelReservation extends Reservation{
	private Hotel reservationPlace; // place to make the reservation
	private String roomType; // the type of room to reserve
	private int numNights;   // the number of nights to be spent in the hotel.
	private int roomPrice;   // the price (in cents) for one night in a room of the specified type in the specified hotel

	public HotelReservation(String clientName, Hotel reservationPlace, String roomType, int numNights) {
		super(clientName); // create a Reservation
		// initialization
		this.reservationPlace = reservationPlace;
		this.roomType = roomType;
		this.numNights = numNights;
		this.roomPrice = reservationPlace.reserveRoom(roomType);
		//  If such reservation is not possible, then an IllegalArgumentException is raised
	}

	/* retrieve the number of night on the reservation */
	public int getNumOfNights() {
		return this.numNights;
	}

	@Override
	/* retrieves the price to pay for the specified type of room 
	 * given the number of nights indicated in the reservation.*/
	public int getCost() {
		int hotelCost= this.roomPrice*this.numNights; 
		return hotelCost;
	}

	@Override
	/*return true if input matches this 
	 * in type, name, hotel, room type, number of nights, and total cost */
	public boolean equals(Object obj) { 
		if(obj == null) return false;
		if(obj instanceof HotelReservation){ // compare types
			HotelReservation htlrsv = (HotelReservation)obj; // down casting
			return this.reservationName().contentEquals(htlrsv.reservationName()) && // compare names
					this.reservationPlace.equals(htlrsv.reservationPlace) && // compare hotels
					this.roomType.equals(htlrsv.roomType) && // compare room types
					this.numNights == htlrsv.numNights &&  // compare number of nights
					this.roomPrice * this.numNights == htlrsv.roomPrice * htlrsv.numNights; // compare total cost
		}
		else return false;
	}
}
