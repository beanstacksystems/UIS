package com.bss.uis.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;

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
}
