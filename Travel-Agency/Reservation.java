/**
 * COMP250_Assignment 1
 * Retrieve the client name and the cost of the reservation
 * Check the equality of two reservations
 * @author Sia Ham (260924883)
 */

public abstract class Reservation {
	private String clientName;
	
	public Reservation(String clientName) {
		// initialization
		this.clientName = clientName;
	}
	
	/* retrieve the name of the reservation */
	public final String reservationName () {
		return this.clientName;
	}
	
	/* determine the cost depending on the type of reservation */
	public abstract int getCost();
	
	/* check the equality of two reservations depending on the type of reservation */
	public abstract boolean equals(Object k);
}
