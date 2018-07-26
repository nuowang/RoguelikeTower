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

import com.nwp.rogueliketower.gles.RendererData;

public class GameView extends GLSurfaceView {
    private Activity activity;
    private MotionEvent currentEvent;
    private GameData gameData;
    private RendererData rendererData;

    public GameView(Activity activity, GameData gameData, RendererData rendererData) {
        super(activity);

        this.activity = activity;
        this.gameData = gameData;
        this.rendererData = rendererData;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentEvent = event;
        rendererData.currentEvent = currentEvent;
        return true;
    }

    public MotionEvent getEvent() {
        return currentEvent;
    }
}
