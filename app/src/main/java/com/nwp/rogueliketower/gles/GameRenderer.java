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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.nwp.rogueliketower.R;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements Renderer {
    private Context activityContext;
    private RendererData data;

    // This will be used to pass in the transformation matrix.
    private int mMVPMatrixHandle;
    // This will be used to pass in model position information.
    private int mPositionHandle;
    // This will be used to pass in model color information.
    private int mColorHandle;
    // This will be used to pass in model texture coordinate information.
    private int mTextureCoordinateHandle;
    // This is a handle to our texture data.
    private int mTextureDataHandle;
    // This will be used to pass in the texture.
    private int mTextureUniformHandle;
    // This is a handle to our per-vertex cube shading program.
    private int mProgramHandle;

    // How many bytes per float.
    private int bytesPerFloat;
    // Size of the position data in elements.
    private int positionDataSize;
    // Size of the color data in elements.
    private int colorDataSize;
    // Size of the texture coordinate data in elements.
    private int textureCoordinateDataSize;

    public GameRenderer(Context activity, RendererData rendererData) {
        this.activityContext = activity;
        this.data = rendererData;
        this.bytesPerFloat = data.bytesPerFloat;
        this.positionDataSize = data.positionDataSize;
        this.colorDataSize = data.colorDataSize;
        this.textureCoordinateDataSize = data.textureCoordinateDataSize;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background clear color to black.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Cull back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        // Fragments with smaller z are displayed in front.
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // Enable texture mapping
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        // Make rendering program.
        //final String vertexShader = readTextFileFromRawResource(activityContext, R.raw.per_pixel_vertex_shader)
        //final String fragmentShader = readTextFileFromRawResource(activityContext, R.raw.per_pixel_fragment_shader);
        final String vertexShader =
            "uniform mat4 u_MVPMatrix;                    \n" +
            "attribute vec4 a_Position;                   \n" +
            "attribute vec4 a_Color;                      \n" +
            "attribute vec2 a_TexCoordinate;              \n" +
            "varying vec4 v_Color;                        \n" +
            "varying vec2 v_TexCoordinate;                \n" +
            "void main() {                                \n" +
            "    v_Color = a_Color;                       \n" +
            "    v_TexCoordinate = a_TexCoordinate;       \n" +
            "    gl_Position = u_MVPMatrix * a_Position;  \n" +
            "}                                            \n";
        final String fragmentShader =
            "precision mediump float;                     \n" +
            "uniform sampler2D u_Texture;                 \n" +
            "varying vec4 v_Color;                        \n" +
            "varying vec2 v_TexCoordinate;                \n" +
            "void main() {                                \n" +
            "    gl_FragColor = v_Color *                 \n" +
                 "texture2D(u_Texture, v_TexCoordinate);  \n" +
            "}                                            \n";
        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
        final String[] attributes = {};
        mProgramHandle = makeProgram(vertexShaderHandle, fragmentShaderHandle, attributes);

        // Load the texture
        Random rand = new Random();
        if(rand.nextInt() % 2 == 0)
            mTextureDataHandle = loadTexture(activityContext, R.drawable.chris);
        else
            mTextureDataHandle = loadTexture(activityContext, R.drawable.chris);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to the same size as the screen.
        GLES20.glViewport(0, 0, width, height);

        // Scale the shapes to keep their original aspect ratio given the aspect ratio of the device screen.
        // The height of the shapes will stay the same while the width varies.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        // Clipping parameters.
        final float near = 1.0f;
        final float far = 10.0f;

        // Orthogonal view.
        Matrix.orthoM(data.mProjectionMatrix, 0, left, right, bottom, top, near, far);
        // Perspective view. Not used here.
        // Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        data.update();

        // Reset colors.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Do a complete rotation every 10 seconds (10000 miliseconds).
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        // Set our per-vertex lighting program.
        GLES20.glUseProgram(mProgramHandle);

        // Set program handles for cube drawing.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Color");
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        // Draw some cubes.
        Matrix.setIdentityM(data.mModelMatrix, 0);
        Matrix.translateM(data.mModelMatrix, 0, 4.0f, 0.0f, -7.0f);
        Matrix.rotateM(data.mModelMatrix, 0, angleInDegrees, 1.0f, 0.0f, 0.0f);
        drawTile();

        Matrix.setIdentityM(data.mModelMatrix, 0);
        Matrix.translateM(data.mModelMatrix, 0, -4.0f, 0.0f, -7.0f);
        Matrix.rotateM(data.mModelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
        drawTile();

        Matrix.setIdentityM(data.mModelMatrix, 0);
        Matrix.translateM(data.mModelMatrix, 0, 0.0f, 4.0f, -7.0f);
        Matrix.rotateM(data.mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
        drawTile();

        Matrix.setIdentityM(data.mModelMatrix, 0);
        Matrix.translateM(data.mModelMatrix, 0, 0.0f, -4.0f, -7.0f);
        drawTile();

        Matrix.setIdentityM(data.mModelMatrix, 0);
        Matrix.translateM(data.mModelMatrix, 0, 0.0f, 0.0f, -5.0f);
        Matrix.rotateM(data.mModelMatrix, 0, angleInDegrees, 1.0f, 1.0f, 0.0f);
        drawTile();
    }

    /**
     * Draw a tile.
     */
    private void drawTile() {
        // Pass in the position information
        data.mCubePositions.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, positionDataSize, GLES20.GL_FLOAT, false,
                0, data.mCubePositions);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Pass in the color information
        data.mCubeColors.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, colorDataSize, GLES20.GL_FLOAT, false,
                0, data.mCubeColors);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        // Pass in the texture coordinate information
        data.mCubeTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, textureCoordinateDataSize, GLES20.GL_FLOAT, false,
                0, data.mCubeTextureCoordinates);
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(data.mMVPMatrix, 0, data.mProjectionMatrix, 0, data.mModelMatrix, 0);

        // Pass in the combined matrix.
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, data.mMVPMatrix, 0);

        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
    }

    public static int loadTexture(final Context context, final int resourceId) {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error generating texture name.");
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;    // No pre-scaling

        // Read in the resource
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        // Bind to the texture in OpenGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Recycle the bitmap, since its data has been loaded into OpenGL.
        bitmap.recycle();

        return textureHandle[0];
    }

    /**
     * Helper function to compile a shader.
     *
     * @param shaderType The shader type.
     * @param shaderSource The shader source code.
     * @return An OpenGL handle to the shader.
     */
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

    /**
     * Helper function to compile and link a program.
     *
     * @param vertexShaderHandle An OpenGL handle to an already-compiled vertex shader.
     * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
     * @param attributes Attributes that need to be bound to the program.
     * @return An OpenGL handle to the program.
     */
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
