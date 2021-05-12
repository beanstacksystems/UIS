package com.beanstack.utility.validators;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AutoCompleteTextValidtor implements AutoCompleteTextView.Validator, OnItemClickListener {

    private List<String> listOptions;
    private TextInputLayout textInputLayout;
    public AutoCompleteTextValidtor(TextInputLayout textInputLayout,List<String> listOptions)
    {
        this.textInputLayout = textInputLayout;
        this.listOptions = listOptions;
    }
    @Override
    public boolean isValid(CharSequence text) {
        textInputLayout.setError(null);
        if(null == text)textInputLayout.setError("Please select value");
        if (listOptions.contains(text.toString())) {
            return true;
        }
        textInputLayout.setError("Please select correct value");
        return false;
    }

    @Override
    public CharSequence fixText(CharSequence invalidText) {

        return "";
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        parent.setSelection(position);
    }
}

