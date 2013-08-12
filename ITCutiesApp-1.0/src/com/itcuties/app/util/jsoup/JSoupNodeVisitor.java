package com.itcuties.app.util.jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

/**
 * JSoup's node visitor used to remove class and style
 * information from the HTML post text.
 * 
 * @author ITCuties
 *
 */
public class JSoupNodeVisitor implements NodeVisitor {

	public void tail(Node node, int depth) {
		if (node instanceof Element) {
			Element e = (Element) node;
			e.removeAttr("class");
			e.removeAttr("style");
		}
	}

	public void head(Node node, int arg1) {
	}

}
