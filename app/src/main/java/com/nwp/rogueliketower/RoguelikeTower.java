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
import com.nwp.lib.gles.MyGLSurfaceView;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class RoguelikeTower extends Game {

    public GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // For android library imaging rendering
        // setContentView(R.layout.main);

        // Create a GLSurfaceView instance and set it as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

    }

//    @Override
//    public void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

}
