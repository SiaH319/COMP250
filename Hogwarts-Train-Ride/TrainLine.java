/**
 * COMP250_Assignment 2
 * A TrainNetwork contains an array of train lines
 * @author Sia Ham (260924883)
 */

import java.util.Arrays;
import java.util.Random;

public class TrainLine {
	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
			 * Constructor for TrainStation. input: stationNames - An array of String
			 * containing the name of the stations to be placed in the line name - Name of
			 * the line goingRight - boolean indicating the direction of travel
			 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		int size = 0;
		TrainStation current = this.leftTerminus;
		while(true) {
			size++;
			current = current.getRight();
			if (current.equals(this.rightTerminus)) {
				size++;
				break;
			}
		}
		return size;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}


	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		try {
			TrainStation curStat = findStation(current.getName());
			TrainStation transfer = current.getTransferStation();

			if (current.hasConnection && 
					(previous == null || !previous.equals(transfer))) {
				return transfer; 
			} 
			else return getNext(current); 
		} 
		catch(StationNotFoundException e) {
			throw e;
		}
	}



	public TrainStation getNext(TrainStation station) {
		try {
			this.findStation(station.getName());
		}
		catch(StationNotFoundException e) {
			throw e;
		}

		if (station.equals(findStation(station.getName()))) { 
			if (goingRight) {
				if (station.isRightTerminal()) {
					reverseDirection();
					return station.getLeft();
				}
				else return station.getRight();
			}

			else { //!goingRight
				if (station.isLeftTerminal()){
					reverseDirection();
					return station.getRight();
				}
				else return station.getLeft();
			}
		}
		throw new StationNotFoundException("such station is not found");	
	}


	public TrainStation findStation(String name) {
		TrainStation current = this.leftTerminus;
		for (int i = 0; i<this.getSize(); i++) {
			if(current.getName().equals(name)) 	return current;
			current = current.getRight();
		}
		throw new StationNotFoundException("such station is not found");
	}

	public void sortLine() {
		TrainStation curStation = this.leftTerminus;
		boolean is_sorted = false;

		while (!is_sorted) {
			is_sorted = true;
			for (int k = 0; k < (this.getSize()-2); k++) {
				TrainStation nxtStation = curStation.getRight();
				boolean is_aphabetical = (curStation.getName().compareToIgnoreCase(nxtStation.getName())<0);

				if (!is_aphabetical) {
					swap(curStation, nxtStation);

					if (k == 0) nxtStation.setLeftTerminal();
					if (k == (this.getSize()-2)) curStation.setRightTerminal();

					is_sorted = false;
				}
				if (is_aphabetical) curStation = curStation.getRight();
			}
		}
	}

	private void swap(TrainStation curStation, TrainStation nxtStation) {
		TrainStation  tmp, tmp1, tmp2, tmp3;
		tmp = curStation;
		tmp1 = curStation.getLeft();
		tmp2 = nxtStation.getRight();
		tmp3 = nxtStation;

		nxtStation.setRight(tmp);
		nxtStation.setLeft(tmp1);
		curStation.setRight(tmp2);
		curStation.setLeft(tmp3);	
	}

	public TrainStation[] getLineArray() {
		TrainStation[] trainStations = new TrainStation[this.getSize()];
		TrainStation current = this.leftTerminus;

		for (int i=0; i<this.getSize(); i++) {
			trainStations[i] = current;
			current = current.getRight(); }
		return trainStations;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}


	public void shuffleLine() {
		TrainStation[] lineArray = getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);

		int size = shuffledArray.length;
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[size-1];

		for (int i = 0; i< size; i++) {
			if (i == size-1) {
				shuffledArray[i].setRight(null);
				shuffledArray[i].setRightTerminal();
			}
			else {
				shuffledArray[i].setRight(shuffledArray[i+1]);
				shuffledArray[i].setNonTerminal();
			}
		}
		for (int i = size-1; i> -1; i--) {
			if (i == 0) {
				shuffledArray[i].setLeft(null);
				shuffledArray[i].setLeftTerminal();
			}
			else shuffledArray[i].setLeft(shuffledArray[i-1]);
		}
		this.lineMap = this.getLineArray();
	}


	/* following codes are given by the instructor*/
	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}

}
