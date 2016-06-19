package cn.bingoogolapple.qrcode.zxing;

import android.content.Context;
import android.util.AttributeSet;


import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ZXingView extends QRCodeView {

    public ZXingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}