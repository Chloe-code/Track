package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class test2 extends AppCompatActivity {
    private CircleImageView circleImageView;
    private Button btnChange;
    pop_window_view mwindow;
    private ChangeHeadImagePop popupWindow = null;
    private RelativeLayout main;
    private Uri iconUri = null;
    private Uri cropImageUri = null;
    public static final String imageDirPath = Environment.getExternalStorageDirectory()+"/DCIM/Camera";
    public static final String takePhotoImageName = "take_photo.jpg";
    public static final String crop_ImageName = "crop_image.jpg";
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private static final int REQUEST_CODE_CROP_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        main = (RelativeLayout) findViewById(R.id.activity_test2);
        circleImageView = (CircleImageView) findViewById(R.id.img_portrait);
        btnChange = (Button) findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mwindow = new pop_window_view(test2.this, itemsOnClick);
                mwindow.showAtLocation(test2.this.findViewById(R.id.activity_test2), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            mwindow.dismiss();
            switch (v.getId()) {
                case R.id.icon_btn_camera:
                    File dir = new File(imageDirPath);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    File file = new File(dir,takePhotoImageName);
                    Log.e("相機存放圖片地址",file.getAbsolutePath());
                    iconUri = provider.getUriForFile(test2.this,file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
                    startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
                    break;
                case R.id.icon_btn_choose:
                    Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                    intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent2, REQUEST_CODE_CHOOSE_IMAGE);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(test2.this,imageDirPath,Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case REQUEST_CODE_TAKE_PHOTO:
                if(null != iconUri){
                    File file = new File(iconUri.getPath());
                    Log.e("文件是否存在",file.exists()+"");
                    if (file.exists()) {
                        startCropImage(iconUri);
                    }
                }
                break;
            case REQUEST_CODE_CHOOSE_IMAGE:
                Log.e("相簿選擇",data.getData()+"");
                if (data.getData() != null) {
                    iconUri = data.getData();
                    startCropImage(iconUri);
                }
                break;
            case REQUEST_CODE_CROP_IMAGE:
                Toast.makeText(test2.this,"剪切完畢",Toast.LENGTH_SHORT).show();
                if(null !=cropImageUri){
                    Log.e("剪切圖片地址",cropImageUri.getPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(imageDirPath+File.separator+crop_ImageName);
                    circleImageView.setImageBitmap(bitmap);
                    bitmap.recycle();
                }
                break;
        }
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(test2.this,"取消",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case REQUEST_CODE_TAKE_PHOTO:
                if(null != iconUri){
                    File file = new File(iconUri.getPath());
                    Log.e("文件是否存在",file.exists()+"");
                    if (file.exists()) {
                        startCropImage(iconUri);
                    }
                }
                break;
            case REQUEST_CODE_CHOOSE_IMAGE:
                Log.e("相簿選擇",data.getData()+"");
                if (data.getData() != null) {
                    iconUri = data.getData();
                    startCropImage(iconUri);
                }
                break;
            case REQUEST_CODE_CROP_IMAGE:
                Toast.makeText(test2.this,"剪切完畢",Toast.LENGTH_SHORT).show();
                if(null !=cropImageUri){
                    Log.e("剪切圖片地址",cropImageUri.getPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(imageDirPath+File.separator+crop_ImageName);
                    circleImageView.setImageBitmap(bitmap);
                    bitmap.recycle();
                }
                break;
        }
    }*/
    public void startCropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        File dir = new File(imageDirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File crop_image = new File(dir,crop_ImageName);
        cropImageUri = provider.getUriForFile(test2.this,crop_image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
        /*popupWindow.setOnItemClickListener(new ChangeHeadImagePop.OnItemClickListener() {
            @Override
            public void onClick(ChangeHeadImagePop.MENUITEM item, String str) {
                switch (item){
                    case ITEM1:
                        File dir = new File(imageDirPath);
                        if(!dir.exists()){
                            dir.mkdirs();
                        }
                        File file = new File(dir,takePhotoImageName);
                        Log.e("相機存放圖片地址",file.getAbsolutePath());
                        iconUri = Uri.fromFile(file);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
                        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
                        break;
                    case ITEM2:
                        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent2, REQUEST_CODE_CHOOSE_IMAGE);
                        break;
                    case ITEM3:

                        break;
                }
            }
        });

    }
    */
}
