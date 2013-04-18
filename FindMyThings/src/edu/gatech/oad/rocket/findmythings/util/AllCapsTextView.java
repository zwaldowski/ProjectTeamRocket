package edu.gatech.oad.rocket.findmythings.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * A text view that forces its content to be shown in all caps.
 * User: zw
 * Date: 4/17/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllCapsTextView extends TextView implements ViewTreeObserver.OnPreDrawListener {

	public AllCapsTextView(Context context) {
		super(context);
	}

	public AllCapsTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AllCapsTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text.toString().toUpperCase(), type);
	}

}