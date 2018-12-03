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
import android.os.SystemClock;

public class Game {
    public Activity activity;
    public GameData data;
    public int counter = 0;

    public Game(Activity activity, GameData gameData) {
        this.activity = activity;
        this.data = gameData;
    }

    public void update() {
        float x = data.currentEventX;
        float y = data.currentEventY;

        // Hero movement.
        if (x > -999) {
            float centerx = 0;
            float centery = 0;
            for (int i = 6*2; i < 6*3; i++) {
                centerx += data.coordinateData[3 * i];
            }
            for (int i = 6*2; i < 6*3; i++) {
                centery += data.coordinateData[3 * i + 1];
            }
            centerx = centerx / 6;
            centery = centery / 6;

            float xDist = (x - centerx) * (float) data.screenWidth;
            float yDist = (y - centery) * (float) data.screenHeight;
            float distance = (float) Math.sqrt(xDist * xDist + yDist * yDist);
            if (distance > 10) {
                // Just the first tile for now.
                for (int i = 6*2; i < 6*3; i++) {
                    data.coordinateData[3 * i + 0] += 10 * xDist / distance / ((float) data.screenWidth);
                    data.coordinateData[3 * i + 1] += 10 * yDist / distance / ((float) data.screenHeight);
                }
            }
            if (x < centerx) {
                // 0 is Carl's ID. 1 is "carl_walk_left".
                data.textureID[2] = 1;
            }
            else {
                // 0 is Carl's ID. 1 is "carl_walk_right".
                data.textureID[2] = 0;
            }
        }

        // Background movement.
        for (int i = 0; i < 6*2; i++) {
            data.coordinateData[3 * i + 0] -= 0.01f;
        }
        if (data.coordinateData[6] < -1f) {
            float second_bg_x = data.coordinateData[6+18];
            float bg_width = data.coordinateData[6] - data.coordinateData[0];
            data.coordinateData[0] = second_bg_x;
            data.coordinateData[3] = second_bg_x;
            data.coordinateData[6] = second_bg_x + bg_width;
            data.coordinateData[9] = second_bg_x;
            data.coordinateData[12] = second_bg_x + bg_width;
            data.coordinateData[15] = second_bg_x + bg_width;
        }
        if (data.coordinateData[6+18] < -1f) {
            float second_bg_x = data.coordinateData[6];
            float bg_width = data.coordinateData[6] - data.coordinateData[0];
            data.coordinateData[0+18] = second_bg_x;
            data.coordinateData[3+18] = second_bg_x;
            data.coordinateData[6+18] = second_bg_x + bg_width;
            data.coordinateData[9+18] = second_bg_x;
            data.coordinateData[12+18] = second_bg_x + bg_width;
            data.coordinateData[15+18] = second_bg_x + bg_width;
        }

        // Hero animation.
        counter += 1;
        // Do a move every ? seconds.
        if (counter % 10 == 0) {
            int offset = 12*2;
            counter = 0;
            float numberOfFrames = 8;
            if (data.textureCoordinateData[0+offset] < (numberOfFrames-1)/numberOfFrames) {
                data.textureCoordinateData[0+offset] += 1/numberOfFrames;
                data.textureCoordinateData[2+offset] += 1/numberOfFrames;
                data.textureCoordinateData[6+offset] += 1/numberOfFrames;
                data.textureCoordinateData[4+offset] += 1/numberOfFrames;
                data.textureCoordinateData[8+offset] += 1/numberOfFrames;
                data.textureCoordinateData[10+offset] += 1/numberOfFrames;
            }
            else {
                data.textureCoordinateData[0+offset] = 0.0f;
                data.textureCoordinateData[2+offset] = 0.0f;
                data.textureCoordinateData[6+offset] = 0.0f;
                data.textureCoordinateData[4+offset] = 1/numberOfFrames;
                data.textureCoordinateData[8+offset] = 1/numberOfFrames;
                data.textureCoordinateData[10+offset] = 1/numberOfFrames;
            }
        }

        data.coordinates.put(data.coordinateData).position(0);
        data.textureCoordinates.put(data.textureCoordinateData).position(0);

    }

}
