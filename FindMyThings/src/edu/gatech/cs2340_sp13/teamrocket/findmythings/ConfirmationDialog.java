package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * The {@link ConfirmationDialog} is a preference subclass that shows
 * a dialog with Yes and No buttons.
 *
 * This preference will persist a boolean into the SharedPreferences key
 * dictated by android:key in the XML preference.
 *
 * Dervied from:
 * http://stackoverflow.com/questions/5365310/creating-a-dialogpreference-from-xml/8818446#8818446
 * YesNoPreference.java in Android Private SDK
 *
 * @attr ref android.R.styleable#DialogPreference_dialogTitle
 * @attr ref android.R.styleable#DialogPreference_dialogMessage
 * @attr ref android.R.styleable#DialogPreference_dialogIcon
 * @attr ref android.R.styleable#DialogPreference_dialogLayout
 * @attr ref android.R.styleable#DialogPreference_positiveButtonText
 * @attr ref android.R.styleable#DialogPreference_negativeButtonText
 *
 */
public class ConfirmationDialog extends DialogPreference {
    /** The Yes/No value of the box. */
    private boolean mValue;

    public ConfirmationDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (callChangeListener(positiveResult)) {
            setValue(positiveResult);
        }
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

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getBoolean(index, false);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedBoolean(mValue) :
                (Boolean) defaultValue);
    }

    @Override
    public boolean shouldDisableDependents() {
        return !mValue || super.shouldDisableDependents();
    }

}