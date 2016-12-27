package com.hxh19950701.imagedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_FROM_SDCARD = 0;
    public static final int REQUEST_CODE_FROM_CAMERA = 1;

    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFormSDCard:
                setImageFromSDCard();
                break;
            case R.id.btnFormCamera:
                setImageFromCamera();
                break;
        }
    }

    private void setImageFromCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }

    private void setImageFromSDCard() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_FROM_SDCARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FROM_SDCARD && resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivImage.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, 200, 200));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_FROM_CAMERA && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getExtras().getParcelable("data");
            ivImage.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, 200, 200));
        }
    }
}
