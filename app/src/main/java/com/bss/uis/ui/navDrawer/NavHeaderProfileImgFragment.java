package com.bss.uis.ui.navDrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bss.uis.R;
import com.bss.uis.model.User;
import com.bss.uis.ui.image.ProfileImageFragment;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NavHeaderProfileImgFragment extends Fragment {
    ImageView userProfileImg;
    ProfileImageFragment profileImageFragment;
    private TextView naveHeaderdate,navHeaderPersonName,navHeaderPersonEmail;
    private User user;
    private View fragmentView;
    private NavigationView navigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.nav_header_main, container, false);
        initView(fragmentView);
        navigationView.addHeaderView(fragmentView);
        return fragmentView;
    }
    public NavHeaderProfileImgFragment() {
        // Required empty public constructor
    }
    public View getFragmentView(){
        return fragmentView;
    }
    public static NavHeaderProfileImgFragment newInstance(User user,NavigationView navigationView) {
        NavHeaderProfileImgFragment fragment = new NavHeaderProfileImgFragment();
        fragment.user = user;
        fragment.navigationView = navigationView;
        return fragment;
    }
    private void initView(View fragmentView) {
        userProfileImg = (ImageView)fragmentView.findViewById(R.id.navHeaderProfileImage);
//        profileImageFragment = getProfileImageFragment(false,R.id.user_profile_image_edit_layout);
        naveHeaderdate = (TextView) fragmentView.findViewById(R.id.naveHeaderdate);
        navHeaderPersonName =  (TextView)fragmentView.findViewById(R.id.navHeaderPersonName);
        navHeaderPersonEmail = (TextView)fragmentView.findViewById(R.id.navHeaderPersonEmail);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        naveHeaderdate.setText(date);
        if(null == user)
            return;
        navHeaderPersonName.setText(user.getUserName());
        navHeaderPersonEmail.setText(user.getUserEmail());
    }
    public ProfileImageFragment getProfileImageFragment(Boolean bool, int id) {
        ProfileImageFragment fragment = ProfileImageFragment.newInstance(userProfileImg);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }
}
