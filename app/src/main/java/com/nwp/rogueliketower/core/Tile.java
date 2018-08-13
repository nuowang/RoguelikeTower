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

/**
 *  Tile is the main entity in the game.
 */
public class Tile {
    // TODO: I need to define what types are needed.
    public String type = "square";
    // Center x, y, z coordinates of the tile. The center calculation depends of the tile's shape.
    public float [] center = {0, 0, 0};
    // Applicable when the shape of the tile is circle.
    public float radius = 0;
    // Applicable when the shape of the tile is square or rectangle.
    public float width = 0;
    public float height = 0;
    // Whether the tile is an active component in the current game.
    public boolean active = false;
    // Whether the tile is animated.
    public boolean animated = false;
    // The name of the texture that should go onto the tile.
    public String texture = null;
    // The frame of the animation that the tile is on.
    public int frame = 0;

    public float [] genGLESCoordinates() {
        float [] a = {0};
        return a;
    }

    public float [] genGLESTextureCoordinate() {
        float [] a = {0};
        return a;
    }

    public float [] genGLESColor() {
        float [] a = {0};
        return a;
    }

    public float [] genGLES() {
        float [] a = {0};
        return a;
    }

}
