package com.bss.uis.ui.navDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.beanstack.showcase.MaterialShowcaseSequence;
import com.beanstack.showcase.MaterialShowcaseView;
import com.beanstack.showcase.ShowcaseConfig;
import com.beanstack.utility.service.NavigationService;
import com.beanstack.utility.service.impl.NavigationServiceImpl;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.model.User;
import com.bss.uis.service.UserService;
import com.bss.uis.service.impl.UserServiceImpl;
import com.bss.uis.ui.registration.RegistrationActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawerMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView naveHeaderdate,navHeaderPersonName,navHeaderPersonEmail;
    private ImageView navHeaderProfileImage;
    private UISApplicationContext uisContext;
    FloatingActionButton fab;
    Toolbar toolbar;
    private UserService userService;
    private NavigationService navigationService;
    private static final String SHOWCASE_ID = "DrawerMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uisContext = UISApplicationContext.getInstance();
        setContentView(R.layout.activity_drawer_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadNavigationHeader();
        fab = findViewById(R.id.fab);
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
        presentShowcaseSequence();
        updateUI();
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
        MenuItem logout = navigationView.getMenu().findItem(R.id.logout);
        naveHeaderdate = (TextView) navHeaderView.findViewById(R.id.naveHeaderdate);
        navHeaderPersonName =  (TextView)navHeaderView.findViewById(R.id.navHeaderPersonName);
        navHeaderPersonEmail = (TextView)navHeaderView.findViewById(R.id.navHeaderPersonEmail);
        navHeaderProfileImage =(ImageView)navHeaderView.findViewById(R.id.navHeaderProfileImage);
        logout.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                userService = new UserServiceImpl();
                userService.logout(navigationService);
                return false;
            }
        });
        navHeaderProfileImage.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DrawerMainActivity.this, UserProfileActivity.class);
            startActivityForResult(intent,100);
        }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        naveHeaderdate.setText(date);
        User user = uisContext.getUser();
        if(null == user)
            return;
        navHeaderPersonName.setText(user.getUsername());
        navHeaderPersonEmail.setText(user.getUseremail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data.hasExtra("selectedImagePath") &&
                    null != data.getExtras().getString("selectedImagePath")) {
                Glide.with(this)
                        .load(data.getExtras().getString("selectedImagePath") )
                        .placeholder(R.color.codeGray)
                        .centerCrop()
                        .into(navHeaderProfileImage);
                Toast.makeText(
                        this,
                        "Your reult is :  "+data.getExtras().getString("selectedImagePath") + " " + data.getExtras().getString("key2"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void presentShowcaseSequence() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);
        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
            }
        });
        sequence.setConfig(config);
        sequence.addSequenceItem(navHeaderProfileImage, "This is profile button \nPlease check your details Here.", "Next");
        sequence.addSequenceItem(fab, "Be a paid user to avail complete feature.", "Next");

//        sequence.addSequenceItem(
//                new MaterialShowcaseView.Builder(this)
//                        .setSkipText("SKIP")
//                        .setTarget(imageView_swipe)
//                        .setDismissText("GOT IT")
//                        .setContentText("Try it...")
//                        .withRectangleShape(true)
//                        .build()
//        );


        sequence.start();

    }
    private void updateUI() {
            navigationService = new NavigationServiceImpl(DrawerMainActivity.this, null);
    }
}