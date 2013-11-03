/*==============================================================================
Copyright (c) 2012-2013 QUALCOMM Austria Research Center GmbH.
All Rights Reserved.

@file
    CloudReco.java

@brief
    CloudReco Activity - Augmentation view - for CloudReco Sample Application

==============================================================================*/

package com.qualcomm.QCARSamples.CloudRecognition;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qualcomm.QCAR.QCAR;
import com.qualcomm.QCARSamples.CloudRecognition.model.Book;
import com.qualcomm.QCARSamples.CloudRecognition.utils.DebugLog;
import com.qualcomm.QCARSamples.CloudRecognition.view.BookOverlayView;
import com.giandroid.lumi.model.*;



/** The main activity for the CloudReco sample. */
@SuppressLint("NewApi")
public class CloudReco extends Activity

{
	
    // Defines the Server URL to get the s data
    private static final String mServerURL = "http://www.soravi.com/__tmp/lumi/pintura/";
    
   
    public void inicio(View view)
    
	{
        
        Intent i = new Intent(this, MenuLumi.class);
        startActivity(i);
	}
    String server="http://www.soravi.com/__tmp/lumi";
    public void guardar(View view)
	{
    	
		
		// Set the Icon for the Dialog
	
			
    	File root = android.os.Environment.getExternalStorageDirectory(); 
    	
    	//String FILENAME = dir.getAbsolutePath()+"/pinacoteca";
		String string = server+"/pintura/"+mPictureJSONUrl;
		
		
		String eol = System.getProperty("line.separator");

		FileOutputStream fos;
		try {
			
			File file =new File(root.getAbsolutePath()+"/pinacoteca.txt");
			 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(root.getAbsolutePath()+"/"+file.getName(),true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);    	            	        	
    	        Scanner scanner=null;
    	        try {
    	        	scanner = new Scanner(file);
    	        } catch(FileNotFoundException e) { 
    	            //handle this
    	        }
    	        //now read the file line by line...
    	        boolean flagContol = false;
    	        while (scanner.hasNextLine()) {
    	            String line = scanner.nextLine();
    	            
    	            if(line.startsWith(string)) { 
    	            	flagContol=true;
    	            }
    	        }
    	    if(!flagContol){    
			String tmp=string+eol;
			bufferWritter.write(tmp);
			 bufferWritter.flush();
			bufferWritter.close();	  
		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Pinacoteca");
	        builder.setMessage("Se ha agregado su pintura correctamente");
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();  
	        }else{
	        
				bufferWritter.close();	
				ImageButton boton=(ImageButton)findViewById(R.id.imageButton3);
				boton.setBackground(this.getResources().getDrawable(R.drawable.disabled));
				boton.setEnabled(false);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Pinacoteca");
		        builder.setMessage("Ya existe la pintura seleccionada en su Pinacoteca");
		        builder.setPositiveButton("OK",null);
		        builder.create();
		        builder.show();  
	        }
	        
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Pinacoteca");
	        builder.setMessage("Ha ocurrido un error al almacenar");
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();  
			
		} catch (IOException e) {			
			e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Pinacoteca");
	        builder.setMessage("Ha ocurrido un error al almacenar");
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();  
		}catch (Exception e) {
			e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Pinacoteca");
	        builder.setMessage("Ha ocurrido un error al almacenar");
	        builder.setPositiveButton("OK",null);
	        builder.create();
	        builder.show();  
			
		}
		
	}
    public void infoPintura(View view){
    	Control.pintura=mPictureData;
    	  Intent i = new Intent(this, Detalle.class);
          startActivity(i);
    	
    }
    public void pinturasRelacionadas(View view)
    
	{
    	Control.pintura=mPictureData;
    	 Intent i = new Intent(this, PinturasRelacionadas.class);
         startActivity(i);
	}
    public void artistasRelacionados(View view)
	{
    	Control.pintura=mPictureData;
   	 Intent i = new Intent(this, AuRelacionados.class);
        startActivity(i);
	}

    // Different screen orientations supported by the CloudReco system.
    public static final int SCREEN_ORIENTATION_LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public static final int SCREEN_ORIENTATION_PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static final int SCREEN_ORIENTATION_AUTOROTATE = ActivityInfo.SCREEN_ORIENTATION_SENSOR;

    // Change this value to switch between different screen orientations
    public static int screenOrientation = SCREEN_ORIENTATION_AUTOROTATE;

    // Application status constants:
    private static final int APPSTATUS_UNINITED = -1;
    private static final int APPSTATUS_INIT_APP = 0;
    private static final int APPSTATUS_INIT_QCAR = 1;
    private static final int APPSTATUS_INIT_TRACKER = 2;
    private static final int APPSTATUS_INIT_APP_AR = 3;
    private static final int APPSTATUS_INIT_CLOUDRECO = 4;
    private static final int APPSTATUS_INITED = 5;
    private static final int APPSTATUS_CAMERA_STOPPED = 6;
    private static final int APPSTATUS_CAMERA_RUNNING = 7;

    // Focus modes:
    private static final int FOCUS_MODE_NORMAL = 0;
    private static final int FOCUS_MODE_CONTINUOUS_AUTO = 1;

    // Name of the native dynamic libraries to load:
    private static final String NATIVE_LIB_SAMPLE = "CloudReco";
    private static final String NATIVE_LIB_QCAR = "QCAR";

    // Stores the current status of the target ( if is being displayed or not )
    private static final int BOOKINFO_NOT_DISPLAYED = 0;
    private static final int BOOKINFO_IS_DISPLAYED = 1;
    
    // Stores the current status of the target ( if is being displayed or not )
    private static final int PICTUREINFO_NOT_DISPLAYED = 0;
    private static final int PICTUREINFO_IS_DISPLAYED = 1;

    // These codes match the ones defined in TargetFinder.h
    static final int INIT_SUCCESS = 2;
    static final int INIT_ERROR_NO_NETWORK_CONNECTION = -1;
    static final int INIT_ERROR_SERVICE_NOT_AVAILABLE = -2;
    static final int UPDATE_ERROR_AUTHORIZATION_FAILED = -1;
    static final int UPDATE_ERROR_PROJECT_SUSPENDED = -2;
    static final int UPDATE_ERROR_NO_NETWORK_CONNECTION = -3;
    static final int UPDATE_ERROR_SERVICE_NOT_AVAILABLE = -4;
    static final int UPDATE_ERROR_BAD_FRAME_QUALITY = -5;
    static final int UPDATE_ERROR_UPDATE_SDK = -6;
    static final int UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE = -7;
    static final int UPDATE_ERROR_REQUEST_TIMEOUT = -8;

