package com.fiuady.android.compustorevv10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Manuel on 20/04/2017.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    public static final String YEAR="yearDate";
    public static final String MONTH="monthDate";
    public static final String DAY="dayDate";
    public static final String SAVED_PICKER_STATE="save_picker";
    public static final String EXTRA_VALUE_CHECKED="value_checked";
    private DatePickerDialog datePickerDialog;
    private int valueChecked;


    int Year,Month,Day;

    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth, int valueofChecked);
        void onDialogNegativeClick(int value);

    }

    OnDateSetListener mDateSetListener;

    public DatePickerFragment()
    {
        //necessary constructor
    }


    public static DatePickerFragment newInstance(int year, int month, int day,int valueOfChecked){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setRetainInstance(true);
        Bundle args= new Bundle();
        args.putInt(YEAR,year);
        args.putInt(MONTH,month);
        args.putInt(DAY,day);
        args.putInt(EXTRA_VALUE_CHECKED,valueOfChecked);
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }



    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        /* Year=  bundle.getInt(YEAR);
         Month = bundle.getInt(MONTH);
         Day = bundle.getInt(DAY);*/
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        valueChecked = bundle.getInt(EXTRA_VALUE_CHECKED);
         final DatePickerDialog dialog = new DatePickerDialog(getActivity(),this ,year,month,day);
        if(savedInstanceState!=null)
        {
        final Bundle internalState = savedInstanceState.getBundle(SAVED_PICKER_STATE);
            dialog.onRestoreInstanceState(internalState);
            int año = dialog.getDatePicker().getYear();
            int mes= dialog.getDatePicker().getMonth();
            int dia = dialog.getDatePicker().getDayOfMonth();
            valueChecked = savedInstanceState.getInt(EXTRA_VALUE_CHECKED);



        }

      dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialog, int which) {
              if (which == DialogInterface.BUTTON_NEGATIVE) {
                  mDateSetListener.onDialogNegativeClick(valueChecked);

              }
          }
      });

                datePickerDialog = dialog;
        return dialog;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (this.mDateSetListener != null) {
            this.mDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth,valueChecked);
        }
    }


    public void setOnDateSetListener(OnDateSetListener dateSetListener) {
        this.mDateSetListener = dateSetListener;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        final Bundle bundle = datePickerDialog.onSaveInstanceState();
        outState.putBundle(SAVED_PICKER_STATE,bundle);
        outState.putInt(EXTRA_VALUE_CHECKED,valueChecked);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if(dialog !=null &&getRetainInstance()){
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
