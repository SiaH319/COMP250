import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
	// num of entries to the table
	private int numEntries;
	// num of buckets 
	private int numBuckets;
	// load factor needed to check for rehashing 
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<HashPair<K,V>>> buckets; 

	// constructor
	public MyHashTable(int initialCapacity) {
		// ADD YOUR CODE BELOW THIS

		this.numEntries =0;
		this.numBuckets=initialCapacity;
		this.buckets=new ArrayList<>();
		for (int i=0;i<this.numBuckets;i++) {
			this.buckets.add(new LinkedList<>());
		}
	}

	public int size() {
		return this.numEntries;
	}

	public boolean isEmpty() {
		return this.numEntries == 0;
	}

	public int numBuckets() {
		return this.numBuckets;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.numBuckets;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */


	public V put(K key, V value) {
		// ADD YOUR CODE BELOW THIS
		try {
			for(HashPair<K, V> hashPair : this.buckets.get(hashFunction(key))) {
				if(hashPair.getKey().equals(key)) {
					V temp = hashPair.getValue(); 
					hashPair.setValue(value);
					return temp;
				}
			}
			// if !hashPair.getKey().equals(key)
			this.buckets.get(hashFunction(key)).add(new HashPair<>(key,value));
			this.numEntries ++;

			if(!((this.numEntries/this.numBuckets) < MAX_LOAD_FACTOR)) rehash();
			// ADD YOUR CODE BELOW THIS
		}
		catch (NullPointerException e) {

			return null;
		}
		return null;
	}



	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		//ADD YOUR CODE BELOW HERE
		try {
			for(HashPair<K, V> hashPair : this.buckets.get(hashFunction(key))) {
				if(hashPair.getKey().equals(key)) return hashPair.getValue(); 
			}}
		catch (NullPointerException e) {

			return null;
		}
		return null;
	}

	//ADD YOUR CODE ABOVE HERE

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1) 
	 */
	public V remove(K key) {
		//ADD YOUR CODE BELOW HERE
		try {
			for(HashPair<K, V> hashPair : this.buckets.get(hashFunction(key))) {
				if(hashPair.getKey().equals(key)){
					this.buckets.get(hashFunction(key)).remove(hashPair);
					this.numEntries--;
					return hashPair.getValue();
				}
			}
	}
	catch (NullPointerException e) {

		return null;
	}
	return null;
}

//ADD YOUR CODE ABOVE HERE


/** 
 * Method to double the size of the hashtable if load factor increases
 * beyond MAX_LOAD_FACTOR.
 * Made public for ease of testing.
 * Expected average runtime is O(m), where m is the number of buckets
 */

public void rehash() {
	//ADD YOUR CODE BELOW HERE

	ArrayList<LinkedList<HashPair<K,V>>> newList = this.buckets;
	this.numBuckets = this.numBuckets*2;
	this.numEntries = 0;
	ArrayList<LinkedList<HashPair<K,V>>> oldBuckets = this.buckets;
	this.buckets = new ArrayList<>();

	for(int i=0 ; i<this.numBuckets; i++) this.buckets.add(new LinkedList<>());
	for (LinkedList<HashPair<K, V>> l1: oldBuckets)
		for (HashPair<K, V> l2: l1)
			put(l2.getKey(), l2.getValue());

	//ADD YOUR CODE ABOVE HERE
}

/**
 * Return a list of all the keys present in this hashtable.
 * Expected average runtime is O(m), where m is the number of buckets
 */

public ArrayList<K> keys() {
	//ADD YOUR CODE BELOW HERE

	ArrayList<K> keyList = new ArrayList<>();
	for(int i = 0; i < this.buckets.size(); i++) {
		for (HashPair<K, V> hashPair: this.buckets.get(i)) {
			keyList.add(hashPair.getKey());
		}
	}
	return keyList;
	//ADD YOUR CODE ABOVE HERE

}


/**
 * Returns an ArrayList of unique values present in this hashtable.
 * Expected average runtime is O(m) where m is the number of buckets
 */

