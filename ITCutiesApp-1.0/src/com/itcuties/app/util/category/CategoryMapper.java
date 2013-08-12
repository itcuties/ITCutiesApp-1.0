package com.itcuties.app.util.category;

import java.util.HashMap;

import com.itcuties.app.R;

/**
 * Category to icon mapper.
 * 
 * @author ITCuties
 *
 */
public class CategoryMapper {

	// This is the mapping - Category name - icon
	private static HashMap<String, Integer> mapping = new HashMap<String, Integer>();
	
	static {
		// Mapping initialization
		mapping.put("Android", R.drawable.ic_android);
		mapping.put("Java", R.drawable.ic_java);
		mapping.put("J2EE", R.drawable.ic_j2ee);
		mapping.put("WordPress", R.drawable.ic_wordpress);
		mapping.put("Facebook", R.drawable.ic_facebook);
		mapping.put("Did you know", R.drawable.ic_didyouknow);
		mapping.put("SQL", R.drawable.ic_sql);
		mapping.put("Tools", R.drawable.ic_tools);
	}
	
	/**
	 * Get icon for the category name.
	 * @param category
	 * @return
	 */
	public static int getIconIdForCategory(String category) {
		if (mapping.get(category) != null)
			return mapping.get(category);
		
		// If no icon is found then return the default one - launcher icon
		return R.drawable.ic_launcher;
	}
	
}
