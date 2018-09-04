package com.mhd.datastructure;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public static int convertPixelToDp(int pixel, Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return (int)(pixel/displayMetrics.density);
    }
    public static int convertDpToPixel(int dp,Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return (int)(dp*displayMetrics.density);
    }
}
