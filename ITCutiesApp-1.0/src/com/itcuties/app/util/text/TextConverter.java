package com.itcuties.app.util.text;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.NodeTraversor;

import com.itcuties.app.util.jsoup.JSoupNodeVisitor;

/**
 * Class deals with unwanted text parts that come from the RSS Atom Feed.
 * 
 * @author ITCuties
 *
 */
public class TextConverter {

	// Text conversion map
	private static HashMap<String, String> conversions = new HashMap<String, String>();
	
	// Map initialization
	static {
		conversions.put("&#8211;", "-");
		conversions.put("&#8217;", "'");
	}
	
	/**
	 * Perform conversion of a post title.
	 * @param text
	 * @return
	 */
	public static String convertTitle(String text) {
		// Remove unwanted signs based on the conversions map
		String[] splittedText = text.trim().split(" ");
		StringBuffer sb = new StringBuffer();
		
		for (String textWord: splittedText) {
			for (String cKey: conversions.keySet())
				textWord = textWord.replaceAll(cKey, conversions.get(cKey));
				
			sb.append(textWord).append(" ");
		}
		
		return sb.toString();
		
	}
	
	
	// Date formatter 2013-07-11T04:29:14Z
	private static SimpleDateFormat dateFormatterIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
	// Date formater 27-07-2013
	private static SimpleDateFormat dateFormatterOut= new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
	/**
	 * Format date to the format like this: 27-07-2013
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static String convertDate(String stringDate) throws ParseException {
		return dateFormatterOut.format(dateFormatterIn.parse(stringDate));
	}
	
	// Download section markers
	private static String downloadSectionIconHtmlCodeStart = "<p><img class='size-full wp-image-967 alignleft'";
	private static String downloadSectionTextHtmlCodeStart = "<p><strong>Download this sample code ";
	private static String downloadSectionEnd = "</p>";
	/**
	 * Removes download section from the post text.
	 * @param postText
	 * @return
	 */
	public static String removeDownloadSection(String postText) {
		int downloadSectionIconIndex = postText.indexOf(downloadSectionIconHtmlCodeStart);
		if (downloadSectionIconIndex != -1)
			postText = postText.replaceAll(postText.substring(downloadSectionIconIndex, postText.indexOf(downloadSectionEnd, downloadSectionIconIndex) + downloadSectionEnd.length()),"");
		
		int downloadSectionTextIndex = postText.indexOf(downloadSectionTextHtmlCodeStart);
		if (downloadSectionTextIndex != -1)
			postText = postText.replaceAll(postText.substring(downloadSectionTextIndex, postText.indexOf(downloadSectionEnd, downloadSectionTextIndex) + downloadSectionEnd.length()),"");
		
		return postText;
	}
	
	// Post info section markers
	private static String postInfoSectionHtmlCodeStart = "<p>The post <a";
	private static String postInfoSectionEnd = "</p>";
	/**
	 * Remove post info section from the post text.
	 * @param postText
	 * @return
	 */
	public static String removePostInfoSection(String postText) {
		int postInfoSectionIndex = postText.indexOf(postInfoSectionHtmlCodeStart);
		if (postInfoSectionIndex != -1)
			postText = postText.replaceAll(postText.substring(postInfoSectionIndex, postText.indexOf(postInfoSectionEnd, postInfoSectionIndex) + postInfoSectionEnd.length()),"");
		
		return postText;
	}
	
	/**
	 * URL Encode string.
	 * @param postText
	 * @return
	 */
	public static String URLEncode(String postText) {
		return URLEncoder.encode(postText).replaceAll("\\+", "%20");
	}
	
	/**
	 * Remove all the styles and JavaScript from HTML post text
	 * @param postText
	 * @return
	 */
	public static String clearStylesAndJS(String postText) {
		Document doc = Jsoup.parse(postText);
		
		doc.select("javascript").remove();
		
		NodeTraversor traversor  = new NodeTraversor(new JSoupNodeVisitor());

		traversor.traverse(doc.body());

		return doc.toString();
	}
}
