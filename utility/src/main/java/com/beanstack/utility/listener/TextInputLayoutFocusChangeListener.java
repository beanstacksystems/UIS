package com.beanstack.utility.listener;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextInputLayoutFocusChangeListener implements View.OnFocusChangeListener{
    TextInputLayout textInputLayout;
    String emptyMessage;
    public TextInputLayoutFocusChangeListener(TextInputLayout textInputLayout,String emptyMessage)
    {
        this.textInputLayout = textInputLayout;
        this.emptyMessage = emptyMessage;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        textInputLayout.setError(null);
        TextInputEditText textInputEditText = (TextInputEditText)v;
        String text = textInputEditText.getText().toString();
        if(null == text || text.isEmpty())
            textInputLayout.setError(emptyMessage);
    }
}
