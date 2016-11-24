package com.example.architecture.bad.myfigurecollection.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by spawn on 24/11/16.
 */

public class CodeUtils {

    /**
     * Gets the size of the screen, in pixels.
     *
     * @param context context
     * @return size of the screen in pixels
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
