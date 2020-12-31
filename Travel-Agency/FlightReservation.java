/**
 * COMP250_Assignment 1
 * Retrieve the cost of the flight reservation
 * Check the equality of two flight reservation
 * @author Sia Ham (260924883)
 */

public class FlightReservation extends Reservation {
	private Airport departure; // the place of departure
	private Airport arrival;   // the place of arrival


	public FlightReservation (String name, Airport departure, Airport arrival) {
		super(name); // create a Reservation
		// initialization
		this.departure = departure;
		this.arrival = arrival;

		if (departure.equals(arrival)) { // check if two airports are the same
			throw new IllegalArgumentException("Given depareture and arrival airports are the same");
		}
	}

	/* retrive the cost of the reservation in cents. 
	 * The cost is computed in the following ways:
	 *  fuels cost + aiports fees + $53.75 
	 *  fuel cost = (distances between airports) * (gallon of fuel /167.52 km) * (1.24$/gallon of fuel)
	 *  The cost is then rounded up to the nearest cent */
	public int getCost() {
		double airportDistance=Airport.getDistance(this.arrival,this.departure);  // distance between two airports
		double fuelCost=(airportDistance)/(167.52)*1.24*100; // cost of the fuel in cents
		double airportFee=53.75*100; // airport fee in cents
		double realCost= fuelCost+airportFee+this.departure.getFees()+this.arrival.getFees(); // total cost
		int roundedUpCost = (int)(Math.ceil(realCost));
		return roundedUpCost;
	}
	
	/* return true if input matches this in type, name, and airports  */
	public boolean equals(Object obj) { 
		if(obj == null) return false;
		if(obj instanceof FlightReservation) {
			// check whether the given reservation is flight reservation or not{
			FlightReservation flrsv = (FlightReservation)obj; // down casting
			return this.reservationName().contentEquals(flrsv.reservationName()) &&  // compare names
					this.arrival.equals(flrsv.arrival) &&  // compare arrivals
					this.departure.equals(flrsv.departure); // compares departures
		}
		return false;
	}
}
