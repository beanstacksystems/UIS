package com.bss.uis.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bss.uis.R;
import com.bss.uis.service.ISelectionService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

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
    public static TextInputLayout getTextInputLayout(Context object, int id, int inputType, String hint) {
        TextInputLayout textInputLayout = new TextInputLayout(object,null,R.style.TextInputLayoutStyleBox);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textInputLayout.setLayoutParams(editTextParams);
        editTextParams.setMargins(5,5,5,5);
        textInputLayout.setHint(hint);
        TextInputEditText textInputEditText = new TextInputEditText(object);
        textInputEditText.setGravity(Gravity.CENTER);
        textInputEditText.setId(id);
        textInputEditText.setInputType(inputType);
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
    public static Drawable decodeDrawable(Context context, String name,String base64) {
        Drawable ret = null;
        if (!base64.equals("")) {
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    Base64.decode(base64.getBytes(), Base64.DEFAULT));
            ret = Drawable.createFromResourceStream(context.getResources(),
                    null, bais, null, null);
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    public static ImageView getImageViewBitMap(Context context, Bitmap bitmap)
    {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
        imageParams.setMargins(10,5,10,10);
        imageView.setLayoutParams(imageParams);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }
    public static LinearLayout getCloseableLinearLayout(Context context, int orientation, int index, int bgResource, int xHeight, int xWidth)
    {
       final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(orientation);
        linearLayout.setId(index);
        if (bgResource > -1)
            linearLayout.setBackgroundResource(bgResource);
        LinearLayout.LayoutParams paramsLinearLayout = new LinearLayout.LayoutParams(xWidth, xHeight);
        paramsLinearLayout.setMargins(1, 1, 1, 1);
        linearLayout.setLayoutParams(paramsLinearLayout);
        LinearLayout imageLayout = new LinearLayout(context);
        imageLayout.setGravity(Gravity.RIGHT);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_close);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(30, 30));
        imageLayout.addView(imageView);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();

            }
        });
        linearLayout.addView(imageLayout);
        return linearLayout;
    }
    public static LinearLayout getLinearLayout(Context context, int orientation, int index, int bgResource, int xHeight, int xWidth) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(orientation);
        linearLayout.setId(index);
        if (bgResource > -1)
            linearLayout.setBackgroundResource(bgResource);
        LinearLayout.LayoutParams paramsLinearLayout = new LinearLayout.LayoutParams(xWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsLinearLayout.setMargins(10, 5, 10, 15);
        linearLayout.setLayoutParams(paramsLinearLayout);
        return linearLayout;
    }
    public static Dialog getPopupDialog(Context context)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_address);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        return dialog;
    }
    public static Dialog getSelectPopupDialog(Context context, String title,String[] options,
                                              TextView view,TextInputLayout genderLayout)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_select);
        RelativeLayout radioLinearLayout = dialog.findViewById(R.id.selectpopup_radioLayout);
        radioLinearLayout.addView(getRadioGroup(context,options,LinearLayout.VERTICAL,view,dialog,genderLayout));
        TextView headerText = dialog.findViewById(R.id.SelectPopUpHeader);
        dialog.setTitle(title);
        headerText.setText(title);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                view.clearFocus();
            }
        });
        return dialog;
    }
    public static RadioGroup getRadioGroup(Context context, String[] options,
                                           int orientation,TextView v,Dialog dialog,
                                           TextInputLayout genderLayout) {
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(orientation);
        radioGroup.setGravity(Gravity.LEFT);
        for (String option : options) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        v.setText(btn.getText().toString());
                        dialog.dismiss();
                        genderLayout.setError(null);
                        return;
                    }
                }
            }
        });
        return radioGroup;
    }
    public static Bitmap getScaledBitmap(Bitmap bitmap,int newWidth,int newHeight)
    {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
    public static Dialog getImagePopupDialog(Context context)
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_viewer);
        ImageView closeImageView = dialog.findViewById(R.id.closeImageViewer);
        closeImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        return dialog;
    }
    public static void updateButtonStatus(Button button,boolean isEnabled,int bgColor,String btnText)
    {
        button.setEnabled(isEnabled);
        button.setBackgroundResource(bgColor);
        if(null !=btnText)
            button.setText(btnText);
    }
    public static boolean isContainsValidCharacter(String text)
    {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern textPattern = Pattern.compile(regex);
        return textPattern.matcher(text).matches();
    }
    public static boolean isValidNumber(String number) {
        String regex = "^[0-9]+$";
        Pattern noPattern = Pattern.compile(regex);
        return noPattern.matcher(number).matches();
    }

    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+[a-z]+", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }
    public static Date getDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void updateImageView(String imageUrl,ImageView imageView)
    {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL imageURL = new URL(imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public static Spinner getSpinnerView(Context context, int id, String[] option, ISelectionService selectionService) {
        Spinner spinner = new Spinner(context);
        spinner.setId(id);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, option);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setGravity(Gravity.CENTER);
//        spinner.setBackgroundResource(R.drawable.bubble_shape_edittext);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectionService.onSelected(parentView.getSelectedItem().toString(), parentView.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return spinner;
    }
}