public ArrayList<V> values() {
	//ADD CODE BELOW HERE

	MyHashTable<V,K> values = new MyHashTable(this.numEntries);
	for (LinkedList<HashPair<K, V>> hashPairList: this.buckets) {
		for(HashPair<K, V> hashPair: hashPairList) {
			values.put(hashPair.getValue(), hashPair.getKey());
		}
	}

	return values.keys();

	//ADD CODE ABOVE HERE
}

/**
 * This method takes as input an object of type MyHashTable with values that 
 * are Comparable. It returns an ArrayList containing all the keys from the map, 
 * ordered in descending order based on the values they mapped to. 
 * 
 * The time complexity for this method is O(n^2), where n is the number 
 * of pairs in the map. 
 */
public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
	ArrayList<K> sortedResults = new ArrayList<>();
	for (HashPair<K, V> entry : results) {
		V element = entry.getValue();
		K toAdd = entry.getKey();
		int i = sortedResults.size() - 1;
		V toCompare = null;
		while (i >= 0) {
			toCompare = results.get(sortedResults.get(i));
			if (element.compareTo(toCompare) <= 0 )
				break;
			i--;
		}
		sortedResults.add(i+1, toAdd);
	}
	return sortedResults;
}


/**
 * This method takes as input an object of type MyHashTable with values that 
 * are Comparable. It returns an ArrayList containing all the keys from the map, 
 * ordered in descending order based on the values they mapped to.
 * 
 * The time complexity for this method is O(n*log(n)), where n is the number 
 * of pairs in the map. 
 */

public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
	//ADD CODE BELOW HERE

	ArrayList<HashPair<K,V>> array = new ArrayList<HashPair<K,V>>();
	ArrayList<K> sorted = new ArrayList();

	for(HashPair<K,V> hashPair : results) array.add(hashPair);
	for (int i = array.size(); i >= 2; i--) Sort(array, array.size(), i/2-1); 

	for (int i=array.size(); i>1; i--) { 
		array.set(i-1, array.get(0));
		array.set(0, array.get(i-1));
		Sort(array, i-1, 0); 
	} 

	for(HashPair<K,V> hashPair : array) sorted.add(0,hashPair.getKey());

	return sorted;
	//ADD CODE ABOVE HERE
}

private static <K, V extends Comparable<V>> void Sort(ArrayList<HashPair<K,V>> array , int i, int k) 
{ 
	int limit = k; 
	if(2*k + 1 < i && 
			array.get(2*k + 1).getValue().compareTo(array.get(limit).getValue()) > 0 )
		limit = 2*k + 1;

	if(2*k + 2<i 
			&& array.get(2*k + 2).getValue().compareTo(array.get(limit).getValue()) > 0)
		limit = 2*k + 2;

	if (limit != k) 		 { 
		array.set(limit, array.get(k));
		array.set(k, array.get(limit));
		Sort(array, i, limit); 
	} 
}



@Override
public MyHashIterator iterator() {
	return new MyHashIterator();
}   

private class MyHashIterator implements Iterator<HashPair<K,V>> {
	//ADD YOUR CODE BELOW HERE

	ArrayList<HashPair<K, V>> array = new ArrayList<>();
	Iterator iterator;

	//ADD YOUR CODE ABOVE HERE

	/**
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	private MyHashIterator() {
		//ADD YOUR CODE BELOW HERE
		for(LinkedList<HashPair<K, V>> linkedList: buckets) {
			for (HashPair<K, V> hashPair: linkedList) this.array.add(hashPair);
		}
		iterator = this.array.iterator();
		//ADD YOUR CODE ABOVE HERE
	}

	@Override
	/**
	 * Expected average runtime is O(1)
	 */
	public boolean hasNext() {
		//ADD YOUR CODE BELOW HERE

		return iterator.hasNext();

		//ADD YOUR CODE ABOVE HERE
	}

	@Override
	/**
	 * Expected average runtime is O(1)
	 */
	public HashPair<K,V> next() {
		//ADD YOUR CODE BELOW HERE

		return  (HashPair<K, V>) iterator.next();

		//ADD YOUR CODE ABOVE HERE
	}

}
}
