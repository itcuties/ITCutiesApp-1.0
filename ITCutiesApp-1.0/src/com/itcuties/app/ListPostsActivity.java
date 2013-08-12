package com.itcuties.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.itcuties.app.adapters.ListAdapter;
import com.itcuties.app.reader.data.RssAtomItem;
import com.itcuties.app.reader.data.RssResults;

/**
 * This is our application main activity which displays list of posts.
 * 
 * @author ITCuties
 *
 */
public class ListPostsActivity extends ListActivity {
	
	/** 
	 * This method creates main application view
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// We set the list adapter giving it the results list downloaded from the ATOM feed
		setListAdapter(new ListAdapter(this, RssResults.getResults()));
		
	}
	
	/**
	 * On item click behavior
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// When an item is clicked a Details activity needs to be started.
		Intent i = new Intent(this, DetailsActivity.class);
		
		// Set data for the details activity to display
		i.putExtra("title", ((RssAtomItem)getListAdapter().getItem(position)).getTitle());
		i.putExtra("content", ((RssAtomItem)getListAdapter().getItem(position)).getContent());
		i.putExtra("publishDate", ((RssAtomItem)getListAdapter().getItem(position)).getPublishDate());
		i.putExtra("category", ((RssAtomItem)getListAdapter().getItem(position)).getCategory());
		
		this.startActivity(i);
	}
	
}