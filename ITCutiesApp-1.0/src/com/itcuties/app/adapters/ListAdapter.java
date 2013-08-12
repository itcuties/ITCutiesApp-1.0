package com.itcuties.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itcuties.app.R;
import com.itcuties.app.reader.data.RssAtomItem;
import com.itcuties.app.util.category.CategoryMapper;

/**
 * ListAdapter builds each element in the list.
 * @author ITCuties
 *
 */
public class ListAdapter extends ArrayAdapter<RssAtomItem> {

	// List context
    private final Context context;
    // List values
    private final List<RssAtomItem> items;

    public ListAdapter(Context context, List<RssAtomItem> items) {
    	// Set the layout for each item
        super(context, R.layout.item, items);
        this.context = context;
        this.items = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	// Build each element of the list
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item, parent, false);
        
        // Populate each layout element with the data from the items list
        TextView itemTitle = (TextView)rowView.findViewById(R.id.textViewTitle);
        itemTitle.setText(items.get(position).getTitle()); 
        
        TextView itemPublishDate = (TextView)rowView.findViewById(R.id.textViewPublishDate);
        itemPublishDate.setText(items.get(position).getPublishDate());
        
        // Set the icon base on the post's category
        ImageView icon = (ImageView)rowView.findViewById(R.id.imageViewCategoryIcon);
        icon.setImageResource(CategoryMapper.getIconIdForCategory(items.get(position).getCategory()));
        
        return rowView;

    }
    
}
