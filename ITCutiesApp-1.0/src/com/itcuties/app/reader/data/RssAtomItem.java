package com.itcuties.app.reader.data;


/**
 * This class represents an ATOM entity - a post
 * 
 * @author ITCuties
 *
 */
public class RssAtomItem {

	private String title;
	
	private String content;

	private String publishDate;
	
	private String category;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
