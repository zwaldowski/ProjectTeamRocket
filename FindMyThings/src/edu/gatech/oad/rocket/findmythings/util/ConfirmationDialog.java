package edu.gatech.oad.rocket.findmythings.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * CS 2340 - FindMyStuff Android App
 * 
 * The {@link ConfirmationDialog} is a preference subclass that shows
 * a dialog with Yes and No buttons, for the purpose of confirming an action.
 *
 * This preference will persist a boolean into the SharedPreferences key
 * dictated by android:key in the XML preference.
 *
 * Derived from:
 * - http://stackoverflow.com/questions/5365310/creating-a-dialogpreference-from-xml/8818446#8818446
 * - YesNoPreference.java in Android Private SDK
 * 
 * @author TeamRocket
 */
public class ConfirmationDialog extends DialogPreference {
    /** The Yes/No value of the box. */
    private boolean mValue;

    /**
     * constructor
     * @param context
     * @param attrs
     */
    public ConfirmationDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * deals with action to do once dialog is closed
     * @param boolean positiveResult
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (callChangeListener(positiveResult)) {
            setValue(positiveResult);
        }
    }

    /**
     * @param TypedArray a
     * @param int index
     * @return Object
     */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getBoolean(index, false);
    }

    /**
     * @param boolean restorePersistedValue
     * @param Object defaultValue
     */
    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedBoolean(mValue) :
                (Boolean) defaultValue);
    }

    /**
     * @return boolean
     */
    @Override
    public boolean shouldDisableDependents() {
        return !mValue || super.shouldDisableDependents();
    }

    /**
     * Sets the value of this preference, and saves it to the persistent store
     * if required.
     *
     * @param value The value of the preference.
     */
    public void setValue(boolean value) {
        mValue = value;

        persistBoolean(value);

        notifyDependencyChange(!value);
    }

    /**
     * Gets the value of this preference.
     *
     * @return The value of the preference.
     */
    public boolean getValue() {
        return mValue;
    }

}
