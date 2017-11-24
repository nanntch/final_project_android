package kmitl.natcha58070069.com.libreria.model;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Nacha on 24-Nov-17.
 */

public class ScreenCapture {

    public static Bitmap takeScreenShot(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    public static Bitmap takeScreenShotOfRootView(View view){
        return takeScreenShot(view.getRootView());
    }
}
