package edu.gatech.oad.rocket.findmythings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.DBItem;
import edu.gatech.oad.rocket.findmythings.util.EnumHelper;

import java.util.Map;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link MainActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 *
 * @author TeamRocket
 */
public class ItemDetailFragment extends Fragment {

	/**
	 * The content this fragment is presenting.
	 */
	public static DBItem mItem;

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
			// yes this is weird, but it works, trust me
			@SuppressWarnings("unchecked")
			Map<String, Object> mapFromBundle = (Map<String, Object>)Map.class.cast(extraInfo.get(ItemDetailActivity.ITEM_EXTRA));
			mItem = new DBItem();
			mItem.putAll(mapFromBundle);
			getActivity().setTitle(mItem.getTitle());
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
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.item_detail))
					.setText(mItem.getDescription());
			((TextView) rootView.findViewById(R.id.loc_detail))
					.setText(mItem.getLocation());
			Category cat = Category.valueOf(mItem.getCategory());
			String catName = EnumHelper.localizedFromArray(getActivity(), R.array.item_category, cat);
			((TextView) rootView.findViewById(R.id.cat_detail))
					.setText(catName);
			// TODO: probably make this string friendlier
			((TextView) rootView.findViewById(R.id.date_detail))
					.setText(mItem.getDate().toString());
			((TextView) rootView.findViewById(R.id.reward_detail))
					.setText("$" + Integer.toString(mItem.getReward()));
		}

		return rootView;
	}

	protected DBItem getItem() {
		return mItem;
	}

}
