/*
 * Roguelike Tower
 * Copyright (C) 2017 Nuo Wang
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

import android.widget.ImageView;
import android.app.ActionBar.LayoutParams;
import android.os.Environment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.os.Bundle;

public class RoguelikeTower extends Activity {

    public RoguelikeTower() {

    }

    public void test() {
        ImageView imageView = new ImageView(getApplicationContext());
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        String path = Environment.getExternalStorageDirectory() + "/your folder name/image_name.bmp";
        Bitmap image = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(image);
        //RelativeLayout rl = (RelativeLayout) findViewById(R.id.imageView);
        //rl.addView(imageView, lp);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //test();

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
