package com.beanstack.utility.listener;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class DateChooserFocusChangeListener implements View.OnFocusChangeListener{
    TextInputLayout textInputLayout;
    String emptyMessage;
    public DateChooserFocusChangeListener(TextInputLayout textInputLayout, String emptyMessage)
    {
        this.textInputLayout = textInputLayout;
        this.emptyMessage = emptyMessage;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        textInputLayout.setError(null);
        TextInputEditText textInputEditText = (TextInputEditText)v;
        String text = textInputEditText.getText().toString().trim();
        if(null == text || text.isEmpty())
        {
            if(hasFocus)
            {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textInputEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day).show();
            }
            textInputLayout.setError(emptyMessage);
        }
        else
        {
            if(!isValidDate(text))
                textInputLayout.setError("Date is not Correct");
        }
    }
    public boolean isValidDate(String date) {
        boolean check;
        String date1 = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([12][0-9]{3})$";
        check = date.matches(date1);
        return check;
    }
}
