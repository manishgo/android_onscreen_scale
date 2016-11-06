package com.thoughtworks.onscreenscale.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
  public static int pixelToDp(int pixel, Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(pixel / (displayMetrics.density));
  }
}
