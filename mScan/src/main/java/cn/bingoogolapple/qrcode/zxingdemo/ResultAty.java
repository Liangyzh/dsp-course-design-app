package cn.bingoogolapple.qrcode.zxingdemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

public class ResultAty extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		String path = getIntent().getStringExtra("picPath");
		ImageView imageView = (ImageView)findViewById(R.id.pic);
		try {
			FileInputStream fis = new FileInputStream(path);
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			imageView.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 返回上级扫描界面
	 */
	public void goBack()
	{
		ResultAty.this.finish();
		Intent intent = new Intent(ResultAty.this,ScanActivity.class);
		startActivity(intent);
	}
	/**
	 * 物理按键监听
	 * @param keyCode
	 * @param event
     * @return
     */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			//返回键
			goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
