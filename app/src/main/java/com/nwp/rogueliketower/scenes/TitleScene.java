package com.nwp.rogueliketower.scenes;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.app.Activity;

import com.nwp.rogueliketower.gles.TestRenderer;

public class TitleScene {
    /** A reference to the game's graphical interface. */
    private GLSurfaceView gameView;

    public TitleScene(Activity activity) {
        gameView = new GLSurfaceView(activity);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
            gameView.setEGLContextClientVersion(2);
            // Set the renderer.
            gameView.setRenderer(new TestRenderer(activity));
        }
        else {
            // Currently, ES 1 is not supported.
            System.out.println("ERROR: Your device does not support OpenGL ES 2.0.");
            return;
        }

        activity.setContentView(gameView);
    }

}
