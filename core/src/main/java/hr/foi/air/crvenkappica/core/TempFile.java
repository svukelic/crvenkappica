package hr.foi.air.crvenkappica.core;

import android.content.Context;

import java.io.File;

import hr.foi.air.crvenkappica.core.OnImageReturn;

/**
 * Created by Mario on 28.01.2016..
 */
public class TempFile {
    public static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), "temp_image");
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }
}
