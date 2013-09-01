/*==============================================================================
Copyright (c) 2012-2013 QUALCOMM Austria Research Center GmbH.
All Rights Reserved.

@file
    CloudRecoRenderer.java

@brief
    Sample for CloudReco

==============================================================================*/

package com.qualcomm.QCARSamples.CloudRecognition;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

import com.qualcomm.QCAR.QCAR;

/** The renderer class for the CloudReco sample. */
public class CloudRecoRenderer implements GLSurfaceView.Renderer
{
    public boolean mIsActive = false;

    /** Reference to main activity **/
    public CloudReco mActivity;

    /** Native function for initializing the renderer. */
    public native void initRendering();

    /** Native function to update the renderer. */
    public native void updateRendering(int width, int height);


    /** Called when the surface is created or recreated. */
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        // Call native function to initialize rendering:
        initRendering();

        // Call QCAR function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        QCAR.onSurfaceCreated();
    }


    /** Called when the surface changed size. */
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        // Call native function to update rendering when render surface
        // parameters
        // have changed:
        updateRendering(width, height);

        // Call QCAR function to handle render surface size changes:
        QCAR.onSurfaceChanged(width, height);
    }


    /** The native render function. */
    public native void renderFrame();


    /** Called to draw the current frame. */
    public void onDrawFrame(GL10 gl)
    {
        if (!mIsActive)
        {
            return;
        }

        // Update render view (projection matrix and viewport) if needed:
        mActivity.updateRenderView();

        // Call our native function to render content
        renderFrame();
    }
}
