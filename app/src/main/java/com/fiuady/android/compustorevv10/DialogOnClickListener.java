package com.fiuady.android.compustorevv10;

import android.app.DialogFragment;

/**
 * Created by Manuel on 26/04/2017.
 */
public interface DialogOnClickListener {
    void onDialogPositiveClick(DialogFragment dialogFragment, int code);
    void onDialogNegativeClick(DialogFragment dialogFragment, int code);
}
