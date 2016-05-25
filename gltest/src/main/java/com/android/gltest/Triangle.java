package com.android.gltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // in counterclockwise order:
             0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
             0.5f, -0.311004243f, 0.0f
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.0f, 0.76953125f, 0.666665625f, 1.0f};

    public Triangle() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
        
        
//        // initialize vertex byte buffer for shape coordinates
//        ByteBuffer bbr = ByteBuffer.allocateDirect(
//                // (number of coordinate values * 4 bytes per float)
//                color.length * 4);
//        // use the device hardware's native byte order
//        bbr.order(ByteOrder.nativeOrder());
//        // create a floating point buffer from the ByteBuffer
//        colorBuffer = bbr.asFloatBuffer();
//        // add the coordinates to the FloatBuffer
//        colorBuffer.put(color);
//        
//        colorBuffer.position(0);
    }
    
    public FloatBuffer getVertexPosition(){
        return vertexBuffer;
    }
    
    public float[] getColor(){
        return color;
    }
    
    public int getVertexDim(){
        return COORDS_PER_VERTEX;
    }
    
    public int getVertexCount(){
        return triangleCoords.length/COORDS_PER_VERTEX;
    }
}
