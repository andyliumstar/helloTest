package com.android.gltest;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class MyGlFilter {
    public static final String NO_FILTER_VERTEX_SHADER =
            "uniform mat4 uMVPMatrix;   \n" +
            "attribute vec4 vPosition;" +
            "void main() {" +
                    " gl_Position = uMVPMatrix * vPosition; \n" +
            "}";

    public static final String NO_FILTER_FRAGMENT_SHADER =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";
    
    
    private final String mVertexShader;
    
    private final String mFragmentShader;
    
    protected int mGLProgId;
    protected int mGLPositionHandle;
    protected int mGLColorHandle;
    protected int mGLMVPMatrixHandle;
    
    public MyGlFilter(String vertexShader, String fragmentShader){
        mVertexShader = vertexShader;
        mFragmentShader = fragmentShader;
    }
    
    public void init(){
        mGLProgId = OpenGlUtils.loadProgram(mVertexShader, mFragmentShader);
        mGLPositionHandle =  GLES20.glGetAttribLocation(mGLProgId, "vPosition");
        mGLColorHandle = GLES20.glGetUniformLocation(mGLProgId, "vColor");
        mGLMVPMatrixHandle = GLES20.glGetUniformLocation(mGLProgId, "uMVPMatrix");
    }
    
    public void prepareDraw(){
        
    }
    
    public void onDraw(final int vertexDim,final int vertexCount, final FloatBuffer positionBuffer,
                       float[] mvpMatrix,final float[] colorBuffer){
        GLES20.glUseProgram(mGLProgId);
        prepareDraw();

        GLES20.glUniformMatrix4fv(mGLMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glVertexAttribPointer(mGLPositionHandle, vertexDim,
                GLES20.GL_FLOAT, false,
                0, positionBuffer);
        GLES20.glEnableVertexAttribArray(mGLPositionHandle);
        
        GLES20.glUniform4fv(mGLColorHandle,1, colorBuffer, 0);
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mGLPositionHandle);
    }

}
