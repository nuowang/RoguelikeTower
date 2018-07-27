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

package com.nwp.rogueliketower;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.nwp.rogueliketower.core.Game;
import com.nwp.rogueliketower.core.GameView;
import com.nwp.rogueliketower.core.GameData;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RoguelikeTower extends Activity {
    // The core engine of the game. Game itself is stateless.
    // It gives responses purely based on the state of GameData.
    private Game game;
    // In charge of storing the state / data of the game.
    private GameData gameData;
    // In charge of rendering and listening to motion events.
    private GameView gameView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure the screen.
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Initialize to title screen and preload all textures.
        this.gameData = new GameData();
        // Start the game.
        this.game = new Game(gameData);
        // Start listening to touch events.
        this.gameView = new GameView(this, this.game);
        // Start displaying the game.
        this.setContentView(this.gameView);
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

}
