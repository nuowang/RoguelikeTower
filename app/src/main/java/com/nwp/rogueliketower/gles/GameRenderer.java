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

package com.nwp.rogueliketower.gles;

import android.content.Context;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

import com.nwp.rogueliketower.R;
import com.nwp.rogueliketower.stores.ParameterStore;
import com.nwp.rogueliketower.core.Game;
import com.nwp.rogueliketower.utils.Resources;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements Renderer {
    private Context activityContext;
    private Game game;

    // Used to pass in tile coordinate data.
    private int coordinateHandle;
    // Used to pass in tile base color data.
    private int colorHandle;
    // Handle to the tile texture data.
    private int textureHandle;
    // Used to pass in tile texture data.
    private int textureUniformHandle;
    // Used to pass in tile texture coordinate data.
    private int textureCoordinateHandle;
    // The handle to the shader program.
    private int programHandle;

    public GameRenderer(Context activity, Game game) {
        this.activityContext = activity;
        this.game = game;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background clear color to black.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Cull back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        // Fragments with smaller z are displayed in front.
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // Enable texture mapping.
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        // Make the rendering program.
        final String vertexShader = Resources.loadTextFileFromRawResource(activityContext, R.raw.vertex_shader);
        final String fragmentShader = Resources.loadTextFileFromRawResource(activityContext, R.raw.fragment_shader);
        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
        final String[] attributes = {};
        programHandle = makeProgram(vertexShaderHandle, fragmentShaderHandle, attributes);
        GLES20.glUseProgram(programHandle);

        // Set program handles.
        coordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
        textureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");
        textureUniformHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture");

        // Additional settings for textures.
        final int[] textureHandles = new int[1];
        GLES20.glGenTextures(1, textureHandles, 0);
        if (textureHandles[0] == 0) {
            throw new RuntimeException("Error generating texture name.");
        }
        // Bind to the texture in OpenGL.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandles[0]);
        // Set filtering.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        textureHandle = textureHandles[0];
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(textureUniformHandle, 0);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to the same size as the screen.
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Check memory leak.
        // Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        // Debug.getMemoryInfo(memoryInfo);
        // String memMessage = String.format(
        //     "Memory: Pss=%.2f MB, Private=%.2f MB, Shared=%.2f MB",
        //     memoryInfo.getTotalPss() / 1024.0,
        //     memoryInfo.getTotalPrivateDirty() / 1024.0,
        //     memoryInfo.getTotalSharedDirty() / 1024.0);
        // System.out.println(memMessage);

        // Move the game forward by one update / frame.
        game.update();

        // Reset the screen.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Pass in the position information
        game.data.coordinates.position(0);
        GLES20.glVertexAttribPointer(coordinateHandle, ParameterStore.COORDINATE_DATA_SIZE,
            GLES20.GL_FLOAT, false, 0, game.data.coordinates);
        GLES20.glEnableVertexAttribArray(coordinateHandle);
        // Pass in the color information
        game.data.colors.position(0);
        GLES20.glVertexAttribPointer(colorHandle, ParameterStore.COLOR_DATA_SIZE,
            GLES20.GL_FLOAT, false, 0, game.data.colors);
        GLES20.glEnableVertexAttribArray(colorHandle);
        // Pass in the texture coordinate information
        game.data.textureCoordinates.position(0);
        GLES20.glVertexAttribPointer(textureCoordinateHandle, ParameterStore.TEXTURE_COORDINATE_DATA_SIZE,
            GLES20.GL_FLOAT, false, 0, game.data.textureCoordinates);
        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        // Draw all tiles.
        for (int i = 0; i < game.data.nTiles; i++) {
            // Load the bitmap into the bound texture.
            // TODO: Find a more elegant way to pass in texture sequence.
            // TODO: Find an elegant way to read sprites.
            // TODO: Fix texture color.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, game.data.textureBitmaps[game.data.textureID[i]], 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, i* ParameterStore.TILE_DATA_SIZE, ParameterStore.TILE_DATA_SIZE);
        }
    }

    private int compileShader(final int shaderType, final String shaderSource) {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderSource);
            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);
            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0) {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    private int makeProgram(final int vertexShaderHandle, final int fragmentShaderHandle,
                            final String[] attributes) {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0) {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes.
            if (attributes != null) {
                final int size = attributes.length;
                for (int i = 0; i < size; i++) {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }

}
