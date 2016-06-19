package cn.bingoogolapple.qrcode.zxingdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends Activity implements SurfaceHolder.Callback{
    private QRCodeView mQRCodeView;
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private RadioGroup  radioGroup;
    private RadioButton radio;
    private boolean previewing;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mPreview = (SurfaceView) findViewById(R.id.preview);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);

        radioGroup = (RadioGroup) findViewById(R.id.chooseRadio);
        radio = (RadioButton)findViewById(R.id.resistRadio);//默认为电阻识别
    }
    @Override
    protected void onResume() {
        // TODO 自动生成的方法存根
        super.onResume();
        if(mCamera ==null)
            mCamera = getCamera();
        if(mHolder!=null)
        {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            //parameters.setPreviewSize(200, 200);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            setStartPreview(mCamera, mHolder);
        }
    }
    @Override
    protected void onPause() {
        // TODO 自动生成的方法存根
        super.onPause();
        releaseCamera();
    }
    /**
     * 获取系统camera对象
     * @return
     */
    private Camera getCamera()
    {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // TODO: handle exception
            camera = null;
        }

        return camera;

    }
    /**
     * 显示camera实时预览图像
     */
    private void setStartPreview(Camera camera,SurfaceHolder holder)
    {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);//旋转竖屏
            camera.startPreview();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera()
    {
        if(mCamera!=null)
        {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }

    /**
     * 继续预览
     * @param view
     */
    public void resumPreview(View view)
    {
        mCamera.startPreview();
        if(!previewing)
        {
            mCamera.startPreview();
            TextView start = (TextView) findViewById(R.id.start_spot);
            start.setText("开始识别");
            previewing=true;
        }
        else
        {
            capture();
        }
    }
    /**
     * 暂停
     * @param view
     */
    public void pausePreview(View view)
    {
       mCamera.stopPreview();
        TextView start = (TextView) findViewById(R.id.start_spot);
        start.setText("继续识别");
        previewing = false;
    }
    /**
     * 拍摄按钮监听
     * @param view
     */
    public void capture()
    {
        radio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        //parameters.setPreviewSize(200, 200);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback(){
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                // TODO 自动生成的方法存根
                if(success)
                {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
                else
                {
                    new DialogDemo("提示","扫描失败，请移动手机或打开灯光再试一次");
                }
            }
        });
    }
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO 自动生成的方法存根
            File tempFile = new File("/sdcard/元件扫描");
            //检查文件夹存在
            while(!tempFile.exists())
            {
                tempFile.mkdir();
            }
            tempFile = new File("/sdcard/元件扫描/temp.jpg");
            try {
                //旋转
                Bitmap bitmap = BitmapFactory
                        .decodeByteArray(data, 0, data.length);
                Matrix matrix = new Matrix();
                matrix.setRotate(90);
                bitmap = Bitmap.createBitmap(bitmap,0,0,
                        bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                //bitmap转byte[]
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                //存储
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(byteArray);
                fos.close();

                //识别
                String resultTitle=null;
                String resultMessage = null;
                if(radio.getText().toString().trim().equals("电阻识别"))
                {
                    //文件路径：tempFile或bitmap
                    resultTitle="电阻识别结果";
                    resultMessage="N欧";
                }
                else
                {
                    resultTitle="芯片识别结果";
                    resultMessage="*****";
                }
                new DialogDemo(resultTitle,resultMessage);
//                //结束
//                ScanActivity.this.finish();
//                //跳转
//                Intent intent = new Intent(ScanActivity.this,ResultAty.class);
//                intent.putExtra("picPath",tempFile.getAbsolutePath());
//                startActivity(intent);
            } catch (FileNotFoundException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    };
    public class DialogDemo{
        /** Called when the activity is first created. */
//        private String title;
//        private String message;
        public DialogDemo(String title,String message)
        {
//            this.title = title;
//            this.message = message;
            new AlertDialog.Builder(ScanActivity.this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                     //添加返回按钮
                         @Override
                         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                             //开始预览
                             mCamera.startPreview();
                         }
                     })
                    .setNegativeButton("退出",new DialogInterface.OnClickListener(){

                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              System.exit(0);
                          }
                     })
                    .show();
        }

    }
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mQRCodeView.startCamera();
//        mQRCodeView.showScanRect();//显示扫描框
//    }
//
//    @Override
//    protected void onStop() {
//        mQRCodeView.stopCamera();
//        super.onStop();
//    }

//    private void vibrate() {
//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        vibrator.vibrate(200);
//    }
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.start_spot:
//                break;
//            case R.id.stop_spot:
//                mQRCodeView.stopSpot();
//                break;
//            case R.id.open_flashlight:
//                mQRCodeView.openFlashlight();
//                break;
//            case R.id.close_flashlight:
//                mQRCodeView.closeFlashlight();
//                break;
//        }
//    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO 自动生成的方法存根
        previewing = true;
        setStartPreview(mCamera, mHolder);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO 自动生成的方法存根
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO 自动生成的方法存根
        releaseCamera();
    }

    /**
     * 打开闪光灯
     */
    public void openFlashLight(View view)
    {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters( parameters );
    }
    public void closeFlashLight(View view)
    {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters( parameters );
    }
}