package com.itcuties.app.util.atom;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.widget.ProgressBar;

import com.itcuties.app.reader.data.RssAtomItem;

/**
 * Class reads RSS ATOM data.
 * 
 * @author ITCuties
 *
 */
public class RssAtomReader {
	
	private String rssUrl;

	// This reference needs to be provided to 
	// the SAX Handler for update.
	private ProgressBar progressBar;

	/**
	 * Constructor
	 * 
	 * @param rssUrl
	 */
	public RssAtomReader(String rssUrl) {
		this.rssUrl = rssUrl;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	/**
	 * Get RSS items.
	 * 
	 * @return
	 */
	public List<RssAtomItem> getItems() throws Exception {
		// SAX parse RSS data
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		// Create our handler
		RssAtomParseHandler handler = new RssAtomParseHandler();
		handler.setProgressBar(progressBar);
		
		// Parse the stream
		saxParser.parse(rssUrl, handler);
		
		return handler.getItems();
		
	}

}
