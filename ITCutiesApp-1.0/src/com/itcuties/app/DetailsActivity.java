package com.itcuties.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itcuties.app.util.category.CategoryMapper;

/**
 * An activity displaying a ATOM entity details - the post
 * 
 * @author ITCuties
 *
 */
public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		// Read data to display
		String title 			= (String)getIntent().getExtras().get("title");
		String content 			= (String)getIntent().getExtras().get("content");
		String publishDate 		= (String)getIntent().getExtras().get("publishDate");
		String category 		= (String)getIntent().getExtras().get("category");
		
		// Set data in the layout
		TextView titleTV = (TextView)findViewById(R.id.textViewDetailsTitle);
		titleTV.setText(title);
		
		TextView publishDateTV = (TextView)findViewById(R.id.textViewDetailsPublishDate);
		publishDateTV.setText(publishDate);
		
		ImageView icon = (ImageView)findViewById(R.id.imageViewDetailsCategoryIcon);
		icon.setImageResource(CategoryMapper.getIconIdForCategory(category));
			
		WebView webView	 = (WebView)findViewById(R.id.webViewDetailsPostContents);
		webView.loadData(content, "text/html", "UTF-8");
		
	}

	
}
