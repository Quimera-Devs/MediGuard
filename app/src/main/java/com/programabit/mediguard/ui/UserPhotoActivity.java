package com.programabit.mediguard.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.programabit.mediguard.R;
import com.programabit.mediguard.data.UserService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class UserPhotoActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int CAPTURE_REQUEST_CODE = 0;
    private static final int SELECT_REQUEST_CODE = 1;
    private Button btnSelect, btnUpload;
    private ImageView imageView;
    private Uri filePath;

    private UserService ourRetrofitClient;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photo);
        btnUpload = findViewById(R.id.btnUpload);
        btnSelect = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.userPicture);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://alduxsan.pythonanywhere.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ourRetrofitClient = retrofit.create(UserService.class);
        progressDialog = new ProgressDialog(UserPhotoActivity.this);
        progressDialog.setMessage("Subiendo imagen....");

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckPermission()) {
                    Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(capture, CAPTURE_REQUEST_CODE);
                }
            }
        });


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CheckPermission()) {
                    Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(select, SELECT_REQUEST_CODE);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case CAPTURE_REQUEST_CODE:
            {
                if(resultCode == RESULT_OK){

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    progressDialog.show();
                    ImageUpload(bitmap);

                }

            }
            break;

            case SELECT_REQUEST_CODE:
            {
                if(resultCode == RESULT_OK){

                    try {
                        Uri ImageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImageUri);
                        imageView.setImageBitmap(bitmap);
                        progressDialog.show();
                        ImageUpload(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }

    }

    private void ImageUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        String name = String.valueOf(Calendar.getInstance().getTimeInMillis());
        Call<ResponseClass> call = ourRetrofitClient.UploadImage(name,image);

        call.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                Toast.makeText(UserPhotoActivity.this, "Foto subida con exito", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {
                Toast.makeText(UserPhotoActivity.this, "Falla al subir foto", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    public boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(UserPhotoActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UserPhotoActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UserPhotoActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserPhotoActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(UserPhotoActivity.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(UserPhotoActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(UserPhotoActivity.this)
                        .setTitle("Permission")
                        .setMessage("Por favor concede permisos")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(UserPhotoActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                startActivity(new Intent(UserPhotoActivity
                                        .this, MainActivity.class));
                                UserPhotoActivity   .this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(UserPhotoActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {

            return true;

        }
    }
}