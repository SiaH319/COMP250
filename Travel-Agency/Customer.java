/**
 * COMP250_Assignment 1
 * Create a basket for the customer
 * Retrieve the name, balance, basket of the customer
 * Add reservation(hotel/flight/hotel with breakfast) to the basket of the customer
 * Remove reservation from the basket of the customer
 * Update the balance of the customer when checking out
 * @author Sia Ham (260924883)
 */


public class Customer {
	private String name;   // name of the customer
	private int balance;   // represents the balance (in cents) of the customer
	private Basket basket; // contains the reservations the customer would like to make

	public Customer(String name, int balance ) {
		// initialization
		this.name=name;	
		this.balance=balance;
		this.basket=new Basket(); // create an empty Basket
	}

	/* retrieves the name of the customer*/
	public String getName () { 
		return this.name; 
	}
	
	/* retrieves the balance (in cents) of the customer*/
	public int getBalance() {
		return this.balance;
	}
	
	/* retrieves the reference to the basket of the customer */
	public Basket getBasket() {
		return this.basket;
	}
	

	/*check the validity of the added balance (whether the added value is negative or positive)
	and update the balance (in cents) if valid  */
	public int addFunds(int added) {

		if(added<0)  throw new IllegalArgumentException("Adding negative value is not allowd");
		// if the input received is negative, throw an IllegalArgumentException
		
		this.balance+=added; // update the balance
		return this.balance;
	}
	
	/*check the validity of the customer name (whether the name on the reservation matches the name of the customer)
	and add the input reservation to the basket of the customer if valid */
	public int addToBasket (Reservation rsv) {
		if(rsv.reservationName().equals(this.name)) { // check if the name on the reservation matched the name of the customer
			int numOfRsv=this.basket.add(rsv); // number of reservations
			return numOfRsv; 
		}
		else throw new IllegalArgumentException("The name on the reservation does not match the name of the customer");
	}


	/* adds the corresponding reservation (hotel reservation with/without breakfast) in the basket of the customer
	 * and retrieves the updated number of reservations in that basket*/
	public int addToBasket(Hotel hotel,String roomType, int numNights, boolean isBreakfast) {
		HotelReservation hotelRsv = new HotelReservation(this.name,hotel,roomType,numNights); 
		// reservation without breakfast
		BnBReservation bnb = new BnBReservation(this.name,hotel,roomType,numNights); 
		// reservation including breakfast

		int numRsv = 0; //number of reservations
		if(isBreakfast) numRsv=addToBasket(bnb); 
		// add reservation including breakfast to the basket of the customer
		else numRsv=addToBasket(hotelRsv); 
		// add reservation without breakfast to the basket of the customer
		return numRsv; // the current number of reservations in the basket of the customer
	}

	
	/* add flight reservation to the basket of the customer and 
	 * retrieves the updated number of reservations in the basket*/
	public int addToBasket(Airport a1, Airport a2) {
		FlightReservation flighRsvt = new FlightReservation(this.name,a1,a2);
		// flight reservation
		int numRsv=addToBasket(flighRsvt); // add flight reservation to the basket of the customer
		return numRsv; // the current number of reservations in the basket of the customer
	}

	/* removes the given input reservation from the basket of the customer and 
	 * indicate whether the operation was successful or not*/
	public boolean removeFromBasket (Reservation rsv) {
		// removes the input reservation from the basket of the customer
		boolean isSuccessful=this.basket.remove(rsv);
		// boolean indicating whether of not the operation was successful
		return isSuccessful; 
	}

	/* check whether the customer's balance is enough or not
	 * and if enough, update the total cost of the basket and clear the basket 
	 * then return the balance left */
	public int checkOut() {
		int totalcost=this.basket.getTotalCost(); // the total cost of customer's basket
		if(this.balance<totalcost) throw new IllegalArgumentException("The coustomer's current balance is not enough to cover the total cost ");
		// if the customer's balance is not enough to cover the total cost
		else{ // if enough balance
			this.balance=this.balance - totalcost; //charge the total cost of the basket
			this.basket.clear(); // clear the basket
			return balance; //balance left
		}
	}
}

