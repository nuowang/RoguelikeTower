/*
 * Roguelike Tower
 * Copyright (C) 2018 NWP
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

package com.nwp.rogueliketower.core;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.nwp.rogueliketower.R;
import com.nwp.rogueliketower.scenes.TitleScene;
import com.nwp.rogueliketower.scenes.TitleScene2;

import java.util.Random;

public class GameView extends GLSurfaceView {
    Activity currentContext;
    public GameView(Activity context) {
        super(context);

        currentContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            TitleScene titleScene = new TitleScene(currentContext);
            currentContext.setContentView(titleScene.getGameView());
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            TitleScene2 titleScene2 = new TitleScene2(currentContext);
            currentContext.setContentView(titleScene2.getGameView());
        }

        return true;
    }
}
