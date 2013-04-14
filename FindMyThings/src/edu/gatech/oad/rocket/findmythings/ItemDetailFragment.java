package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.api.services.fmthings.model.DBItem;
import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.Item;
import edu.gatech.oad.rocket.findmythings.util.EnumHelper;
import edu.gatech.oad.rocket.findmythings.util.ModelHelper;

import java.util.Date;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 *
 * @author TeamRocket
 */
public class ItemDetailFragment extends Fragment {

	/**
	 * The content this fragment is presenting.
	 */
	public static Item mItem;
	public static DBItem mItemNew;

	public static ItemDetailFragment newInstance(DBItem item) {
		ItemDetailFragment f = new ItemDetailFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putParcelable(ItemDetailActivity.ITEM_EXTRA, item);
		f.setArguments(args);

		return f;
	}


	public static ItemDetailFragment newInstance(int itemID) {
		ItemDetailFragment f = new ItemDetailFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("id", itemID);
		f.setArguments(args);

		return f;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {}

	/**
	 * creates new instance of ItemDetailActivity
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extraInfo = getArguments();
		if (extraInfo != null) {
			mItemNew = extraInfo.getParcelable(ItemDetailActivity.ITEM_EXTRA);
			if (mItemNew == null) {
				int value = extraInfo.getInt("id");
				mItem = MainActivity.currList.get(value);
			}
		}
	}

	/**
	 * creates all UI features of the window
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItemNew != null) {
			((TextView) rootView.findViewById(R.id.item_detail))
					.setText(mItemNew.getDescription());
			((TextView) rootView.findViewById(R.id.loc_detail))
					.setText(mItemNew.getLocation());
			Category cat = Category.valueOf(mItemNew.getCategory());
			String catName = EnumHelper.localizedFromArray(getActivity(), R.array.item_category, cat);
			((TextView) rootView.findViewById(R.id.cat_detail))
					.setText(catName);
			Date date = mItem.getDate();
			String dateString = ModelHelper.getDateString(date);
					((TextView) rootView.findViewById(R.id.date_detail))
					.setText(dateString);
			((TextView) rootView.findViewById(R.id.reward_detail))
					.setText("$" + Integer.toString(mItemNew.getReward()));
		} else if (mItem != null) {
			// TODO remove
			((TextView) rootView.findViewById(R.id.item_detail))
					.setText(mItem.getDescription());
			((TextView) rootView.findViewById(R.id.loc_detail))
					.setText(mItem.getLoc());
			String catName = EnumHelper.localizedFromArray(getActivity(), R.array.item_category, mItem.getCat());
			((TextView) rootView.findViewById(R.id.cat_detail))
					.setText(catName);
			((TextView) rootView.findViewById(R.id.date_detail))
					.setText(mItem.getDateString());
			((TextView) rootView.findViewById(R.id.reward_detail))
					.setText("$" + Integer.toString(mItem.getReward()));

		}

		return rootView;
	}

	protected  Item getItem() {
		return mItem;
	}

	protected DBItem getItemNew() {
		return mItemNew;
	}

}
