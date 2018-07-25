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

import com.nwp.rogueliketower.gles.GameRenderer;
import com.nwp.rogueliketower.gles.RendererData;

public class Game {
    private Activity activity;
    private GameView gameView;
    private GameData gameData;
    private RendererData rendererData;
    private GameRenderer gameRenderer;


    public Game(Activity activity) {
        this.activity = activity;
        this.gameData = new GameData();
        this.rendererData = new RendererData();
        this.gameView = new GameView(activity, gameData, rendererData);
        this.gameRenderer = new GameRenderer(this.activity, this.rendererData);

        // The system must support GLES 2.0 to run this code.
        this.gameView.setEGLContextClientVersion(2);
        this.gameView.setRenderer(this.gameRenderer);
        this.activity.setContentView(this.gameView);
    }
}
