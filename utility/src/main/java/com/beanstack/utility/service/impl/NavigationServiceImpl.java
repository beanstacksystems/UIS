package com.beanstack.utility.service.impl;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.beanstack.utility.service.NavigationService;


public class NavigationServiceImpl implements NavigationService {
    private AppCompatActivity appCompatActivity;
    private Class clasz;

    public NavigationServiceImpl(AppCompatActivity appCompatActivity, Class clasz) {
        this.appCompatActivity = appCompatActivity;
        this.clasz = clasz;
    }
    @Override
    public void finishAndNavigate() {
        Intent intent = new Intent(appCompatActivity, clasz);
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }
    @Override
    public void navigate() {
        Intent intent = new Intent(appCompatActivity, clasz);
        appCompatActivity.startActivity(intent);
    }

    @Override
    public void exitApp() {
        appCompatActivity.finishAffinity();
        System.exit(0);
    }

    @Override
    public void buttonAction() {

    }
}
