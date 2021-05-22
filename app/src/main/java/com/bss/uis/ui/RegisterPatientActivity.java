package com.bss.uis.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.ui.image.ImageCaptureFragment;
import com.bss.uis.util.AppUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPatientActivity extends AppCompatActivity {


    Button address_btn,dateOfBirth_btn;
    Dialog address_dialogue;
    Button upDateAddbutton;
    CircleImageView profile_image;
    TextInputEditText textInputEditTextPin,textInputEditTextState,textInputEditTextDist;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        address_dialogue =UIUtil.getPopupDialog(this);
        address_btn = findViewById(R.id.address_btn);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        getFragment(false,R.id.id_proof_imageview_Layout);
        getFragment(false,R.id.medicalreport_imageviewLayout);
        dateOfBirth_btn = findViewById(R.id.dateOfBirth_btn);

        dateOfBirth_btn.setOnClickListener(new CustomOnclickListener());
        profile_image.setOnClickListener(new CustomOnclickListener());
        address_btn.setOnClickListener(new CustomOnclickListener());
        Spinner spinner_blood_group = (Spinner) findViewById(R.id.spinner_blood_group);
        ArrayAdapter bloodgrupAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AppConstants.Blood_Groups);
        spinner_blood_group.setAdapter(bloodgrupAdapter);
    }

    private void selectDateOfBirth() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @RequiresApi(api = VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateOfBirth_btn.setText("Birth Date: "+dayOfMonth + "-" + (monthOfYear + 1)
                                + "-" + year+" \n Age: "+ AppUtil.getAge(year, monthOfYear + 1,dayOfMonth));
                        dateOfBirth_btn.setBackgroundResource(R.drawable.bg_shape_green);
                    }


                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @RequiresApi(api = VERSION_CODES.M)
    public void openAddressPopup() {
        LinearLayout linearlayoutAddressPopup = address_dialogue.findViewById(R.id.linearlayoutAddressPopup);
        linearlayoutAddressPopup.setMinimumWidth(UIUtil.getscreenwidth(address_dialogue.getWindow()));
        initAddressPopUp();
    }

    @RequiresApi(api = VERSION_CODES.M)
    private void initAddressPopUp() {
        textInputEditTextPin = address_dialogue.findViewById(R.id.pincode);
        textInputEditTextDist = address_dialogue.findViewById(R.id.dist);
        textInputEditTextState = address_dialogue.findViewById(R.id.state);
        upDateAddbutton = address_dialogue.findViewById(R.id.upDateAddbutton);
        upDateAddbutton.setOnClickListener(new CustomOnclickListener());
        textInputEditTextPin.addTextChangedListener(new CustomTextValidator(textInputEditTextPin){
            @Override
            public void validate(TextView textView, String text) {
//                if(text.length() == 6)
//                    new APIServiceImpl().fetchPinData(text,textInputEditTextState,textInputEditTextDist);
            }
        });
        createPinPopup(address_dialogue);
    }
    @RequiresApi(api = VERSION_CODES.M)
    private void createPinPopup(final Dialog address_dialogue)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(address_dialogue.getContext(),null));
        int displayWidth = UIUtil.getscreenwidth(address_dialogue.getWindow());
        int displayHeight = UIUtil.getscreenheight(address_dialogue.getWindow());
        final TextInputLayout textInputLayout = UIUtil.getTextInputLayout(address_dialogue.getContext(),
                0,(int)(displayHeight*0.2f),displayWidth-200, InputType.TYPE_CLASS_NUMBER,"PinCode");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int dialogWindowWidth = displayWidth-100;
        int dialogWindowHeight = (int) (displayHeight * 0.5f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog,int which)
                            {
                                textInputEditTextPin.setText(textInputLayout.getEditText().getText().toString());
                                address_dialogue.show();
                            }
                        });
        builder.setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        dialog.dismiss();
                        address_dialogue.show();
                    }
                });
        AlertDialog dialog = builder.create();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_alert_dialog);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.setView(textInputLayout);
        dialog.show();
    }



    class CustomOnclickListener implements View.OnClickListener
    {
        @RequiresApi(api = VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            if (v == address_btn) {
                openAddressPopup();
            }
            if(v == dateOfBirth_btn){
                selectDateOfBirth();
            }
            if(v == upDateAddbutton){
                if(isRequiredDataFilled()){
                    address_btn.setBackgroundResource(R.drawable.bg_shape_green);
                    address_btn.setText("Edit Address");
                    address_dialogue.dismiss();
                }

            }
        }
    }
    private boolean isRequiredDataFilled()
    {
        //                captureAddressDetails();
        return true;
    }
    public Fragment getFragment(Boolean bool,int id) {
        Fragment fragment = new ImageCaptureFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }

}