package com.bss.uis.ui.listener;

import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;

public class CameraActivityListener extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private Dialog dialog;
    public CameraActivityListener()
    {
    }
    public CameraActivityListener(Context context,Dialog dialog)
    {
        this.context = context;
        this.dialog = dialog;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @RequiresApi(api = VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_capture_image) {
            selectImage(dialog,5);
        }

    }
    @RequiresApi(api = VERSION_CODES.M)
    private void selectImage(Dialog popupDialog,final int requestCode) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(popupDialog.getContext(),null));
        builder.setTitle("Select picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @RequiresApi(api = VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Intent userIntent = null;
                if (options[item].equals("Take Photo")) {
                    if (ActivityCompat.checkSelfPermission(((AlertDialog) dialog).getContext(), permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CameraActivityListener.this,new String[]{permission.CAMERA}, AppConstants.MY_CAMERA_PERMISSION_CODE);
                    }
                    else {
                        userIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    userIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                    return;
                }
                ((Activity)getApplicationContext()).startActivityForResult(userIntent , requestCode);
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.ID_SCAN_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            idproofimageviewLayout.addView(UIUtil.getImageViewBitMap(this, data));
        }
    }
}
