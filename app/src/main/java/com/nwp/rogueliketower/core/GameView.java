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

import com.nwp.rogueliketower.gles.GameRenderer;

public class GameView extends GLSurfaceView {
    public Activity activity;
    public Game game;
    public GameRenderer gameRenderer;

    public GameView(Activity activity, Game game) {
        super(activity);

        this.activity = activity;
        this.game = game;
        this.gameRenderer = new GameRenderer(this.activity, this.game);

        // The system running this code must support GLES 2.0.
        // TODO: Add GLES version checks.
        this.setEGLContextClientVersion(2);
        this.setRenderer(this.gameRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.data.currentEvent = event;
        return true;
    }

}
