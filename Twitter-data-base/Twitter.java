import java.util.ArrayList;



public class Twitter {

	//ADD YOUR CODE BELOW HERE

	private MyHashTable<String, String> stopWords;
	ArrayList<String> stopWordsList;
	private ArrayList<Tweet> tweetList; 
	private MyHashTable<String, ArrayList<Tweet> > byAuthor; 
	private MyHashTable<String, ArrayList<Tweet>> tweetsByDate; 

	//ADD CODE ABOVE HERE 

	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		this.stopWordsList = stopWords;
		this.stopWords = new MyHashTable<>(stopWords.size());
		for (String word: stopWords)this.stopWords.put(word.toLowerCase(),  word.toLowerCase());


		this.byAuthor = new MyHashTable<String, ArrayList<Tweet>>(tweets.size());
		this.tweetsByDate = new MyHashTable<String, ArrayList<Tweet>>(tweets.size());

		for(Tweet oneTweet: tweets) addTweet(oneTweet);
		this.tweetList = tweets;
		//ADD CODE ABOVE HERE 
	}



	/**
	 * Add Tweet t to this Twitter
	 * O(1)
	 */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		ArrayList<Tweet> tempTweet = new ArrayList<Tweet>();
		ArrayList<Tweet> tempAuthor = new ArrayList<Tweet>();

		try {
			if((ArrayList<Tweet>) this.tweetsByDate.get(t.getDateAndTime().substring(0,10)) != null) {
				((ArrayList<Tweet>) this.tweetsByDate.get(t.getDateAndTime().substring(0,10))).add(t);
				this.tweetsByDate.put(t.getDateAndTime().substring(0,10), (ArrayList<Tweet>) this.tweetsByDate.get(t.getDateAndTime().substring(0,10)));
			}

			else {
				tempTweet.add(t);
				this.tweetsByDate.put(t.getDateAndTime().substring(0,10), tempTweet);
			}


			if((ArrayList<Tweet>) this.byAuthor.get(t.getAuthor()) != null) {
				((ArrayList<Tweet>) this.byAuthor.get(t.getAuthor())).add(t);
				this.byAuthor.put(t.getAuthor(), (ArrayList<Tweet>) this.byAuthor.get(t.getAuthor()));
			}

			else {
				tempAuthor.add(t);
				this.byAuthor.put(t.getAuthor(), tempAuthor);
			}
		}
		catch (NullPointerException e) {
			System.out.println("Exception caught:null tweet cannot be added"); 

		}
	}


	//ADD CODE ABOVE HERE 



	/**
	 * Search this Twitter for the latest Tweet of a given author.
	 * If there are no tweets from the given author, then the 
	 * method returns null. 
	 * O(1)  
	 */


	public Tweet latestTweetByAuthor(String author) {
		//ADD CODE BELOW HERE

		try {
			if(this.byAuthor.get(author) != null)
				return this.byAuthor.get(author).get(this.byAuthor.get(author).size() -1);
			else return null;
		}
		catch (ArithmeticException e ) {
			return null;
		}
		//ADD CODE ABOVE HERE 
	}

	/**
	 * Search this Twitter for Tweets by `date' and return an 
	 * ArrayList of all such Tweets. If there are no tweets on 
	 * the given date, then the method returns null.
	 * O(1)
	 */
	public ArrayList<Tweet> tweetsByDate(String date) {
		//ADD CODE BELOW HERE	


		if(this.tweetsByDate.get(date) != null) return this.tweetsByDate.get(date);

		else return null;

		//ADD CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */

	public ArrayList<String> trendingTopics() {
		//ADD CODE BELOW HERE

		MyHashTable<String, Integer> currWord = new MyHashTable<String, Integer>(this.tweetList.size());

		int i = 0;
		for (Tweet tweet: this.tweetList) {
			for (String msg: getWords(tweet.getMessage())) {
				msg = msg.toLowerCase();

				if(this.stopWords.isEmpty()||this.stopWords.get(msg) == null) {
					if(currWord.get(msg) != null) i = currWord.get(msg) + 1;
					else i = 1;

					currWord.put(msg, i);
				}
			}
		}
		return MyHashTable.fastSort(currWord);
	}


	//ADD CODE ABOVE HERE    	



	/**
	 * An helper method you can use to obtain an ArrayList of words from a 
	 * String, separating them based on apostrophes and space characters. 
	 * All character that are not letters from the English alphabet are ignored. 
	 */
	private static ArrayList<String> getWords(String msg) {
		msg = msg.replace('\'', ' ');
		String[] words = msg.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>(words.length);
		for (int i=0; i<words.length; i++) {
			String w = "";
			for (int j=0; j< words[i].length(); j++) {
				char c = words[i].charAt(j);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
					w += c;

			}
			wordsList.add(w);
		}
		return wordsList;
	}
}
