package com.bss.uis.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UIUtil {
    public static Button getButtonWithImg(Context object, Drawable icon, String btnText, int bkg_color, int height, int width) {
        Button button = new Button(object);
        button.setText(btnText);
        button.setBackgroundResource(bkg_color);
        button.setHeight(height);
        button.setWidth(width);
        button.setPadding(5, 5, 5, 5);
        button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        return button;
    }
    public static TextInputLayout getTextInputLayout(Context object, int id, int height, int width, int inputtType, String hint) {
        TextInputLayout textInputLayout = new TextInputLayout(object);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textInputLayout.setHint(hint);
        TextInputEditText textInputEditText = new TextInputEditText(object);
        textInputEditText.setHeight(height);
        textInputEditText.setWidth(width);
        textInputEditText.setGravity(Gravity.CENTER);
        textInputEditText.setId(id);
        textInputEditText.setInputType(inputtType);
        textInputLayout.addView(textInputEditText, editTextParams);
        return textInputLayout;
    }
    public static int getscreenheight(Window window) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return height;
    }

   public static int getscreenwidth(Window window) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
       window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }
}
