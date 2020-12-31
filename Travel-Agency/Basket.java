/**
 * COMP250_Assignment 1
 * Create a basket in order to represent a list of reservations
 * Reservation can be added or removed from the basket
 * Basket allows access to retrieve the number of reservations and total cost in the basket
 * Basket can be cleared
 * @author Sia Ham (260924883)
 */

public class Basket { // class representing a list of reservations
	private Reservation[] reservations;
	
	public Basket() {
		this.reservations= new Reservation[0];
		// array initialization with no reservations
	}
	
	/* a shallow copy of the array of Reservations of the basket
	 * containing all the reservations in the basket in the same order in which they were added */
	public Reservation[] getProducts() {
		Reservation[] reservationsCopy = this.reservations;
		return reservationsCopy;
	}

	/* input adds the reservation at the end of the list of reservation of the basket */
	public int add (Reservation rsv) {
		removeNull(); // remove all the null value in the array "reservations"
		
		int size = this.reservations.length;
		Reservation[] addedReservation = new Reservation[size+1]; // create a new array with +1 size
		
		if(size == 0){ // create a new array if previous array does not exist
			addedReservation[0] = rsv;
			this.reservations = addedReservation;
			return 1; //number of reservations
		}
		
		// traverse the array and deep copy
		for(int i = 0; i < size; i++) addedReservation[i] = this.reservations[i];
		addedReservation[size] = rsv; // add the new reservation at the end of the array
		this.reservations = addedReservation; // update
		
		return size+1; // current number of reservations 
	}
	

	/* removes the first occurrence of the specified element from the array of reservation of the basket
	 * and returns a boolean indicating whether the input reservation exists or not*/	
	public boolean remove (Reservation rsv) {
	
		boolean isRsvExist=false; 
		for(int i=0;i<this.reservations.length;i++) { 
			if(this.reservations[i].equals(rsv)) { 
				this.reservations[i]=null;  // assign a value null to the first occurrence of the specified element
				isRsvExist= true;
				removeNull(); // remove all null values
				break;
			}
		}
		return isRsvExist;
	}

	/* empties the array of reservations of the basket */
	public void clear() {
		this.reservations= new Reservation[0];
	}

	/* retrieves the number of reservations in the basket */
	public int getNumOfReservations() {
		int numRsv = this.reservations.length - nullCount(this.reservations); 
		// size of the array - number of null values inside the array
		return numRsv;
	}
	
	/* retrieves the cost (in cents) of all the reservations in the basket */
	public int getTotalCost() {
		int totalcost=0;
		// traverse the array and sum up the cost of each reservation
		for (int i = 0; i<this.reservations.length; i++) {
			totalcost += this.reservations[i].getCost();
		}
		return totalcost;
	}
	
	
	// extra methods used
	
	/*retrieves the number of null values inside the input reservation array*/
	private int nullCount(Reservation[] rsv) {
		int countNull =0; // count the number of null values
		for (int i = 0; i<rsv.length; i ++) {
			if(rsv[i] == null) {
				countNull = i; 
			}
		}
		return countNull;
	}

	/* removes any null values inside reservations*/
	private void removeNull() {
		int nullNum = nullCount(this.reservations); // count the number of null values inside reservations
		Reservation[] nullRemoved = new Reservation[this.reservations.length - nullNum];
		// create a new array with a size of null-value-removed array
		for(int j=0,k=0;k<this.reservations.length-nullNum;j++,k++) {
			if(this.reservations[j]!=null)  nullRemoved[k]=this.reservations[j];
			if(this.reservations[j]==null) k--;
		}
		this.reservations = nullRemoved;
	}
}


