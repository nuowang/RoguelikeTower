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

import com.nwp.lib.gameplay.Game;
import com.nwp.lib.gles.MyGLRenderer3;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class RoguelikeTower extends Game {
    /** A reference to the game's graphical interface. */
    private GLSurfaceView gameView;

    @Override
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
            gameView.setRenderer(new MyGLRenderer3());
        }
        else {
            // Currently, ES 1 is not supported.
            System.out.println("ERROR: Your device does not support OpenGL ES 2.0.");
            return;
        }

        setContentView(gameView);
    }

    @Override
    public void onResume()
    {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
        gameView.onResume();
    }

    @Override
    public void onPause()
    {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        gameView.onPause();
    }
}
