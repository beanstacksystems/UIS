package com.bss.uis.ui.navDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.ui.image.ProfileImageFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    private ImageView profileImageView,backArrow,editLink,updateLink;
    private TextView name,email,mobileNo,desgnTv,genderTv;
    private TextInputLayout genderSelectLayout,desgnSelectLayout;
    private AutoCompleteTextView designation,gender;
    private EditText mobileNoEditText;
    private UISApplicationContext uisContext;
    ProfileImageFragment profileImageFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uisContext = UISApplicationContext.getInstance();
        setContentView(R.layout.activity_user_profile);
        profileImageView = findViewById(R.id.user_profile_image);
        profileImageFragment=getProfileImageFragment(false,R.id.user_profile_image_edit_layout);
        name=findViewById(R.id.profile_Name);
        email = findViewById(R.id.profile_email);
        editLink = findViewById(R.id.edit_IV);
        updateLink=findViewById(R.id.checkmark_IV);
        mobileNo = findViewById(R.id.user_contact_tv);
        mobileNoEditText = findViewById(R.id.user_contact_et);
        desgnSelectLayout = findViewById(R.id.profile_desg_spinner_layout);
        genderSelectLayout = findViewById(R.id.profile_gender_spinner_layout);
        desgnTv = findViewById(R.id.user_desg_tv);
        genderTv = findViewById(R.id.user_gender_tv);
        designation = findViewById(R.id.profile_desg_spinner);
        gender = findViewById(R.id.profile_gender_spinner);
        backArrow = findViewById(R.id.user_back);
        backArrow.setColorFilter(R.color.black);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("selectedImagePath", profileImageFragment.getSelectedImagePath());
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(0,0);
            }
        });
        editLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desgnTv.setText("");
                genderTv.setText("");
                mobileNo.setText("");
                mobileNoEditText.setVisibility(View.VISIBLE);
                desgnSelectLayout.setVisibility(View.VISIBLE);
                genderSelectLayout.setVisibility(View.VISIBLE);
                updateLink.setVisibility(View.VISIBLE);
                editLink.setVisibility(View.GONE);
            }
        });
        updateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desgnTv.setText(designation.getText().toString());
                genderTv.setText(gender.getText().toString());
                mobileNo.setText(mobileNoEditText.getText());
                mobileNoEditText.setVisibility(View.GONE);
                genderSelectLayout.setVisibility(View.GONE);
                desgnSelectLayout.setVisibility(View.GONE);
                editLink.setVisibility(View.VISIBLE);
                updateLink.setVisibility(View.GONE);

            }
        });
        initSpinnerView();
    }
    public ProfileImageFragment getProfileImageFragment(Boolean bool, int id) {
        ProfileImageFragment fragment = ProfileImageFragment.newInstance(profileImageView);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }
    private void initSpinnerView()
    {
        ArrayList<String> genderValue = new ArrayList<>();
        genderValue.add("Female");
        genderValue.add("Male");
        genderValue.add("ThirdGender");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(uisContext.getContext(), android.R.layout.simple_spinner_item, genderValue);
        gender.setAdapter(adapter);
    }
}