package com.android.gltest;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGlSurfaceView extends GLSurfaceView {

    private MyGlRender mGlRender = new MyGlRender();
    public MyGlSurfaceView(Context context) {
        super(context);
        
        setEGLContextClientVersion(2);
        setRenderer(mGlRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
