package com.itcuties.app.util.atom;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.widget.ProgressBar;

import com.itcuties.app.reader.data.RssAtomItem;
import com.itcuties.app.util.text.TextConverter;

/**
 * SAX tag handler
 * 
 * @author ITCuties
 *
 */
public class RssAtomParseHandler extends DefaultHandler {

	private List<RssAtomItem> rssItems;
	
	// Used to reference item while parsing
	private RssAtomItem currentItem;
	
	// Parsing title indicator
	private boolean parsingTitle;
	// Parsing contents indicator
	private boolean parsingContents;
	// Parsing published date indicator
	private boolean parsingPublishedDate;
	// A buffer for title contents
	private StringBuffer currentTitleSb;
	// A buffer for content tag contents
	private StringBuffer currentContentSb;
	// A buffer for publish date tag contents;
	private StringBuffer currentPubishDateSb;
	
	// This is a ProgressBar reference set by the SplashScreen
	// We use it to show the real progress while each item from 
	// the feed is read.
	private ProgressBar progressBar;
	
	public RssAtomParseHandler() {
		rssItems = new ArrayList<RssAtomItem>();
	}
	
	public List<RssAtomItem> getItems() {
		return rssItems;
	}
	
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("entry".equals(qName)) {
			currentItem = new RssAtomItem();
		} else if ("title".equals(qName)) {
			parsingTitle = true;
			currentTitleSb = new StringBuffer();
		} else if ("content".equals(qName)) {
			parsingContents = true;
			currentContentSb = new StringBuffer();
		} else if ("published".equals(qName)) {
			parsingPublishedDate = true;
			currentPubishDateSb = new StringBuffer();
		} else if ("category".equals(qName)) {
			if (currentItem != null && currentItem.getCategory() == null) {
				currentItem.setCategory(attributes.getValue("term"));
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("entry".equals(qName)) {
			rssItems.add(currentItem);
			currentItem = null;
		} else if ("title".equals(qName)) {
			parsingTitle = false;
			
			// There is a title tag for a whole channel present. 
			// It is being parsed before the entry tag is present, 
			// so we need to check if item is not null
			if (currentItem != null)
				// We encode the title so that it can be read by the application properly
				currentItem.setTitle(TextConverter.convertTitle(currentTitleSb.toString()));
			
		} else if ("content".equals(qName)) {
			parsingContents = false;
			
			if (currentItem != null) {
				// When an item's content is being set we convert it a little bit:
				// - All styles and JavaScript information are removed
				// - Content is URL encoded so that the WebView component can display it correctly
				// - We remove two sections which are added by the ATOM feed, the post info section
				//	 and the code download section (only github link is present in the text)
				currentItem.setContent(TextConverter.clearStylesAndJS(
									   		TextConverter.URLEncode(
									   				TextConverter.removePostInfoSection(
									   						TextConverter.removeDownloadSection(currentContentSb.toString())
													)
									   		)
									   ));
				
				// Update progressBar
				progressBar.setProgress(progressBar.getProgress()+1);
				
			}
			
		} else if ("published".equals(qName)) {
			parsingPublishedDate = false;
			
			if (currentItem != null)
				try {
					// We convert the date from the WordPress format to DD-MM-YYYY format
					currentItem.setPublishDate(TextConverter.convertDate(currentPubishDateSb.toString()));
				} catch (ParseException e) {
					currentItem.setPublishDate("no date");
					Log.e("ITCRssReader", "No publish date available");
				}
			
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// title, contents and date tags contents values are set here.
		// This method can be called multiple times for one tag, it depends
		// on how much text tag's contents has. Remember that SAX is a 
		// stream parser so it parses feed stream sequentially.
		if (parsingTitle) {
			if (currentItem != null)
				currentTitleSb.append(new String(ch, start, length));
		} else if (parsingContents) {
			if (currentItem != null)
				currentContentSb.append(new String(ch, start, length));
		} else if (parsingPublishedDate) {
			if (currentItem != null)
				currentPubishDateSb.append(new String(ch, start, length));
		}
	}
	
}
