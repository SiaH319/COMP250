/**
 * COMP250_Assignment 2
 * A TrainNetwork contains an array of train lines
 * @author Sia Ham (260924883)
 */

public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

	public TrainNetwork(int nLines) {
		this.networkLines = new TrainLine[nLines];
	}

	public void addLines(TrainLine[] lines) {
		this.networkLines = lines;
	}

	public TrainLine[] getLines() {
		return this.networkLines;
	}

	public void dance() {
		System.out.println("The tracks are moving!");
		for (TrainLine trainline: networkLines) {
			trainline.shuffleLine();
		}
	}

	public void undance() {
		for (TrainLine trainline: networkLines) {
			trainline.sortLine();
		}
	}

	public int travel(String startStation, String startLine, String endStation, String endLine) {

		TrainLine curLine;
		TrainLine finishLine;
		TrainStation curStation;
		TrainStation finishStation;
		TrainStation prevStation = null;
		TrainStation tmpStation = null;
		
		try {
			curLine = getLineByName(startLine);
			finishLine = getLineByName(endLine);
			
			curStation = curLine.findStation(startStation);
			finishStation = finishLine.findStation(endStation);
		}
		catch (StationNotFoundException | LineNotFoundException e ) {
			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
			return 168;
		}
		
		
		int hoursCount = 0;
		System.out.println("Departing from " + startStation);


		if(!curStation.equals(finishStation)) {
			while(!curStation.equals(finishStation)) {
				try {
					tmpStation = curStation;
					curStation = curLine.travelOneStation(curStation,  prevStation); 
					prevStation = tmpStation; 

					curLine = curStation.getLine();
					hoursCount ++;
				}

				catch (StationNotFoundException | LineNotFoundException e ) {
					System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
					return 168;
				}

				if(hoursCount == 168) {
					System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
					return hoursCount;
				}

				System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
				System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
				System.out.println("=============================================");
				if(hoursCount %2 ==0 && hoursCount != 0 &&hoursCount !=168) this.dance();
			}
		}
		
		System.out.println("Arrived at destination after "+hoursCount+" hours!");
		return hoursCount;
	}

	
	public TrainLine getLineByName(String lineName){
		for (TrainLine trainline: networkLines) {
			if (lineName.equals(trainline.getName())) {
				return trainline;
			}
		}
		throw new LineNotFoundException ("such line cannot be found");	
	}

	//prints a plan of the network for you.
	public void printPlan() {
		System.out.println("CURRENT TRAIN NETWORK PLAN");
		System.out.println("----------------------------");
		for(int i=0;i<this.networkLines.length;i++) {
			System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
		}
		System.out.println("----------------------------");
	}
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	String name;

	public LineNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "LineNotFoundException[" + name + "]";
	}
}