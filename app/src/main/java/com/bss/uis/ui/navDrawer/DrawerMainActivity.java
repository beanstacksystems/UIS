package com.bss.uis.ui.navDrawer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.model.User;
import com.bss.uis.ui.registration.RegistrationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawerMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView naveHeaderdate,navHeaderPersonName,navHeaderPersonEmail;
    private ImageView navHeaderProfileImage,profile_image,checkmark_IV, edit_IV;
    EditText popup_contact_et;
    private UISApplicationContext uisContext;
    private Dialog profile_dialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uisContext = UISApplicationContext.getInstance();
        setContentView(R.layout.activity_drawer_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadNavigationHeader();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerMainActivity.this, RegistrationActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_patient, R.id.nav_shelterHome,R.id.nav_members,R.id.nav_gallery,
                R.id.nav_notification,R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void loadNavigationHeader()
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);
        naveHeaderdate = (TextView) navHeaderView.findViewById(R.id.naveHeaderdate);
        navHeaderPersonName =  (TextView)navHeaderView.findViewById(R.id.navHeaderPersonName);
        navHeaderPersonEmail = (TextView)navHeaderView.findViewById(R.id.navHeaderPersonEmail);
        navHeaderProfileImage =(ImageView)navHeaderView.findViewById(R.id.navHeaderProfileImage);
        navHeaderProfileImage.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            openProfileDialog();
        }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        naveHeaderdate.setText(date);
        User user = uisContext.getUser();
        if(null == user)
            return;
        navHeaderPersonName.setText(user.getUserName());
        navHeaderPersonEmail.setText(user.getUserEmail());
    }

    private void openProfileDialog() {
        profile_dialogue = new Dialog(this);
        profile_dialogue.setContentView(R.layout.popup_profile);
        profile_dialogue.setTitle("Custom Alert Dialog");
        profile_dialogue.setCancelable(false);
        profile_image = profile_dialogue.findViewById(R.id.profile_image);
        checkmark_IV = profile_dialogue.findViewById(R.id.checkmark_IV);
        edit_IV = profile_dialogue.findViewById(R.id.edit_IV);
        popup_contact_et = profile_dialogue.findViewById(R.id.popup_contact_et);
        profile_dialogue.show();
    }
}