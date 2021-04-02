package com.bss.uis.service.impl;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.bss.uis.service.NavigationService;

public class NavigationServiceImpl implements NavigationService {
    private AppCompatActivity appCompatActivity;
    private Class clasz;

    public NavigationServiceImpl(AppCompatActivity appCompatActivity, Class clasz) {
        this.appCompatActivity = appCompatActivity;
        this.clasz = clasz;
    }
    @Override
    public void navigate() {
        Intent intent = new Intent(appCompatActivity, clasz);
        appCompatActivity.startActivity(intent);
    }
}
