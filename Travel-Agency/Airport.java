/**
 * COMP250_Assignment 1
 * Retrieve the fees of the given airport
 * Calculate the rounded up distance (in km) between two airports 
 * @author Sia Ham (260924883)
 */

public class Airport {  
	private int xCoord;		// x-coordinate of the airport on a world map with a scale to 1 km
	private int yCoord;		// y-coordinate of the airport on a world map with a scale to 1 km
	private int fees;		// the airport fees associated to this airport (in cents)
	
	public Airport(int xCoord, int yCoord,int fees) {
		// initialize the corresponding fields
		this.xCoord=xCoord;
		this.yCoord=yCoord;
		this.fees=fees;
	}
	
	/* retrieve the fees of the airport in cents */
	public int getFees() { 
		return this.fees;
}
	/* evaluate the rounded up distance (in kilometer) between two given airports */
	public static int getDistance(Airport a1, Airport a2) {
		int a1x = a1.xCoord;	// x-coordinate of the first airport
		int a1y = a1.yCoord; 	// y-coordinate of the first airport
		int a2x = a2.xCoord; 	// x-coordinate of the second airport
		int a2y = a2.yCoord;	// y-coordinate of the second airport
		double real_distance = Math.sqrt(Math.pow((a1x-a2x),2)+Math.pow(a1y-a2y,2)); // distance between two airports
		int roundedUp_distane = (int) Math.ceil(real_distance);   // rounded up distance in km
		return roundedUp_distane;
	}
} 