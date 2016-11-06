package com.thoughtworks.onscreenscale.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.onscreenscale.R;
import com.thoughtworks.onscreenscale.util.DisplayUtil;

public class ScaleService extends Service {

  private WindowManager windowManager;
  private LinearLayout scaleRoot;
  private TextView txtLineSize;
  private ImageView line;
  private LinearLayout lineFrame;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }


  @Override
  public void onCreate() {
    super.onCreate();
    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    scaleRoot = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_scale, null);
    txtLineSize = (TextView) scaleRoot.findViewById(R.id.line_size);
    lineFrame = (LinearLayout) scaleRoot.findViewById(R.id.line_frame);
    line = (ImageView) scaleRoot.findViewById(R.id.line);

    line.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        Integer dp = DisplayUtil.pixelToDp(view.getWidth(), ScaleService.this);
        txtLineSize.setText(getResources().getString(R.string.size_in_dp, dp));
      }
    });
    LayoutParams layoutParams = createLayoutParams();

    windowManager.addView(scaleRoot, layoutParams);

    final ViewGroup.LayoutParams lineFrameParams = lineFrame.getLayoutParams();
    ImageView horizontalLineDragger = (ImageView) scaleRoot.findViewById(R.id.horizontal_line_dragger);
    horizontalLineDragger.setOnTouchListener(new View.OnTouchListener() {
      int initialWidth;
      float initialMotionEventX;

      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
          case MotionEvent.ACTION_DOWN:
            initialWidth = lineFrameParams.width;
            initialMotionEventX = motionEvent.getRawX();
            return true;
          case MotionEvent.ACTION_MOVE:
            lineFrameParams.width = Math.max(20, initialWidth + (int) (motionEvent.getRawX() - initialMotionEventX));
            lineFrame.requestLayout();
            return true;
          case MotionEvent.ACTION_UP:
            initialMotionEventX = 0.0f;
            initialWidth = lineFrameParams.width;
            return false;
        }
        return false;
      }
    });
  }

  @Override
  public void onDestroy() {
    windowManager.removeView(scaleRoot);
    super.onDestroy();
  }

  @NonNull
  private LayoutParams createLayoutParams() {
    LayoutParams layoutParams = new LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        LayoutParams.TYPE_PHONE,
        LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT);

    layoutParams.gravity = Gravity.TOP | Gravity.START;
    layoutParams.x = 100;
    layoutParams.y = 100;
    return layoutParams;
  }
}
