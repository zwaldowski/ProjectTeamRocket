package edu.gatech.oad.rocket.findmythings.control;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * @author TeamRocket
 * */
// TODO: delete, implement Filterable on mai nactivity
public class Adapter extends ArrayAdapter<Item> implements Filterable {

	/**
	 * Gets context from the activity using this adapter
	 */
	private Context mContext;
	
	/**
	 * Instance of the text filter used to filter search results
	 */
	private TextFilter filter;

	/**
	 * constructor1
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public Adapter(Context context, int textViewResourceId, List<Item> objects) {
		super(context, textViewResourceId, objects);
		
		mContext = context;
		addAll(objects);
	}

	/**
	 * constructor2
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public Adapter(Context context, int resource, int textViewResourceId,
			List<Item> objects) {
		super(context,resource,textViewResourceId, objects);
		mContext=context;
		addAll(objects);
	}
	
	/**
	 * sets the main list of the Adapter
	 * @param l
	 */
	public void setList(List<Item> l) {
		if (l != null) {
			clear();
			if (!l.isEmpty()) {
				addAll(l);
			}
			notifyDataSetChanged();
		}
	}
	
	/**
	 * Sets the filter for this ArrayAdapter
	 */
	@Override
	public Filter getFilter() {
	    if (filter == null)
	        filter = new TextFilter();

	    return filter;
	}
	
	/**
	 * returns the current View being used
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	 View row = convertView;
	 if (row == null || isEmpty()) {
         LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
         row = inflater.inflate (R.layout.arrayadapter_view, parent, false);
	 }

	 if (isEmpty() || position > getCount())
		 return row;
	
	 Item temp = getItem(position);
	 
	 Spannable span = new SpannableString(temp.toString() + " - " + temp.getLoc() + temp.getSummary());
	 int start = temp.toString().length();
	 int stop = temp.getLoc().length()+ temp.toString().length() +  temp.getSummary().length()+ 3;

	 //Span changes the color of the text to light gray to mimic hint and summary
	 span.setSpan(new ForegroundColorSpan(Color.LTGRAY),start,stop,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 TextView frag = (TextView)row.findViewById(R.id.item_list);

	 //New span spans the last span so that span doesn't span the full text size.
	 // Actually just makes the last span into a smaller text
	 Spannable span2 = new SpannableString(span);
	 int start2 = start + temp.getLoc().length() + 3;
	 span2.setSpan(new RelativeSizeSpan(0.8f),start2,stop,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	 frag.setText(span2);

	 return row;
	}
	
	/**
	 * Class used for filtering items in the ArrayAdapter
	 * @author Justin
	 *
	 */
	private class TextFilter extends Filter {
		
		public TextFilter() {}

		/**
		 * filtering by sequence of characters
		 * @param constraint
		 * @return
		 */
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if(constraint != null && constraint.length()>0) {
	            ArrayList<Item> list = new ArrayList<Item>();
	            for(Item a : mList)
	            	if(a.getAll().toLowerCase().contains(constraint.toString().toLowerCase()))
	            		list.add(a);
	            results.values = list;
	            results.count = list.size();
			}
			return results;
		}

		/**
		 * @param constraint
		 * @param results
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			//TODO: refilter
			//MainActivity.update((ArrayList<Item>)results.values);
            notifyDataSetChanged();
			
		}
		
	}
	
}
