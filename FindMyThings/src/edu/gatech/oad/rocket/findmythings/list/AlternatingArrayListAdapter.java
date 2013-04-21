package edu.gatech.oad.rocket.findmythings.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.R;

/**
 * Array adapter that swaps colors on every other list item.
 * User: zw
 * Date: 4/14/13
 * Time: 2:12 AM
 */
public class AlternatingArrayListAdapter<T extends TwoLineListProvider> extends ArrayListAdapter<T> {

	private static final int primarySelectableResource = R.drawable.table_background_selector;
	private static final int secondarySelectableResource = R.drawable.table_background_alternate_selector;
	private static final int primaryResource = R.color.pager_background;
	private static final int secondaryResource = R.color.pager_background_alternate;
	private static final int cellLayout = R.layout.list_two_item_cell;

	public AlternatingArrayListAdapter(Context context) {
		super(context, cellLayout);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout row = inflateRowView(convertView, cellLayout);

		TextView mText1 = (TextView) row.findViewById(R.id.item_cell_title);
		TextView mText2 = (TextView) row.findViewById(R.id.item_cell_summary);

		TwoLineListProvider item = getItem(position);

		mText1.setText(item.getTitle());
		mText2.setText(item.getDescription());

		if (this.isEnabled(position)) {
			if (position % 2 != 0)
				row.setBackgroundResource(primarySelectableResource);
			else
				row.setBackgroundResource(secondarySelectableResource);
		} else {
			if (position % 2 != 0)
				row.setBackgroundResource(primaryResource);
			else
				row.setBackgroundResource(secondaryResource);
		}

		return row;
	}
}
