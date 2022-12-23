package com.bss.uis.ui.image;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.ui.image.adapter.SelectedImageAdapter;
import com.bss.uis.util.AppUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageCaptureFragment extends Fragment {
    private String imageCaptureId;
    ImageView imageView;
    File imageFile;
    String imagePath;
    RecyclerView selectedImageRecyclerView;
    SelectedImageAdapter selectedImageAdapter;
    ArrayList<String> selectedImageList;
    int imageCount = 0;
    String[] projection = {MediaStore.MediaColumns.DATA};
    public ImageCaptureFragment() {
        // Required empty public constructor
    }

    public static ImageCaptureFragment newInstance(String imageCaptureId,int imageCount) {
        ImageCaptureFragment fragment = new ImageCaptureFragment();
        Bundle args = new Bundle();
        args.putString("imageCaptureId", imageCaptureId);
        fragment.setArguments(args);
        fragment.setImageCaptureId(imageCaptureId);
        fragment.setImageCount(imageCount);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageCaptureId = getArguments().getString("imageCaptureId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        selectedImageList = new ArrayList<>();
        View view =  inflater.inflate(R.layout.fragment_image_capture, container, false);
        imageView = (ImageView) view.findViewById(R.id.id_capture_image);
        selectedImageRecyclerView = view.findViewById(R.id.selected_recycler_img_view);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        if (isStoragePermissionGranted())
            setSelectedImageList();
        checkCameraPermision();
        return view;
    }
    public void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    takePicture();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    getPickImageIntent();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AppConstants.ID_SCAN_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            addImage(imagePath);
        }
        if (requestCode == AppConstants.SELECT_GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data.getData() != null) {
                Uri uri = data.getData();
                getImageFilePath(uri);
            }
        }
    }
    public void getPickImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, AppConstants.SELECT_GALLERY_REQUEST);
    }
    // start the image capture Intent
    public void takePicture() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Continue only if the File was successfully created;
        File photoFile = createImageFile();
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(cameraIntent, AppConstants.ID_SCAN_CAMERA_REQUEST);
        }
    }
    public File createImageFile() {
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + dateTime + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        imagePath = "file:" + imageFile.getAbsolutePath();
        return imageFile;
    }
    // add image in selectedImageList and imageList
    public void addImage(String filePath) {
        if(selectedImageList.size() == imageCount)
            selectedImageList.remove(0);
        selectedImageList.add(filePath);
        selectedImageAdapter.notifyDataSetChanged();
    }
    public void setSelectedImageList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        selectedImageRecyclerView.setLayoutManager(layoutManager);
        selectedImageAdapter = new SelectedImageAdapter(getActivity(), selectedImageList);
        selectedImageRecyclerView.setAdapter(selectedImageAdapter);
    }
    // Get image file path
    public void getImageFilePath(Uri uri) {
        String absolutePathOfImage = AppUtil.getImagePath(getActivity(),uri);//cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        if (absolutePathOfImage != null)
            checkImage(absolutePathOfImage);
        else
           checkImage(String.valueOf(uri));
    }

    // add image in selectedImageList and imageList
    public void checkImage(String filePath) {
        // Check before adding a new image to ArrayList to avoid duplicate images
        if (!selectedImageList.contains(filePath)) {
            addImage(filePath);
        }
    }
    public boolean checkCameraPermision() {
        int CAMERA_PERM = ContextCompat.checkSelfPermission(UISApplicationContext.getInstance().getContext(), permission.CAMERA);
        if ((CAMERA_PERM != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, AppConstants.ID_SCAN_CAMERA_REQUEST);
            return false;
        }
        return true;
    }
    public boolean isStoragePermissionGranted() {
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(UISApplicationContext.getInstance().getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.STORAGE_PERMISSION);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.STORAGE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setSelectedImageList();
        }
    }
    public List<String> getSelectedImage()
    {
        return selectedImageList;
    }
}