    // Handles Codes to display/Hide views
    static final int HIDE_STATUS_BAR = 0;
    static final int SHOW_STATUS_BAR = 1;

    static final int HIDE_2D_OVERLAY = 0;
    static final int SHOW_2D_OVERLAY = 1;

    static final int HIDE_LOADING_DIALOG = 0;
    static final int SHOW_LOADING_DIALOG = 1;

    // Augmented content status
    //private int mBookInfoStatus = BOOKINFO_NOT_DISPLAYED;
    private int mPictureInfoStatus = PICTUREINFO_NOT_DISPLAYED;

    // Status Bar Text
    private String mStatusBarText;

    // Active Book Data
    private Book mBookData;
    private String mBookJSONUrl;
    private View mLoadingDialogContainer;
    private Texture mBookDataTexture;
    
    // Active Picture Data
    private Picture mPictureData;
    private String mPictureJSONUrl;
    //private View mLoadingDialogContainer;
    private Texture mPictureDataTexture;

	// Indicates if the app is currently loading the book data
    private boolean mIsLoadingBookData = false;
    
    // Indicates if the app is currently loading the picture data
    private boolean mIsLoadingPictureData = false;

    // AsyncTask to get  data from a json object
    //private GetBookDataTask mGetBookDataTask;
    private GetPictureDataTask mGetPictureDataTask;

    // Our OpenGL view:
    private QCARSampleGLView mGlView;

    // Our renderer:
    private CloudRecoRenderer mRenderer;

    // Display size of the device
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    // Constant representing invalid screen orientation to trigger a query:
    private static final int INVALID_SCREEN_ROTATION = -1;

    // Last detected screen rotation:
    private int mLastScreenRotation = INVALID_SCREEN_ROTATION;

    // The current application status
    private int mAppStatus = APPSTATUS_UNINITED;

    // The async tasks to initialize the QCAR SDK
    private InitQCARTask mInitQCARTask;
    private InitCloudRecoTask mInitCloudRecoTask;

    // An object used for synchronizing QCAR initialization, dataset loading and
    // the Android onDestroy() life cycle event. If the application is destroyed
    // while a data set is still being loaded, then we wait for the loading
    // operation to finish before shutting down QCAR.
    private Object mShutdownLock = new Object();

    // QCAR initialization flags
    private int mQCARFlags = 0;

    // View overlays to be displayed in the Augmented View
    private RelativeLayout mUILayout;
    private TextView mStatusBar;
    private Button mCloseButton;

    // Error message handling:
    private int mlastErrorCode = 0;

    // Alert Dialog used to display SDK errors
    private AlertDialog mErrorDialog;

    // Contextual Menu Options for Camera Flash - Autofocus
    private boolean mFlash = false;
    private boolean mContAutofocus = false;

    // Detects the double tap gesture for launching the Camera menu
    private GestureDetector mGestureDetector;

    // size of the Texture to be generated with the Picture data
    private static int mTextureSize = 768;
    
    /** Static initializer block to load native libraries on start-up. */
    static
    {
        loadLibrary(NATIVE_LIB_QCAR);
        loadLibrary(NATIVE_LIB_SAMPLE);
    }

    /** Activates the Flash */
    private native boolean activateFlash(boolean flash);

    /** Applies auto focus if supported by the current device */
    private native boolean autofocus();

    /** Setups the focus mode */
    private native boolean setFocusMode(int mode);

    /** Checks if the CloudReco is already Started */
    private native boolean isCloudRecoStarted();

    /** Native tracker initialization and deinitialization. */
    public native int initTracker();

    public native void deinitTracker();

    /** Native functions to init and deinit cloud-based recognition. */
    public native int initCloudReco();

    public native void deinitCloudReco();

    /** Native function to enter CloudReco scanning mode */
    public native void enterScanningModeNative();

    /** Native function to stop CloudReco scanning mode */
    public native void enterContentModeNative();

    /** Native methods for starting and stopping the camera. */
    private native void startCamera();

    private native void stopCamera();

    /**
     * Native method for setting / updating the projection matrix for AR content
     * rendering
     */
    private native void setProjectionMatrix();

    /** Native function to de-initialize the application. */
    private native void deinitApplicationNative();

    /** Tells native code whether we are in portrait or landscape mode */
    private native void setActivityPortraitMode(boolean isPortrait);

    /** Native function to initialize the application. */
    private native void initApplicationNative(int width, int height);

    /**
     * Native function to generate the OpenGL Texture Object in the renderFrame
     * thread
     */
    public native void productTextureIsCreated();

    /** Sets current device Scale factor based on screen dpi */
    public native void setDeviceDPIScaleFactor(float dpiScaleIndicator);

    /** Cleans the lastTargetTrackerId variable in Native Code */
    public native void cleanTargetTrackedId();


    /**
     * Crates a Handler to Show/Hide the status bar overlay from an UI Thread
     */
    static class StatusBarHandler extends Handler
    {
        private final WeakReference<CloudReco> mCloudReco;

        StatusBarHandler(CloudReco cloudReco)
        {
            mCloudReco = new WeakReference<CloudReco>(cloudReco);
        }


        public void handleMessage(Message msg)
        {
            CloudReco cloudReco = mCloudReco.get();
            if (cloudReco == null)
            {
                return;
            }

            if (msg.what == SHOW_STATUS_BAR)
            {
                cloudReco.mStatusBar.setText(cloudReco.mStatusBarText);
                cloudReco.mStatusBar.setVisibility(View.VISIBLE);
            }
            else
            {
                cloudReco.mStatusBar.setVisibility(View.GONE);
            }
        }
    }

    private Handler statusBarHandler = new StatusBarHandler(this);


    /**
     * Creates a handler to Show/Hide the UI Overlay from an UI thread
     */
    static class Overlay2dHandler extends Handler
    {
        private final WeakReference<CloudReco> mCloudReco;

        Overlay2dHandler(CloudReco cloudReco)
        {
            mCloudReco = new WeakReference<CloudReco>(cloudReco);
        }


