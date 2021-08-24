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
import com.bss.uis.ui.UIUtil;
import com.bss.uis.ui.image.ProfileImageFragment;
import com.bss.uis.util.AppUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    private ImageView profileImageView,backArrow,editLink,updateLink;
    private TextView name,email,mobileNo,occuTv,genderTv,userroletv,userroledef,updatedon;
    private TextInputLayout genderSelectLayout,desgnSelectLayout;
    private AutoCompleteTextView occupation,gender;
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
        occuTv = findViewById(R.id.user_occu_tv);
        genderTv = findViewById(R.id.user_gender_tv);
        occupation = findViewById(R.id.profile_occu_spinner);
        gender = findViewById(R.id.profile_gender_spinner);
        userroletv = findViewById(R.id.userRoletv);
        userroledef =findViewById(R.id.userRoledef);
        updatedon = findViewById(R.id.updatedOn);
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
                occuTv.setText("");
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
                occuTv.setText(occupation.getText().toString());
                genderTv.setText(gender.getText().toString());
                mobileNo.setText(mobileNoEditText.getText());
                mobileNoEditText.setVisibility(View.GONE);
                genderSelectLayout.setVisibility(View.GONE);
                desgnSelectLayout.setVisibility(View.GONE);
                editLink.setVisibility(View.VISIBLE);
                updateLink.setVisibility(View.GONE);

            }
        });
        UIUtil.updateImageView(uisContext.getUser().getImageurl(),profileImageView);
        name.setText(UISApplicationContext.getInstance().getUser().getUsername());
        email.setText(UISApplicationContext.getInstance().getUser().getUseremail());
        userroletv.setText(AppUtil.getRoleName(uisContext,false));
        userroledef.setText(AppUtil.getRoleName(uisContext,true));
        updatedon.setText(updatedon.getText()+" "+uisContext.getUser().getUsername());
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
        ArrayList<String> genderValue = AppUtil.getMasterByType("gender");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(uisContext.getContext(), android.R.layout.simple_spinner_item, genderValue);
        gender.setAdapter(adapter);

        ArrayList<String> occuValue = AppUtil.getMasterByType("occupationtype");
        ArrayAdapter<String> occuValueadapter = new ArrayAdapter<>(uisContext.getContext(), android.R.layout.simple_spinner_item, occuValue);
        gender.setAdapter(occuValueadapter);
    }
}