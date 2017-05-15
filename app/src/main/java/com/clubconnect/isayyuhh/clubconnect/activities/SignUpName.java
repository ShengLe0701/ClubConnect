package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.datas.StudentSignUpData;
import com.clubconnect.isayyuhh.clubconnect.utils.BitmapUtils;
import com.clubconnect.isayyuhh.clubconnect.utils.CameraUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//This class is for inputing the user name and profile image in progressing signup.
public class SignUpName extends BaseActivity implements SurfaceHolder.Callback {

    @Bind(R.id.sign_up_b)
    Button signUpB;
    @Bind(R.id.sign_up_name)
    EditText nameET;

    /////////Camera Setting////////////////
    private Camera mCamera;
    private SurfaceView surfaceView;
    private SurfaceHolder mHolder;
    private int mCameraId = 1;
    private Context context;

    private int screenWidth;
    private int screenHeight;
    private int index;
//    private int menuPopviewHeight;
    private int animHeight;
    private int light_num = 0;
    private int delay_time;
    private int delay_time_temp;
    private boolean isview = false;
    private boolean is_camera_delay;
    private ImageView imgClose;
//    private int picHeight;
    private String imgPath;
    ///////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_name);
        ButterKnife.bind(this);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();

        this.setFab();

        ///Camera Setting/////
        context = this;
        initView();
        initData();

        imgPath = "";
        //////////////////////

    }

    private void setFab() {
     //   signUpB.hide();
        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabListener();
            }
        });
    }

    public void fabListener() {
        boolean invalidform = false;

        if (nameET.getText().toString().equals("")) {
            Toast.makeText(SignUpName.this, "Error : Please enter a name.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }

        if (imgPath.toString().equals("")) {
            Toast.makeText(SignUpName.this, "Error : Please enter a profile image.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }


        if (invalidform) {
            return;
        }


        Intent OutIntent = new Intent(mContext, SignUpPhone.class);
        OutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        StudentSignUpData.getInstance().m_strName = nameET.getText().toString();

        StudentSignUpData.getInstance().m_strProfileImagePath = imgPath;
        StudentSignUpData.getInstance().m_strProfileImageWidth = screenWidth;
        StudentSignUpData.getInstance().m_strProfileImageHeight = screenHeight;

        startActivity(OutIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_activity_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


///////////////Camera Setting/////////////////////////////////
private void initView() {
    surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

    mHolder = surfaceView.getHolder();
    mHolder.addCallback(this);
    surfaceView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CaptureListener();
        }
    });

    imgClose = (ImageView) findViewById(R.id.camera_close);
    imgClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CloseListener();
        }
    });
    imgClose.setVisibility(View.INVISIBLE);

}

    public void CaptureListener() {
        //imgPhoto.setVisibility(View.VISIBLE);

        surfaceView.setOnClickListener(null);
        imgClose.setVisibility(View.VISIBLE);
        captrue();
    }

    private void CloseListener()
    {
        imgClose.setVisibility(View.INVISIBLE);
        onResume();
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureListener();
            }
        });
    }

    private void initData() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        screenWidth = (int)(dm.widthPixels / 3.5);
//        screenHeight = (int)(dm.heightPixels /3.5);
        screenWidth = 400;
        screenHeight = 400;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case AppConstant.WHAT.SUCCESS:
                    if (delay_time > 0) {
                    }

                    try {
                        if (delay_time == 0) {
                            captrue();
                            is_camera_delay = false;
                        }
                    } catch (Exception e) {
                        return;
                    }

                    break;

                case AppConstant.WHAT.ERROR:
                    is_camera_delay = false;
                    break;

            }
        }
    };

    public void switchCamera() {
        releaseCamera();
        mCameraId = (mCameraId + 1) % mCamera.getNumberOfCameras();
        mCamera = getCamera(mCameraId);
        if (mHolder != null) {
            startPreview(mCamera, mHolder);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mCamera == null)
                return;

            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {

        }
        return camera;
    }

    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            CameraUtil.getInstance().setCameraDisplayOrientation(this, mCameraId, camera);
            camera.startPreview();
            isview = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void captrue() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                isview = false;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap saveBitmap = CameraUtil.getInstance().setTakePicktrueOrientation(mCameraId, bitmap);

                saveBitmap = Bitmap.createScaledBitmap(saveBitmap, screenWidth, screenHeight, true);

                imgPath = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() +
                        File.separator + System.currentTimeMillis() + ".jpeg";

                BitmapUtils.saveJPGE_After(context, saveBitmap, imgPath, 100);

                if(!bitmap.isRecycled()){
                    bitmap.recycle();
                }

                if(!saveBitmap.isRecycled()){
                    saveBitmap.recycle();
                }

                onPause();

//                imgPhoto.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, picHeight));
//                imgPhoto.setImageURI(Uri.parse(img_path));

//                Intent intent = new Intent();
//                intent.putExtra(AppConstant.KEY.IMG_PATH, img_path);
//                intent.putExtra(AppConstant.KEY.PIC_WIDTH, screenWidth);
//                intent.putExtra(AppConstant.KEY.PIC_HEIGHT, picHeight);
//                setResult(AppConstant.RESULT_CODE.RESULT_OK, intent);
//                finish();
            }
        });
    }

    private void setupCamera(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            // Autofocus mode is supported 自动对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

//        Camera.Size previewSize = CameraUtil.getInstance().getPropPreviewSize(parameters.getSupportedPreviewSizes(), 1000);
//        parameters.setPreviewSize(previewSize.width, previewSize.height);
//
//        Camera.Size pictrueSize = CameraUtil.getInstance().getPropPictureSize(parameters.getSupportedPictureSizes(), 1000);
//        parameters.setPictureSize(pictrueSize.width, pictrueSize.height);

        Camera.Size previewSize = CameraUtil.getInstance().getOptimalPreviewSize(parameters.getSupportedPreviewSizes(), screenWidth, screenHeight);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        parameters.setPictureSize(previewSize.width, previewSize.height);


        camera.setParameters(parameters);

//        picHeight = screenHeight;
//        picHeight = screenWidth * previewSize.width / previewSize.height;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, screenHeight);
        params.setMargins(13,13,15,15);
        surfaceView.setLayoutParams(params);

        FrameLayout frmCameraLayout = (FrameLayout)findViewById(R.id.frmCameraLayout);
        LinearLayout.LayoutParams rlParams = new LinearLayout.LayoutParams(screenWidth+30, screenHeight+30);
        rlParams.gravity = Gravity.CENTER_HORIZONTAL;
        frmCameraLayout.setLayoutParams(rlParams);
        frmCameraLayout.setBackgroundDrawable( getResources().getDrawable(R.drawable.roundborder) );

    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mCamera == null)
                return;
        }

        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }
//////////////////////////////////////////////////////////////


}