        public void handleMessage(Message msg)
        {
            CloudReco cloudReco = mCloudReco.get();
            if (cloudReco == null)
            {
                return;
            }

            if (cloudReco.mCloseButton != null)
            {
                if (msg.what == SHOW_2D_OVERLAY)
                {
                    cloudReco.mCloseButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    cloudReco.mCloseButton.setVisibility(View.GONE);
                }
            }
        }
    }

    private Handler overlay2DHandler = new Overlay2dHandler(this);


    /**
     * Creates a handler to update the status of the Loading Dialog from an UI
     * thread
     */
    static class LoadingDialogHandler extends Handler
    {
        private final WeakReference<CloudReco> mCloudReco;

        LoadingDialogHandler(CloudReco cloudReco)
        {
            mCloudReco = new WeakReference<CloudReco>(cloudReco);
        }


        public void handleMessage(Message msg)
        {
            CloudReco cloudReco = mCloudReco.get();
            if (cloudReco == null)
            {
                return;
            }

            if (msg.what == SHOW_LOADING_DIALOG)
            {
                cloudReco.mLoadingDialogContainer.setVisibility(View.VISIBLE);

            }
            else if (msg.what == HIDE_LOADING_DIALOG)
            {
                cloudReco.mLoadingDialogContainer.setVisibility(View.GONE);
            }
        }
    }

    private Handler loadingDialogHandler = new LoadingDialogHandler(this);


    /** An async task to initialize QCAR asynchronously. */
    private class InitQCARTask extends AsyncTask<Void, Integer, Boolean>
    {
        // Initialize with invalid value
        private int mProgressValue = -1;

        protected Boolean doInBackground(Void... params)
        {
            // Prevent the onDestroy() method to overlap with initialization:
            synchronized (mShutdownLock)
            {
                QCAR.setInitParameters(CloudReco.this, mQCARFlags);

                do
                {
                    // QCAR.init() blocks until an initialization step is
                    // complete,
                    // then it proceeds to the next step and reports progress in
                    // percents (0 ... 100%)
                    // If QCAR.init() returns -1, it indicates an error.
                    // Initialization is done when progress has reached 100%.
                    mProgressValue = QCAR.init();

                    // Publish the progress value:
                    publishProgress(mProgressValue);

                    // We check whether the task has been canceled in the
                    // meantime
                    // (by calling AsyncTask.cancel(true))
                    // and bail out if it has, thus stopping this thread.
                    // This is necessary as the AsyncTask will run to completion
                    // regardless of the status of the component that started
                    // is.
                } while (!isCancelled() && mProgressValue >= 0
                        && mProgressValue < 100);

                return (mProgressValue > 0);
            }
        }


      
        protected void onProgressUpdate(Integer... values)
        {
            // Do something with the progress value "values[0]", e.g. update
            // splash screen, progress bar, etc.
        }


        protected void onPostExecute(Boolean result)
        {
            // Done initializing QCAR, proceed to next application
            // initialization status:
            if (result)
            {
                DebugLog.LOGD("InitQCARTask::onPostExecute: QCAR initialization"
                        + " successful");

                updateApplicationStatus(APPSTATUS_INIT_TRACKER);
            }
            else
            {
                // Create dialog box for display error:
                AlertDialog dialogError = new AlertDialog.Builder(
                        CloudReco.this).create();

                dialogError.setButton(DialogInterface.BUTTON_POSITIVE,
                        getString(R.string.button_OK),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                    int which)
                            {
                                // Exiting application
                                System.exit(1);
                            }
                        });

                String logMessage;

                // NOTE: Check if initialization failed because the device is
                // not supported. At this point the user should be informed
                // with a message.
                if (mProgressValue == QCAR.INIT_DEVICE_NOT_SUPPORTED)
                {
                    logMessage = "Failed to initialize QCAR because this "
                            + "device is not supported.";
                }
                else
                {
                    logMessage = "Failed to initialize QCAR.";
                }

                // Log error:
                DebugLog.LOGE("InitQCARTask::onPostExecute: " + logMessage
                        + " Exiting.");

            	onDestroy();
                
