package com.bss.uis.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import com.bss.uis.database.entity.PatientImages;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {
    final static String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static Pattern pattern =  Pattern.compile(PASSWORD_PATTERN);
    private static Matcher matcher;
    public static boolean isCorrectPasswordPolicy(String password)
    {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    @RequiresApi(api = VERSION_CODES.O)
    public static int getAge(int year, int month, int dayOfMonth) {
        return Period.between(
                LocalDate.of(year, month, dayOfMonth),
                LocalDate.now()
        ).getYears();
    }
    public static PatientImages getPatientImage(String imageType, String imageStr)
    {
        PatientImages patientImage = new PatientImages();
        patientImage.setImageType(imageType);
        patientImage.setImageStr(getImageStr(imageStr));
        return patientImage;
    }
    public static List<PatientImages> getPatientImageList(String imageType, List<String> imageStrList)
    {
        List<PatientImages> patientImagesList = new ArrayList<>();
        for(String imageStr:imageStrList)
        {
            PatientImages patientImage = new PatientImages();
            patientImage.setImageType(imageType);
            patientImage.setImageStr(getImageStr(imageStr));
            patientImagesList.add(patientImage);
        }

        return patientImagesList;
    }
    public static String getImageStr(String filePath)
    {
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return Base64.encodeToString(bytes, Base64.URL_SAFE);
    }
    public static String getImagePath(Context context, Uri uri){
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    public static Bitmap getBitmapFromEncodedString(String encodedString){
        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;

    }
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
    public static void getErrorDialog(String contentText, String confirmText, Context context) {
    }

    public static void getNoticeDialog(String contentText, String confirmText, Context context) {
    }

}
