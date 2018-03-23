/*
 * Roguelike Tower
 * Copyright (C) 2017 NWP
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.nwp.rogueliketower;

import com.nwp.rogueliketower.gles.GameRenderer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class RoguelikeTower extends Activity implements View.OnTouchListener {
    /** A reference to the game's graphical interface. */
    private GLSurfaceView gameView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GLSurfaceView(this);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
            gameView.setEGLContextClientVersion(2);
            // Set the renderer.
            gameView.setRenderer(new GameRenderer());
        }
        else {
            // Currently, ES 1 is not supported.
            System.out.println("ERROR: Your device does not support OpenGL ES 2.0.");
            return;
        }

        setContentView(gameView);
    }

    public void onRestart() {
        super.onRestart();
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume(){
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
