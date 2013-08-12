package com.itcuties.app;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.itcuties.app.reader.data.RssAtomItem;
import com.itcuties.app.reader.data.RssResults;
import com.itcuties.app.util.atom.RssAtomReader;

/**
 * Application splash screen. It also loads data.
 * 
 * @author ITCuties
 *
 */
public class SplashActivity extends Activity {
	
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		progressBar.setProgress(0); // No progress so far
		
		// Download data in the new thread
		GetRSSDataTask grdt = new GetRSSDataTask();
		grdt.execute("http://www.itcuties.com/feed/atom");
		
	}
	
	/**
	 * Read RSS channel data.
	 * 
	 * @author ITCuties
	 *
	 */
	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssAtomItem> > {
		@Override
		protected List<RssAtomItem> doInBackground(String... urls) {
			try {
				// Create RSS reader
				RssAtomReader rssReader = new RssAtomReader(urls[0]);
				rssReader.setProgressBar(progressBar); // Set the progress bar to show real progress
				
				// Parse RSS, get items
				return rssReader.getItems();
			
			} catch (Exception e) {
				Log.e("ITCRssReader", e.getMessage());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<RssAtomItem> results) {
			// When the download is done the main activity needs to be started
			Intent i = new Intent(SplashActivity.this, ListPostsActivity.class);
			
			// You might find this not right to use a static attribute to pass data between the 
			// the activities in the application. We tried to pass the List of the RssAtomItem 
			// object with no luck although RssAtomItem implemented Serializable interface.
			// ListPostActivity read null values. So this is the engineer's solution. It works :)
			RssResults.setResults(results); //We need to set the results of the download process
			
			// Show 100% progress
			progressBar.setProgress(100);
			
			// Start new activity and finish this splash activity
			SplashActivity.this.startActivity(i);
			SplashActivity.this.finish();
			
		}
		
	}
	
}
