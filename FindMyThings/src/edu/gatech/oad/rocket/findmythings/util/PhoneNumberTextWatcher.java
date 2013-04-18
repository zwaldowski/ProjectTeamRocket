package edu.gatech.oad.rocket.findmythings.util;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;

/**
 * Watcher that adds and deletes hyphens while entering a phone number.
 * User: zw
 * Date: 4/18/13
 * Time: 2:02 AM
 */
public class PhoneNumberTextWatcher implements TextWatcher {

	private boolean isFormatting;
	private boolean deletingHyphen;
	private int hyphenStart;
	private boolean deletingBackward;

	@Override
	public void afterTextChanged(Editable text) {
		if (isFormatting)
			return;

		isFormatting = true;

		// If deleting hyphen, also delete character before or after it
		if (deletingHyphen && hyphenStart > 0) {
			if (deletingBackward) {
				if (hyphenStart - 1 < text.length()) {
					text.delete(hyphenStart - 1, hyphenStart);
				}
			} else if (hyphenStart < text.length()) {
				text.delete(hyphenStart, hyphenStart + 1);
			}
		}
		if (text.length() == 3 || text.length() == 7) {
			text.append('-');
		}

		isFormatting = false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		if (isFormatting)
			return;

		// Make sure user is deleting one char, without a selection
		final int selStart = Selection.getSelectionStart(s);
		final int selEnd = Selection.getSelectionEnd(s);
		if (s.length() > 1 // Can delete another character
				&& count == 1 // Deleting only one character
				&& after == 0 // Deleting
				&& s.charAt(start) == '-' // a hyphen
				&& selStart == selEnd) { // no selection
			deletingHyphen = true;
			hyphenStart = start;
			// Check if the user is deleting forward or backward
			if (selStart == start + 1) {
				deletingBackward = true;
			} else {
				deletingBackward = false;
			}
		} else {
			deletingHyphen = false;
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}
}
