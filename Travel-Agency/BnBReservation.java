/**
 * COMP250_Assignment 1
 * BnBReservation derived from the HotelReservation class
 * Create HotelResrvation
 * Retrieve the total cost of the reservation including breakfast
 * @author Sia Ham (260924883)
 */


public class BnBReservation extends HotelReservation{

	public BnBReservation(String clientName, Hotel reservationPlace, String roomType, int numNights) {
		super(clientName, reservationPlace, roomType, numNights); // create an HotelReservation
	}
	
	/* retrieve the total cost of the reservation including breakfast */
	public int getCost() {
		int totalcost = super.getCost() + super.getNumOfNights() * 10* 100;	
		// total cost of hotel reservation + (number of nights) * (10$ breakfast / night) * (100cents/$)
		return totalcost;
	}
}
