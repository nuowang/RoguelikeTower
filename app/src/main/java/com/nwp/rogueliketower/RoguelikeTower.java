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

import com.nwp.rogueliketower.scenes.TitleScene;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class RoguelikeTower extends Activity implements View.OnTouchListener {
    String currentScene = "";

    public RoguelikeTower() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                         View.SYSTEM_UI_FLAG_FULLSCREEN |
                                                         View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        TitleScene titleScene = new TitleScene(this);

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
