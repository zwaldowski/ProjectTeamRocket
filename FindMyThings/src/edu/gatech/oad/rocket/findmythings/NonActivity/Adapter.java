package edu.gatech.oad.rocket.findmythings.NonActivity;

import java.util.List;

import edu.gatech.oad.rocket.findmythings.NonActivity.Item;
import edu.gatech.oad.rocket.findmythings.R;

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

import android.widget.TextView;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * @author TeamRocket
 * */
public class Adapter extends ArrayAdapter<Item> {

	/**
	 * Gets context from the activity using this adapter
	 */
	private Context mContext;

	/**
	 * Reference to the list passed into the adapter
	 */
	private List<Item> mList;

	public Adapter(Context context, int textViewResourceId, List<Item> objects) {
		super(context, textViewResourceId, objects);
		
		mContext = context;
		mList = objects;
	}

	public Adapter(Context context, int resource, int textViewResourceId,
			List<Item> objects) {
		super(context,resource,textViewResourceId, objects);
		mContext=context;
		mList = objects;
		
	}
	
	public void setList(List<Item> l) {
		if (!l.isEmpty()) {
			mList = l;
		}
		else mList = null;
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(mList==null)
			return 0;
	    return mList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	 View row = convertView;

	 if (row == null || mList == null) {
         LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
         row = inflater.inflate (R.layout.activity_item_list, parent, false);
	 }
	 if(mList == null || mList.isEmpty() || position>mList.size()) 
		 return row;
	
	 Item temp = mList.get(position);
	 
	 Spannable span = new SpannableString(temp.toString() + " - " + temp.getLoc() + temp.getSummary());
	 int start = temp.toString().length();
	 int stop = temp.getLoc().length()+ temp.toString().length() +  temp.getSummary().length()+ 3;

	 //Span changes the color of the text to light gray to mimic hint and summary
	 span.setSpan(new ForegroundColorSpan(Color.LTGRAY),start,stop,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 TextView frag = (TextView) row.findViewById(R.id.item_list);

	 //New span spans the last span so that span doesn't span the full text size.
	 // Actually just makes the last span into a smaller text
	 Spannable span2 = new SpannableString(span);
	 int start2 = start + temp.getLoc().length() + 3;
	 span2.setSpan(new RelativeSizeSpan(0.8f),start2,stop,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	 frag.setText(span2);

	 return row;
	 
	 
	}
}