                // Show dialog box with error message:
               // dialogError.setMessage(logMessage);
                //dialogError.show();
            }
        }
    }


    /** An async task to initialize cloud-based recognition asynchronously. */
    private class InitCloudRecoTask extends AsyncTask<Void, Integer, Boolean>
    {
        // Initialize with invalid value
        private int mInitResult = -1;

        protected Boolean doInBackground(Void... params)
        {
            // Prevent the onDestroy() method to overlap:
            synchronized (mShutdownLock)
            {
                // Init cloud-based recognition:
                mInitResult = initCloudReco();
                return mInitResult == INIT_SUCCESS;
            }
        }


        protected void onPostExecute(Boolean result)
        {
            DebugLog.LOGD("InitCloudRecoTask::onPostExecute: execution "
                    + (result ? "successful" : "failed"));

            if (result)
            {
                // Done loading the tracker, update application status:
                updateApplicationStatus(APPSTATUS_INITED);

                // Hides the Loading Dialog
                loadingDialogHandler.sendEmptyMessage(HIDE_LOADING_DIALOG);

                mUILayout.setBackgroundColor(Color.TRANSPARENT);
            }
            else
            {
                // Create dialog box for display error:
                AlertDialog dialogError = new AlertDialog.Builder(
                        CloudReco.this).create();
                dialogError.setButton(DialogInterface.BUTTON_POSITIVE,
                        getString(R.string.button_OK),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                    int which)
                            {
                                // Exiting application
                                System.exit(1);
                            }
                        });

                // Show dialog box with error message:
                String logMessage = "Failed to initialize CloudReco.";

                // NOTE: Check if initialization failed because the device is
                // not supported. At this point the user should be informed
                // with a message.
                if (mInitResult == INIT_ERROR_NO_NETWORK_CONNECTION)
                    logMessage = "Failed to initialize CloudReco because "
                            + "the device has no network connection.";
                else if (mInitResult == INIT_ERROR_SERVICE_NOT_AVAILABLE)
                    logMessage = "Failed to initialize CloudReco because "
                            + "the service is not available.";

                dialogError.setMessage(logMessage);
                dialogError.show();
            }
        }
    }


    /** Stores screen dimensions */
    private void storeScreenDimensions()
    {
        // Query display dimensions
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }


    /**
     * Called when the activity first starts or needs to be recreated after
     * resuming the application or a configuration change.
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        DebugLog.LOGD("CloudReco::onCreate");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
        super.onCreate(savedInstanceState);

        // Query the QCAR initialization flags:
        mQCARFlags = getInitializationFlags();

        // Update the application status to start initializing application
        updateApplicationStatus(APPSTATUS_INIT_APP);

        // Creates the GestureDetector listener for processing double tap
        mGestureDetector = new GestureDetector(this, new GestureListener());

        // Gets the current device screen density
        float dpiScaleIndicator = getApplicationContext().getResources()
                .getDisplayMetrics().density;

        // Sets the device scale density to the native code
        setDeviceDPIScaleFactor(dpiScaleIndicator);
    }


    /** Configure QCAR with the desired version of OpenGL ES. */
    private int getInitializationFlags()
    {
        return QCAR.GL_20;
    }


    /** Called when the activity will start interacting with the user. */
    protected void onResume()
    {
        DebugLog.LOGD("CloudReco::onResume");
        super.onResume();

        // QCAR-specific resume operation
        QCAR.onResume();

        // We may start the camera only if the QCAR SDK has already been
        // initialized
        if (mAppStatus == APPSTATUS_CAMERA_STOPPED)
        {
            updateApplicationStatus(APPSTATUS_CAMERA_RUNNING);

            // Reactivate flash if it was active before pausing the app
            if (mFlash)
            {
                boolean result = activateFlash(mFlash);
                DebugLog.LOGI("Turning flash " + (mFlash ? "ON" : "OFF") + " "
                        + (result ? "WORKED" : "FAILED") + "!!");
            }
        }

        // Resume the GL view:
        if (mGlView != null)
        {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }

        mPictureInfoStatus = PICTUREINFO_NOT_DISPLAYED;

        // By default the 2D Overlay is hidden
        hide2DOverlay();
    }


    /** Callback for configuration changes the activity handles itself */
    public void onConfigurationChanged(Configuration config)
    {
        DebugLog.LOGD("CloudReco::onConfigurationChanged");
        super.onConfigurationChanged(config);

        // updates screen orientation
        updateActivityOrientation();

        storeScreenDimensions();

        // Invalidate screen rotation to trigger query upon next render call:
        mLastScreenRotation = INVALID_SCREEN_ROTATION;
    }


    /** Called when the system is about to start resuming a previous activity. */
    protected void onPause()
    {
        DebugLog.LOGD("CloudReco::onPause");
        DebugLog.LOGE("ESTOY EN EL ONPAUSE");
        super.onPause();

        
    }


    /** The final call you receive before your activity is destroyed. */
    protected void onDestroy()
    {
        DebugLog.LOGD("CloudReco::onDestroy");
        super.onDestroy();

        // Cancel potentially running tasks
        if (mInitQCARTask != null
                && mInitQCARTask.getStatus() != InitQCARTask.Status.FINISHED)
        {
            mInitQCARTask.cancel(true);
            mInitQCARTask = null;
        }

        if (mInitCloudRecoTask != null
                && mInitCloudRecoTask.getStatus() != InitCloudRecoTask.Status.FINISHED)
        {
            mInitCloudRecoTask.cancel(true);
            mInitCloudRecoTask = null;
        }

        // Ensure that all asynchronous operations to initialize QCAR and
        // loading
        // the tracker datasets do not overlap:
        synchronized (mShutdownLock)
        {

            // Do application deinitialization in native code
            deinitApplicationNative();

            // Destroy the tracking data set:
            deinitCloudReco();

            // Deinit the tracker:
            deinitTracker();

            // Deinitialize QCAR SDK
            QCAR.deinit();
        }

        System.gc();
    }


    /**
     * NOTE: this method is synchronized because of a potential concurrent
     * access by VisualSearch::onResume() and InitQCARTask::onPostExecute().
     */
    private synchronized void updateApplicationStatus(int appStatus)
    {
        // Exit if there is no change in status
        if (mAppStatus == appStatus)
            return;

        // Store new status value
        mAppStatus = appStatus;

        // Execute application state-specific actions
        switch (mAppStatus)
        {
        case APPSTATUS_INIT_APP:
            // Initialize application elements that do not rely on QCAR
            // initialization
            initApplication();

            // Proceed to next application initialization status
            updateApplicationStatus(APPSTATUS_INIT_QCAR);
            break;

        case APPSTATUS_INIT_QCAR:
            // Initialize QCAR SDK asynchronously to avoid blocking the
            // main (UI) thread.
            // This task instance must be created and invoked on the UI
            // thread and it can be executed only once!
            try
            {
                mInitQCARTask = new InitQCARTask();
                mInitQCARTask.execute();
            }
            catch (Exception e)
            {
                DebugLog.LOGE("Initializing QCAR SDK failed");
            }
            break;

        case APPSTATUS_INIT_TRACKER:
            // Initialize the ImageTracker
            if (initTracker() > 0)
            {
                // Proceed to next application initialization status
                updateApplicationStatus(APPSTATUS_INIT_APP_AR);

            }
            break;

        case APPSTATUS_INIT_APP_AR:
            // Initialize Augmented Reality-specific application elements
            // that may rely on the fact that the QCAR SDK has been
            // already initialized
            initApplicationAR();

            // Proceed to next application initialization status
            updateApplicationStatus(APPSTATUS_INIT_CLOUDRECO);
            break;

        case APPSTATUS_INIT_CLOUDRECO:
            // Initialize visual search
            //
            // This task instance must be created and invoked on the UI
            // thread and it can be executed only once!
            try
            {
                mInitCloudRecoTask = new InitCloudRecoTask();
                mInitCloudRecoTask.execute();
            }
            catch (Exception e)
            {
                DebugLog.LOGE("Failed to initialize CloudReco");
            }
            break;

        case APPSTATUS_INITED:
            // Hint to the virtual machine that it would be a good time to
            // run the garbage collector.
            //
            // NOTE: This is only a hint. There is no guarantee that the
            // garbage collector will actually be run.
            System.gc();

            // Activate the renderer
            mRenderer.mIsActive = true;

            // Now add the GL surface view. It is important
            // that the OpenGL ES surface view gets added
            // BEFORE the camera is started and video
            // background is configured.
            addContentView(mGlView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            // Start the camera:
            updateApplicationStatus(APPSTATUS_CAMERA_RUNNING);
            mUILayout.bringToFront();

            break;

        case APPSTATUS_CAMERA_STOPPED:
            // Call the native function to stop the camera
            stopCamera();
            break;

        case APPSTATUS_CAMERA_RUNNING:
            // Call the native function to start the camera
            startCamera();

            // Set continuous auto-focus if supported by the device,
            // otherwise default back to regular auto-focus mode.
            // This will be activated by a tap to the screen in this
            // application.
            if (!setFocusMode(FOCUS_MODE_CONTINUOUS_AUTO))
            {
                setFocusMode(FOCUS_MODE_NORMAL);
                mContAutofocus = false;
            }
            else
            {
                mContAutofocus = true;
            }
            break;

        default:
            throw new RuntimeException("Invalid application state");
        }
    }


    /** Initialize application GUI elements that are not related to AR. */
    private void initApplication()
    {
        // Set the screen orientation from activity setting:
        int screenOrientation = CloudReco.screenOrientation;

        // This is necessary for enabling AutoRotation in the Augmented View
        if (screenOrientation == CloudReco.SCREEN_ORIENTATION_AUTOROTATE)
        {
            // NOTE: We use reflection here to see if the current platform
            // supports the full sensor mode (available only on Gingerbread
            // and above.
            try
            {
                // SCREEN_ORIENTATION_FULL_SENSOR is required to allow all 
                // 4 screen rotations if API level >= 9:
                Field fullSensorField = ActivityInfo.class
                        .getField("SCREEN_ORIENTATION_FULL_SENSOR");
                screenOrientation = fullSensorField.getInt(null);
            }
            catch (NoSuchFieldException e)
            {
                // App is running on API level < 9, do nothing.
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // Apply screen orientation
        setRequestedOrientation(screenOrientation);

        updateActivityOrientation();

        storeScreenDimensions();

        // As long as this window is visible to the user, keep the device's
        // screen turned on and bright.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    private void updateActivityOrientation()
    {
        Configuration config = getResources().getConfiguration();

        boolean isPortrait = false;

        switch (config.orientation)
        {
        case Configuration.ORIENTATION_PORTRAIT:
            isPortrait = true;
            break;
        case Configuration.ORIENTATION_LANDSCAPE:
            isPortrait = false;
            break;
        case Configuration.ORIENTATION_UNDEFINED:
        default:
            break;
        }

        DebugLog.LOGI("Activity is in "
                + (isPortrait ? "PORTRAIT" : "LANDSCAPE"));
        setActivityPortraitMode(isPortrait);
    }


    /**
     * Updates projection matrix and viewport after a screen rotation
     * change was detected.
     */
    public void updateRenderView()
    {
        int currentScreenRotation =
            getWindowManager().getDefaultDisplay().getRotation();

        if (currentScreenRotation != mLastScreenRotation)
        {
            // Set projection matrix if there is already a valid one:
            if (QCAR.isInitialized() &&
                (mAppStatus == APPSTATUS_CAMERA_RUNNING))
            {
                DebugLog.LOGD("CloudReco::updateRenderView");

                // Query display dimensions:
                storeScreenDimensions();

                // Update viewport via renderer:
                mRenderer.updateRendering(mScreenWidth, mScreenHeight);

                // Update projection matrix:
                setProjectionMatrix();

                // Cache last rotation used for setting projection matrix:
                mLastScreenRotation = currentScreenRotation;
            }
        }
    }


    /** Initializes AR application components. */
    private void initApplicationAR()
    {
        // Do application initialization in native code (e.g. registering
        // callbacks, etc.)
        initApplicationNative(mScreenWidth, mScreenHeight);

        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = QCAR.requiresAlpha();

        // Initialize the GLView with proper flags
        mGlView = new QCARSampleGLView(this);
        mGlView.init(mQCARFlags, translucent, depthSize, stencilSize);

        // Setups the Renderer of the GLView
        mRenderer = new CloudRecoRenderer();
        mRenderer.mActivity = this;
        mGlView.setRenderer(mRenderer);

        // Inflates the Overlay Layout to be displayed above the Camera View
        LayoutInflater inflater = LayoutInflater.from(this);
        mUILayout = (RelativeLayout) inflater.inflate(R.layout.camera_overlay,
                null, false);

        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);

        addContentView(mUILayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        // Gets a Reference to the Bottom Status Bar
        mStatusBar = (TextView) mUILayout.findViewById(R.id.overlay_status);

        // By default
        mLoadingDialogContainer = mUILayout.findViewById(R.id.loading_layout);
        mLoadingDialogContainer.setVisibility(View.VISIBLE);

        // Gets a reference to the Close Button
        mCloseButton = (Button) mUILayout
                .findViewById(R.id.overlay_close_button);

        // Sets the Close Button functionality
        mCloseButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                // Updates application status
                mPictureInfoStatus = PICTUREINFO_NOT_DISPLAYED;

                loadingDialogHandler.sendEmptyMessage(HIDE_LOADING_DIALOG);

                // Checks if the app is currently loading a picture data
                if (mIsLoadingPictureData)
                {

                    // Cancels the AsyncTask
                    mGetPictureDataTask.cancel(true);
                    mIsLoadingPictureData = false;
                    
                    // Cleans the Target Tracker Id
                    cleanTargetTrackedId();
                }

                // Enters Scanning Mode
                enterScanningMode();
            }
        });

        // As default the 2D overlay and Status bar are hidden when application
        // starts
        hide2DOverlay();
        hideStatusBar();
    }


    /** Sets the Status Bar Text in a UI thread */
    public void setStatusBarText(String statusText)
    {
        mStatusBarText = statusText;
        statusBarHandler.sendEmptyMessage(SHOW_STATUS_BAR);
    }


    /** Hides the Status bar 2D Overlay in a UI thread */
    public void hideStatusBar()
    {
        if (mStatusBar.getVisibility() == View.VISIBLE)
        {
            statusBarHandler.sendEmptyMessage(HIDE_STATUS_BAR);
        }
    }


    /** Shows the Status Bar 2D Overlay in a UI thread */
    public void showStatusBar()
    {
        if (mStatusBar.getVisibility() == View.GONE)
        {
            statusBarHandler.sendEmptyMessage(SHOW_STATUS_BAR);
        }
    }


    /** Starts the WebView with the Picture Extra Data */
    public void startWebView(int value)
    {
        // Checks that we have a valid picture data
//        if ( != null)
//        {
            // Starts an Intent to open the picture URL
//            Intent viewIntent = new Intent("android.intent.action.VIEW",
//                    Uri.parse("http://192.168.0.101:8080/lumi/imagenes/"+mPictureData.getPicUrl()));//ADAPTAR
//            System.out.println("\n------- TIENE EL DATO DEL LIBRO "+mPictureData.getPicUrl());
//            startActivity(viewIntent);
        //}
    }

    public Drawable getImagen( String name){
		URL url;

		try {
			url = new URL(server+"/imagenes/"+name);
			InputStream is = (InputStream) url.getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  

    public void startInfoActivity()
    {
    	DebugLog.LOGI("ANTES DE INTENT");
    	setContentView(R.layout.activity_info);
    	TextView txtCambiado = (TextView)findViewById(R.id.textView1);
    	DebugLog.LOGE(mPictureData.getTitle());
    	txtCambiado.setText(mPictureData.getTitle());
    	ImageButton imagen=(ImageButton)findViewById(R.id.imageButton6);
  /// 	 DebugLog.LOGD("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+mPictureData.getPicUrl());
    	Drawable pintura=getImagen(mPictureData.getPicUrl());
    	mPictureData.setImage(pintura);
    	imagen.setBackground(pintura);
    
    	
    	
    	
    	//Intent i = new Intent(CloudReco.this, InfoActivity.class);
    	DebugLog.LOGI("DESPUES DE INTENT");
    	//startActivity(i);
    	DebugLog.LOGI("DESPUES DE START INFO ACT");
    }
    /** Returns the error message for each error code */
    private String getStatusDescString(int code)
    {
        if (code == UPDATE_ERROR_AUTHORIZATION_FAILED)
            return getString(R.string.UPDATE_ERROR_AUTHORIZATION_FAILED_DESC);
        if (code == UPDATE_ERROR_PROJECT_SUSPENDED)
            return getString(R.string.UPDATE_ERROR_PROJECT_SUSPENDED_DESC);
        if (code == UPDATE_ERROR_NO_NETWORK_CONNECTION)
            return getString(R.string.UPDATE_ERROR_NO_NETWORK_CONNECTION_DESC);
        if (code == UPDATE_ERROR_SERVICE_NOT_AVAILABLE)
            return getString(R.string.UPDATE_ERROR_SERVICE_NOT_AVAILABLE_DESC);
        if (code == UPDATE_ERROR_UPDATE_SDK)
            return getString(R.string.UPDATE_ERROR_UPDATE_SDK_DESC);
        if (code == UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE)
            return getString(R.string.UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE_DESC);
        if (code == UPDATE_ERROR_REQUEST_TIMEOUT)
            return getString(R.string.UPDATE_ERROR_REQUEST_TIMEOUT_DESC);
        if (code == UPDATE_ERROR_BAD_FRAME_QUALITY)
            return getString(R.string.UPDATE_ERROR_BAD_FRAME_QUALITY_DESC);
        else
        {
            return getString(R.string.UPDATE_ERROR_UNKNOWN_DESC);
        }
    }


    /** Returns the error message for each error code */
    private String getStatusTitleString(int code)
    {
        if (code == UPDATE_ERROR_AUTHORIZATION_FAILED)
            return getString(R.string.UPDATE_ERROR_AUTHORIZATION_FAILED_TITLE);
        if (code == UPDATE_ERROR_PROJECT_SUSPENDED)
            return getString(R.string.UPDATE_ERROR_PROJECT_SUSPENDED_TITLE);
        if (code == UPDATE_ERROR_NO_NETWORK_CONNECTION)
            return getString(R.string.UPDATE_ERROR_NO_NETWORK_CONNECTION_TITLE);
        if (code == UPDATE_ERROR_SERVICE_NOT_AVAILABLE)
            return getString(R.string.UPDATE_ERROR_SERVICE_NOT_AVAILABLE_TITLE);
        if (code == UPDATE_ERROR_UPDATE_SDK)
            return getString(R.string.UPDATE_ERROR_UPDATE_SDK_TITLE);
        if (code == UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE)
            return getString(R.string.UPDATE_ERROR_TIMESTAMP_OUT_OF_RANGE_TITLE);
        if (code == UPDATE_ERROR_REQUEST_TIMEOUT)
            return getString(R.string.UPDATE_ERROR_REQUEST_TIMEOUT_TITLE);
        if (code == UPDATE_ERROR_BAD_FRAME_QUALITY)
            return getString(R.string.UPDATE_ERROR_BAD_FRAME_QUALITY_TITLE);
        else
        {
            return getString(R.string.UPDATE_ERROR_UNKNOWN_TITLE);
        }
    }


    /** Shows error messages as System dialogs */
    public void showErrorMessage(int errorCode)
    {
        mlastErrorCode = errorCode;

        runOnUiThread(new Runnable()
        {
            public void run()
            {
                if (mErrorDialog != null)
                {
                    mErrorDialog.dismiss();
                }

                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        CloudReco.this);
                builder.setMessage(
                        getStatusDescString(CloudReco.this.mlastErrorCode))
                        .setTitle(
                                getStatusTitleString(CloudReco.this.mlastErrorCode))
                        .setCancelable(false)
                        .setIcon(0)
                        .setPositiveButton(getString(R.string.button_OK),
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,
                                            int id)
                                    {
                                        dialog.dismiss();
                                    }
                                });

                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }


    /**
     * Generates a texture for the picture data fecthing the picture info from
     * the specified picture URL
     */
    public void createProductTexture(String pictureJSONUrl)
    {
        // gets book url from parameters
        mPictureJSONUrl = pictureJSONUrl.trim();
        DebugLog.LOGD("CREA TEXTURA PRODUCTO");
        // Cleans old texture reference if necessary
        if (mPictureDataTexture != null)
        {
            mPictureDataTexture = null;

            System.gc();
        }

        // Searches for the book data in an AsyncTask
        mGetPictureDataTask = new GetPictureDataTask();
        mGetPictureDataTask.execute();
    }


    /** Gets the picture data from a JSON Object */
    private class GetPictureDataTask extends AsyncTask<Void, Void, Void>
    {
        private String mPictureDataJSONFullUrl;
        private static final String CHARSET = "UTF-8";


        protected void onPreExecute()
        {
            mIsLoadingPictureData = true;

            // Initialize the current picture full url to search
            // for the data
            StringBuilder sBuilder = new StringBuilder();
            DebugLog.LOGD("mServerUR: "+mServerURL);
            DebugLog.LOGD("mPictureJSONUrl: "+mPictureJSONUrl);
            
            sBuilder.append(mServerURL);
            sBuilder.append(mPictureJSONUrl);

            mPictureDataJSONFullUrl = sBuilder.toString();

            // Shows the loading dialog
            loadingDialogHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        }


        protected Void doInBackground(Void... params)
        {
            HttpURLConnection connection = null;

            try
            {
                // Connects to the Server to get the picture data
                URL url = new URL(mPictureDataJSONFullUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Charset", CHARSET);
                connection.connect();
                DebugLog.LOGD("CONETADO PARA OBTENER DATA");
                int status = connection.getResponseCode();

                // Checks that the picture JSON url exists and connection
                // has been successful
                if (status != HttpURLConnection.HTTP_OK)
                {
                    // Cleans picture data variables
                    mPictureData = null;
                    mPictureInfoStatus = PICTUREINFO_NOT_DISPLAYED;

                    // Hides loading dialog
                    loadingDialogHandler.sendEmptyMessage(HIDE_LOADING_DIALOG);

                    // Cleans current tracker Id and returns to scanning mode
                    cleanTargetTrackedId();

                    enterScanningMode();
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                
                String line;
                while ((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }

                // Cleans any old reference to mPictureData
                if (mPictureData != null)
                {
                    mPictureData = null;

                }
                DebugLog.LOGD("A POR EL JSON");
                JSONObject jsonObject = new JSONObject(builder.toString());

                // Generates a new Picture Object with the JSON object data
                mPictureData = new Picture();//ADAPTAR

                mPictureData.setTitle(jsonObject.getString("title"));
                mPictureData.setDescription(jsonObject.getString("description"));
                mPictureData.setPicUrl(jsonObject.getString("picUrl"));
                mPictureData.setRelations(jsonObject.getString("relations"));
                mPictureData.setTechnique(jsonObject.getString("technique"));
                mPictureData.setYear(jsonObject.getString("year"));
                mPictureData.setAuthor(jsonObject.getString("author"));

                DebugLog.LOGD("TITULO: "+mPictureData.getTitle()+" TECNICA: "+mPictureData.getTechnique());
                // Gets the picture thumb image
                byte[] thumb = downloadImage(jsonObject.getString("picUrl"));

                if (thumb != null)
                {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(thumb, 0,
                            thumb.length);
                    mPictureData.setPicture(bitmap);
                }
                
                DebugLog.LOGE("TERMINO EL RUN IN BACKGROUND");
                //startInfoActivity();
            }
            catch (Exception e)
            {
                DebugLog.LOGD("Couldn't get pictures. e: " + e);
            }
            finally
            {
                connection.disconnect();
            }

            return null;
        }


        protected void onProgressUpdate(Void... values)
        {

        }


        protected void onPostExecute(Void result)
        {
        	DebugLog.LOGE("INTENTO LO QUE CONTIENE ONPOSTEXECUTE");
            if (mPictureData != null)
            {
                // Generates a View to display the picture data
                InfoActivity informationactivity = new InfoActivity();

                DebugLog.LOGE("INTENTO LANZAR INFO");
                // Updates the view used as a 3d Texture
                startInfoActivity();

                // Sets the layout params
                
//                productView.setLayoutParams(new LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT,
//                        RelativeLayout.LayoutParams.WRAP_CONTENT));

                // Sets View measure - This size should be the same as the
                // texture generated to display the overlay in order for the
                // texture to be centered in screen
                
//                productView.measure(MeasureSpec.makeMeasureSpec(mTextureSize,
//                        MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
//                        mTextureSize, MeasureSpec.EXACTLY));

                // updates layout size
                
//                productView.layout(0, 0, productView.getMeasuredWidth(),
//                        productView.getMeasuredHeight());

                // Draws the View into a Bitmap. Note we are allocating several
                // large memory buffers thus attempt to clear them as soon as
                // they are no longer required:
//                Bitmap bitmap = Bitmap.createBitmap(mTextureSize, mTextureSize,
//                        Bitmap.Config.ARGB_8888);
//                
//                Canvas c = new Canvas(bitmap);
////                informationactivity.draw(c);
//
//                // Clear the product view as it is no longer needed
////                productView = null;
//                System.gc();
//                
//                // Allocate int buffer for pixel conversion and copy pixels
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                
//                int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
//                bitmap.getPixels(data, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
//                        bitmap.getHeight());
//                
//                // Recycle the bitmap object as it is no longer needed
//                bitmap.recycle();
//                bitmap = null;
//                c = null;
//                System.gc();   
//                
//                // Generates the Texture from the int buffer
//                mBookDataTexture = Texture.loadTextureFromIntBuffer(data,
//                                        width, height);
//
//                // Clear the int buffer as it is no longer needed
//                data = null;
//                System.gc(); 
//                                
//                // Hides the loading dialog from a UI thread
//                loadingDialogHandler.sendEmptyMessage(HIDE_LOADING_DIALOG);
//
//                mIsLoadingBookData = false;
//
//                productTextureIsCreated();
            }
        }
    }


    /**
     * Downloads and image from an Url specified as a paremeter returns the
     * array of bytes with the image Data for storing it on the Local Database
     */
    private byte[] downloadImage(final String imageUrl)
    {
        ByteArrayBuffer baf = null;

        try
        {
            URL url = new URL(imageUrl);
            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 128);
            baf = new ByteArrayBuffer(128);

            // get the bytes one by one
            int current = 0;
            while ((current = bis.read()) != -1)
            {
                baf.append((byte) current);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (baf == null)
        {
            return null;
        }
        else
        {
            return baf.toByteArray();
        }
    }


    /** Returns the current Book Data Texture */
    private Texture getProductTexture()
    {
        return mBookDataTexture;
    }


    /** Updates a BookOverlayView with the Book data specified in parameters */
    private void updateProductView(InfoActivity infoactivity, Picture picture)
    {    	
    	infoactivity.setPictureAuthor(picture.getAuthor());
    	infoactivity.setPictureTitle(picture.getTitle());
    	infoactivity.setPictureYear(picture.getYear());
    	//startInfoActivity();
//        productView.setPictureTitle(picture.getTitle());
//        productView.setBookPrice(book.getPriceList());
//        productView.setYourPrice(book.getPriceYour());
//        productView.setBookRatingCount(book.getRatingTotal());
//        productView.setRating(book.getRatingAvg());
//        productView.setBookAuthor(book.getAuthor());
//        productView.setCoverViewFromBitmap(book.getThumb());
    }


    /**
     * Starts application content Mode Displays UI OVerlays and turns CloudReco
     * off
     */
    public void enterContentMode()
    {
        // Updates state variables
        mPictureInfoStatus = PICTUREINFO_IS_DISPLAYED;

        // Shows the 2D Overlay
        show2DOverlay();

        // Enters content mode to disable CloudReco in Native
        enterContentModeNative();
    }


    /** Hides the 2D Overlay view and starts CloudReco service again */
    private void enterScanningMode()
    {
        // Hides the 2D Overlay
        hide2DOverlay();

        // Enables CloudReco Scanning Mode in Native code
        enterScanningModeNative();
    }


    /** Displays the 2D Book Overlay */
    public void show2DOverlay()
    {
        // Sends the Message to the Handler in the UI thread
        overlay2DHandler.sendEmptyMessage(SHOW_2D_OVERLAY);
    }


    /** Hides the 2D Book Overlay */
    public void hide2DOverlay()
    {
        // Sends the Message to the Handler in the UI thread
        overlay2DHandler.sendEmptyMessage(HIDE_2D_OVERLAY);
    }


    /** A helper for loading native libraries stored in "libs/armeabi*". */
    public static boolean loadLibrary(String nLibName)
    {
        try
        {
            System.loadLibrary(nLibName);

            DebugLog.LOGI("Native library lib" + nLibName + ".so loaded");
            return true;

        }
        catch (UnsatisfiedLinkError ulee)
        {

            DebugLog.LOGE("The library lib" + nLibName
                    + ".so could not be loaded");
        }
        catch (SecurityException se)
        {

            DebugLog.LOGE("The library lib" + nLibName
                    + ".so was not allowed to be loaded");
        }

        return false;
    }


    /** Shows the Camera Options Dialog when the Menu Key is pressed */
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            showCameraOptionsDialog();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }


    public boolean onTouchEvent(MotionEvent event)
    {
        // Process the Gestures
        return mGestureDetector.onTouchEvent(event);
    }


    /** Process Double Tap event for showing the Camera options menu */
    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener
    {
        public boolean onDown(MotionEvent e)
        {
            return true;
        }


        public boolean onSingleTapUp(MotionEvent e)
        {

            // If the book info is not displayed it performs an Autofocus
            if (mPictureInfoStatus == BOOKINFO_NOT_DISPLAYED)
            {
                // Calls the Autofocus Native Method
                autofocus();

                // Triggering manual auto focus disables continuous
                // autofocus
                mContAutofocus = false;

                // If the book info is displayed it shows the book data web view
            }
            else if (mPictureInfoStatus == BOOKINFO_IS_DISPLAYED)
            {

                float x = e.getX(0);
                float y = e.getY(0);

                // Creates a Bounding box for detecting touches
                float screenLeft = mScreenWidth / 8.0f;
                float screenRight = mScreenWidth * 0.8f;
                float screenUp = mScreenHeight / 7.0f;
                float screenDown = mScreenHeight * 0.7f;

                // Checks touch inside the bounding box
                if (x < screenRight && x > screenLeft && y < screenDown
                        && y > screenUp)
                {
                    // Starts the webView
                    startInfoActivity(); //LANZAR NUEVO ACTIVITY
                }
            }

            return true;
        }


        // Event when double tap occurs
        public boolean onDoubleTap(MotionEvent e)
        {
            // Shows the Camera options
            showCameraOptionsDialog();
            return true;
        }
    }


    /**
     * Shows an AlertDialog with the camera options available
     */
    private void showCameraOptionsDialog()
    {
        // Only show camera options dialog box if app has been already inited
        if (mAppStatus < APPSTATUS_INITED)
        {
            return;
        }

        final int itemCameraIndex = 0;
        final int itemAutofocusIndex = 1;

        AlertDialog cameraOptionsDialog = null;

        CharSequence[] items =
        { getString(R.string.menu_flash_on),
                getString(R.string.menu_contAutofocus_off) };

        // Updates list titles according to current state of the options
        if (mFlash)
        {
            items[itemCameraIndex] = (getString(R.string.menu_flash_off));
        }
        else
        {
            items[itemCameraIndex] = (getString(R.string.menu_flash_on));
        }

        if (mContAutofocus)
        {
            items[itemAutofocusIndex] = (getString(R.string.menu_contAutofocus_off));
        }
        else
        {
            items[itemAutofocusIndex] = (getString(R.string.menu_contAutofocus_on));
        }

        // Builds the Alert Dialog
        AlertDialog.Builder cameraOptionsDialogBuilder = new AlertDialog.Builder(
                CloudReco.this);
        cameraOptionsDialogBuilder
                .setTitle(getString(R.string.menu_camera_title));
        cameraOptionsDialogBuilder.setItems(items,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        if (item == itemCameraIndex)
                        {
                            // Turns focus mode on/off by calling native
                            // method
                            if (activateFlash(!mFlash))
                            {
                                mFlash = !mFlash;
                            }
                            else
                            {
                                Toast.makeText
                                (
                                    CloudReco.this,
                                    "Unable to turn " + 
                                    (mFlash ? "off" : "on") + " flash",
                                    Toast.LENGTH_SHORT
                                ).show();
                            }

                            // Dismisses the dialog
                            dialog.dismiss();
                        }
                        else if (item == itemAutofocusIndex)
                        {
                            if (mContAutofocus)
                            {
                                // Sets the Focus Mode by calling the native
                                // method
                                if (setFocusMode(FOCUS_MODE_NORMAL))
                                {
                                    mContAutofocus = false;
                                }
                                else
                                {
                                    Toast.makeText
                                    (
                                            CloudReco.this,
                                        "Unable to deactivate Continuous Auto-Focus",
                                        Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                            else
                            {
                                // Sets the focus mode by calling the native
                                // method
                                if (setFocusMode(FOCUS_MODE_CONTINUOUS_AUTO))
                                {
                                    mContAutofocus = true;
                                }
                                else
                                {
                                    Toast.makeText
                                    (
                                            CloudReco.this,
                                        "Unable to activate Continuous Auto-Focus",
                                        Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }

                            // Dismisses the dialog
                            dialog.dismiss();
                        }
                    }
                });

        // Shows the dialog
        cameraOptionsDialog = cameraOptionsDialogBuilder.create();
        cameraOptionsDialog.show();
    }
}
