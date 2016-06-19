package cn.bingoogolapple.qrcode.core;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class QRCodeView extends FrameLayout  {
//    protected Camera mCamera;
    //protected CameraPreview mPreview;
    protected SurfaceView mPreview;
    protected ScanBoxView mScanBoxView;
//    protected boolean mSpotAble = false;

    public QRCodeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QRCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化view
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        mPreview = new SurfaceView(getContext());

        mScanBoxView = new ScanBoxView(getContext());
        mScanBoxView.initCustomAttrs(context, attrs);

        addView(mPreview);
        addView(mScanBoxView);
    }

    /**
     * 显示扫描框
     */
    public void showScanRect() {
        if (mScanBoxView != null) {
            mScanBoxView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏扫描框
     */
    public void hiddenScanRect() {
        if (mScanBoxView != null) {
            mScanBoxView.setVisibility(View.GONE);
        }
    }

//    /**
//     * 打开摄像头开始预览，但是并未开始识别
//     */
//    public void startCamera() {
//        if (mCamera != null) {
//            return;
//        }
//
//        try {
//            mCamera = Camera.open();
//        } catch (Exception e) {
//
//        }
////        if (mCamera != null) {
////            //mPreview.setCamera(mCamera);
////            //mPreview.initCameraPreview();
////        }
//    }

//    /**
//     * 关闭摄像头预览，并且隐藏扫描框
//     */
//    public void stopCamera() {
//        stopSpotAndHiddenRect();
//        if (mCamera != null) {
////            mPreview.stopCameraPreview();
////            mPreview.setCamera(null);
//            mCamera.release();
//            mCamera = null;
//        }
//    }

//    /**
//     * 延迟1.5秒后开始识别
//     */
//    public void startSpot() {
//        startSpotDelay(1500);
//    }
//
//    /**
//     * 延迟delay毫秒后开始识别
//     *
//     * @param delay
//     */
//    public void startSpotDelay(int delay) {
////        mSpotAble = true;
//          startCamera();
//        mCamera.autoFocus(new Camera.AutoFocusCallback(){
//
//            @Override
//            public void onAutoFocus(boolean success, Camera camera) {
//                // TODO 自动生成的方法存根
//                if(success)
//                {
//                    mCamera.takePicture(null, null, mPictureCallback);
//                }
//            }
//        });
////        mPreview.start();
//        // 开始前先移除之前的任务
//    }
//
//    /**
//     * 停止识别
//     */
//    public void stopSpot() {
//       // mSpotAble = false;
//
//        if (mCamera != null) {
//            mCamera.setOneShotPreviewCallback(null);
//        }
//
//    }
//
//    /**
//     * 停止识别，并且隐藏扫描框
//     */
//    public void stopSpotAndHiddenRect() {
//        stopSpot();
//        hiddenScanRect();
//    }

//    /**
//     * 显示扫描框，并且延迟1.5秒后开始识别
//     */
//    public void startSpotAndShowRect() {
//        startSpot();
//        showScanRect();
//    }
//
//    /**
//     * 打开闪光灯
//     */
//    public void openFlashlight() {
//        mPreview.openFlashlight();
//    }
//
//    /**
//     * 关闭散光灯
//     */
//    public void closeFlashlight() {
//        mPreview.closeFlashlight();
//    }
//    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            // TODO 自动生成的方法存根
//            File tempFile = new File("/sdcard/temp.jpg");
//            try {
//                FileOutputStream fos = new FileOutputStream(tempFile);
//                fos.write(data);
//                fos.close();
////                Intent intent = new Intent(ResistCamera.this,ResultAty.class);
////                intent.putExtra("picPath",tempFile.getAbsolutePath());
////                startActivity(intent);
////                ResistCamera.this.finish();
//            } catch (FileNotFoundException e) {
//                // TODO 自动生成的 catch 块
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO 自动生成的 catch 块
//                e.printStackTrace();
//            }
//        }
//    };
}
