package com.itcuties.app.reader.data;

import java.util.List;

/**
 * This is the class that we use to send data between the SplashActivtiy
 * and the ListPostsActivity.
 * You might find this not right to use a static attribute to pass data between the 
 * the activities in the application. We tried to pass the List of the RssAtomItem 
 * object with no luck although RssAtomItem implemented Serializable interface.
 * ListPostActivity read null values. So this is the engineer's solution. It works :)
 * 
 * @author ITCuties
 */
public class RssResults {

	private static List<RssAtomItem> rssResults;

	public static List<RssAtomItem> getResults() {
		return rssResults;
	}

	public static void setResults(List<RssAtomItem> results) {
		rssResults = results;
	}
	
}
