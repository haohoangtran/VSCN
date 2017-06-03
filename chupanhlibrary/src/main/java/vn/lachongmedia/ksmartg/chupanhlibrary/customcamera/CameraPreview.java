package vn.lachongmedia.ksmartg.chupanhlibrary.customcamera;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import vn.lachongmedia.ksmartg.chupanhlibrary.activities.ChupAnhActivity;


/**
 * This class assumes the parent layout is RelativeLayout.LayoutParams.
 */
@SuppressWarnings("unused")
public class CameraPreview extends SurfaceView implements
        SurfaceHolder.Callback {
    private static boolean DEBUGGING = true;
    private static final String LOG_TAG = "CameraPreviewSample";
    private static final String CAMERA_PARAM_ORIENTATION = "orientation";
    private static final String CAMERA_PARAM_LANDSCAPE = "landscape";
    private static final String CAMERA_PARAM_PORTRAIT = "portrait";
    protected Activity mActivity;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    protected List<Size> mPreviewSizeList;
    protected List<Size> mPictureSizeList;
    public Size mPreviewSize;
    protected Size mPictureSize;
    private int mSurfaceChangedCallDepth = 0;
    private int mCameraId;
    private LayoutMode mLayoutMode;
    private int mCenterPosX = -1;
    private int mCenterPosY;
    public static int State = 0; // bien luu ho tro muc chup 600.800
    List<Size> mSupportedPreviewSizes;
    PreviewReadyCallback mPreviewReadyCallback = null;


    public static enum LayoutMode {
        FitToParent, // Scale to the size that no side is larger than the parent
        NoBlank // Scale to the size that no side is smaller than the parent
    }


    public interface PreviewReadyCallback {
        public void onPreviewReady();
    }

    /**
     * State flag: true when surface's layout size is set and surfaceChanged()
     * process has not been completed.
     */
    protected boolean mSurfaceConfiguring = false;

    public CameraPreview(Activity activity, int cameraId, LayoutMode mode) {
        super(activity); // Always necessary
        mActivity = activity;
        mLayoutMode = mode;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (Camera.getNumberOfCameras() > cameraId) {
                mCameraId = cameraId;
            } else {
                mCameraId = 0;
            }
        } else {
            mCameraId = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            setmCamera(Camera.open(mCameraId));
        } else {
            setmCamera(Camera.open());
        }
        Camera.Parameters cameraParams = getmCamera().getParameters();
        mPreviewSizeList = cameraParams.getSupportedPreviewSizes();
        mPictureSizeList = cameraParams.getSupportedPictureSizes();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            getmCamera().setPreviewDisplay(mHolder);
        } catch (IOException e) {
            try {
                getmCamera().release();
                setmCamera(null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mSurfaceChangedCallDepth++;
        doSurfaceChanged(width, height);
        mSurfaceChangedCallDepth--;
    }

    private void doSurfaceChanged(int width, int height) {
        try {
            getmCamera().stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Camera.Parameters cameraParams = getmCamera().getParameters();
        boolean portrait = isPortrait();

        // The code in this if-statement is prevented from executed again when
        // surfaceChanged is
        // called again due to the change of the layout size in this
        // if-statement.
        if (!mSurfaceConfiguring) {
            Size previewSize = determinePreviewSize(portrait, width,
                    height);
            Size pictureSize = determinePictureSize(previewSize);
            if (DEBUGGING) {
                Log.v(LOG_TAG, "Desired Preview Size - w: " + width + ", h: "
                        + height);
            }
            mPreviewSize = previewSize;
            mPictureSize = pictureSize;
            mSurfaceConfiguring = adjustSurfaceLayoutSize(previewSize,
                    portrait, width, height);
            // Continue executing this method if this method is called
            // recursively.
            // Recursive call of surfaceChanged is very special case, which is a
            // path from
            // the catch clause at the end of this method.
            // The later part of this method should be executed as well in the
            // recursive
            // invocation of this method, because the layout change made in this
            // recursive
            // call will not trigger another invocation of this method.
            if (mSurfaceConfiguring && (mSurfaceChangedCallDepth <= 1)) {
                return;
            }
        }

        configureCameraParameters(cameraParams, portrait);
        mSurfaceConfiguring = false;

        try {
            getmCamera().startPreview();
        } catch (Exception e) {
            Log.w(LOG_TAG, "Failed to start preview: " + e.getMessage());

            // Remove failed size
            mPreviewSizeList.remove(mPreviewSize);
            mPreviewSize = null;

            // Reconfigure
            if (mPreviewSizeList.size() > 0) { // prevent infinite loop
                surfaceChanged(null, 0, width, height);
            } else {
                Toast.makeText(mActivity, "Can't start preview",
                        Toast.LENGTH_LONG).show();
                Log.w(LOG_TAG, "Gave up starting preview");
            }
        }

        if (null != mPreviewReadyCallback) {
            mPreviewReadyCallback.onPreviewReady();
        }
    }


    protected Size determinePreviewSize(boolean portrait, int reqWidth,
                                        int reqHeight) {
        // Meaning of width and height is switched for preview when portrait,
        // while it is the same as user's view for surface and metrics.
        // That is, width must always be larger than height for setPreviewSize.
        int reqPreviewWidth; // requested width in terms of camera hardware
        int reqPreviewHeight; // requested height in terms of camera hardware
        if (portrait) {
            reqPreviewWidth = reqHeight;
            reqPreviewHeight = reqWidth;
        } else {
            reqPreviewWidth = reqWidth;
            reqPreviewHeight = reqHeight;
        }

        if (DEBUGGING) {
            Log.v(LOG_TAG, "Listing all supported preview sizes");
            for (Size size : mPreviewSizeList) {
                Log.v(LOG_TAG, "  w: " + size.width + ", h: " + size.height);
            }
            Log.v(LOG_TAG, "Listing all supported picture sizes");
            for (Size size : mPictureSizeList) {
                Log.v(LOG_TAG, "  w: " + size.width + ", h: " + size.height);
            }
        }

        // Adjust surface size with the closest aspect-ratio
        float reqRatio = ((float) reqPreviewWidth) / reqPreviewHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Size retSize = null;
        for (Size size : mPreviewSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }

    protected Size determinePictureSize(Size previewSize) {
        Size retSize = null;
        for (Size size : mPictureSizeList) {
            if (size.equals(previewSize)) {
                return size;
            }
        }

        if (DEBUGGING) {
            Log.v(LOG_TAG, "Same picture size not found.");
        }

        // if the preview size is not supported as a picture size
        float reqRatio = ((float) previewSize.width) / previewSize.height;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        for (Size size : mPictureSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }

    protected boolean adjustSurfaceLayoutSize(Size previewSize,
                                              boolean portrait, int availableWidth, int availableHeight) {

        float tmpLayoutHeight, tmpLayoutWidth;
        float ratio43 = 0;
        if (portrait) {
            ratio43 = (float) 3 / 4;
            tmpLayoutHeight = previewSize.width;
            tmpLayoutWidth = previewSize.width * ratio43;

        } else {
            ratio43 = (float) 4 / 3;
            tmpLayoutHeight = previewSize.height;
            tmpLayoutWidth = previewSize.height * ratio43;
        }

        float factH, factW, fact;
        factH = availableHeight / tmpLayoutHeight;
        factW = availableWidth / tmpLayoutWidth;
        if (mLayoutMode == LayoutMode.FitToParent) {
            // Select smaller factor, because the surface cannot be set to the
            // size larger than display metrics.
            if (factH < factW) {
                fact = factH;
            } else {
                fact = factW;
            }
        } else {
            if (factH < factW) {
                fact = factW;
            } else {
                fact = factH;
            }
        }

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
                .getLayoutParams();

        int layoutHeight = (int) (tmpLayoutHeight * fact);
        int layoutWidth = (int) (tmpLayoutWidth * fact);
        if (DEBUGGING) {
            Log.v(LOG_TAG, "Preview Layout Size - w: " + layoutWidth + ", h: "
                    + layoutHeight);
            Log.v(LOG_TAG, "Scale factor: " + fact);
        }

        boolean layoutChanged;
        if ((layoutWidth != this.getWidth())
                || (layoutHeight != this.getHeight())) {
            layoutParams.height = layoutHeight;
            layoutParams.width = layoutWidth;
            if (mCenterPosX >= 0) {
                layoutParams.topMargin = mCenterPosY - (layoutHeight / 2);
                layoutParams.leftMargin = mCenterPosX - (layoutWidth / 2);
            }
            this.setLayoutParams(layoutParams); // this will trigger another
            // surfaceChanged invocation.
            layoutChanged = true;
        } else {
            layoutChanged = false;
        }

        return layoutChanged;
    }

    /**
     * @param x X coordinate of center position on the screen. Set to negative
     *          value to unset.
     * @param y Y coordinate of center position on the screen.
     */
    public void setCenterPosition(int x, int y) {
        mCenterPosX = x;
        mCenterPosY = y;
    }

    protected void configureCameraParameters(Camera.Parameters cameraParams,
                                             boolean portrait) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) { // for 2.1 and
            // before
            if (portrait) {
                cameraParams.set(CAMERA_PARAM_ORIENTATION,
                        CAMERA_PARAM_PORTRAIT);
            } else {
                cameraParams.set(CAMERA_PARAM_ORIENTATION,
                        CAMERA_PARAM_LANDSCAPE);
            }
        } else { // for 2.2 and later
            int angle;
            Display display = mActivity.getWindowManager().getDefaultDisplay();
            switch (display.getRotation()) {
                case Surface.ROTATION_0: // This is display orientation
                    angle = 90; // This is camera orientation
                    break;
                case Surface.ROTATION_90:
                    angle = 0;
                    break;
                case Surface.ROTATION_180:
                    angle = 270;
                    break;
                case Surface.ROTATION_270:
                    angle = 180;
                    break;
                default:
                    angle = 90;
                    break;
            }
            getmCamera().setDisplayOrientation(angle);
        }
        cameraParams.setFocusMode(getFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE));
        try {
            cameraParams.setPictureSize(1600, 1200);
            cameraParams.setPreviewSize(640, 480);

            getmCamera().setParameters(cameraParams);
        } catch (Exception e) {
            State = 1;
            cameraParams.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            cameraParams.setPictureSize(mPictureSize.width, mPictureSize.height);
            getmCamera().setParameters(cameraParams);
        }

        if (DEBUGGING) {
            Log.v(LOG_TAG, "Preview Actual Size - w: " + mPreviewSize.width
                    + ", h: " + mPreviewSize.height);
            Log.v(LOG_TAG, "Picture Actual Size - w: " + mPictureSize.width
                    + ", h: " + mPictureSize.height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    public void stop() {
        if (null == getmCamera()) {
            return;
        }
//        try {
//            getmCamera().unlock();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            mHolder.removeCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getmCamera().setPreviewDisplay(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getmCamera().stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            setPreviewCallback(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getmCamera().release();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            setmCamera(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPortrait() {
        return (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public void setOneShotPreviewCallback(PreviewCallback callback) {
        if (null == getmCamera()) {
            return;
        }
        getmCamera().setOneShotPreviewCallback(callback);
    }

    public void setPreviewCallback(PreviewCallback callback) {
        if (null == getmCamera()) {
            return;
        }
        getmCamera().setPreviewCallback(callback);
    }

    public Size getPreviewSize() {
        return mPreviewSize;
    }

    public Size getPictureSize() {
        return mPictureSize;
    }

    public void setOnPreviewReady(PreviewReadyCallback cb) {
        mPreviewReadyCallback = cb;
    }

    public void setCamera(int soLanThuKhoiTaoLai) {
        if (soLanThuKhoiTaoLai <= 0) {
            return;
        }
        try {
            if (getmCamera() != null) {
                mSupportedPreviewSizes = getmCamera().getParameters()
                        .getSupportedPreviewSizes();
                requestLayout();
                Camera.Parameters params = getmCamera().getParameters();
                try {
                    // get Camera parameters


//                    List<String> focusModes = params.getSupportedFocusModes();
//                    if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//                        // set the focus mode
//                        params.getFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//                    }

                    //				if(params.getSupportedFlashModes().contains(Camera.Parameters.FLASH_MODE_AUTO)){
                    //					params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    //				}else{
                    //					params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    //				}

                    if (ChupAnhActivity.stateFlash.equals("Bật")) {
                        params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    } else if (ChupAnhActivity.stateFlash.equals("Tắt")) {
                        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    } else {
                        params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    }

                } catch (Exception ex) {
                }
                // set Camera parameters
                params.setFocusMode(getFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE));
                getmCamera().setParameters(params);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.gc();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            setCamera(soLanThuKhoiTaoLai - 1);
        }
    }

    public Camera getmCamera() {
        return mCamera;
    }

    protected void setmCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }

    public void setFocusModeForParameter(Camera.Parameters parameters, String focusMode) {
        final List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)
                && focusMode == Camera.Parameters.FOCUS_MODE_AUTO
                && parameters.getFocusMode() != focusMode) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED)
                && focusMode == Camera.Parameters.FOCUS_MODE_FIXED
                && parameters.getFocusMode() != focusMode) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)
                && focusMode == Camera.Parameters.FOCUS_MODE_INFINITY
                && parameters.getFocusMode() != focusMode) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_MACRO)
                && focusMode == Camera.Parameters.FOCUS_MODE_MACRO
                && parameters.getFocusMode() != focusMode) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                && focusMode == Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
                && parameters.getFocusMode() != focusMode) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
    }

    public String getFocusMode(String focusMode) {
        // Apply an alternative focus mode

        Camera.Parameters parameters = mCamera.getParameters();
        final List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO) && focusMode == Camera.Parameters.FOCUS_MODE_AUTO) {
            return (Camera.Parameters.FOCUS_MODE_AUTO);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED) && focusMode == Camera.Parameters.FOCUS_MODE_FIXED) {
            return (Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY) && focusMode == Camera.Parameters.FOCUS_MODE_INFINITY) {
            return (Camera.Parameters.FOCUS_MODE_INFINITY);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_MACRO) && focusMode == Camera.Parameters.FOCUS_MODE_MACRO) {
            return (Camera.Parameters.FOCUS_MODE_MACRO);
        }
        return Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;
    }

    ///////////////////////////XU LY FOCUS/////////////////////
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
//            float touchMajor = event.getTouchMajor();
//            float touchMinor = event.getTouchMinor();
            Rect touchRect = new Rect(
                    (int) (x - 50 < 0 ? 0 : (x - 50)),
                    (int) (y - 50 < 0 ? 0 : (y - 50)),
                    (int) (x + 50),
                    (int) (y + 50));
//            Rect touchRect = new Rect(
//                    (int) (x - touchMajor / 2),
//                    (int) (y - touchMinor / 2),
//                    (int) (x + touchMajor / 2),
//                    (int) (y + touchMinor / 2));

            //((ChupAnhActivity) getContext()).touchFocus(touchRect);
        }

        return true;

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // TODO Auto-generated method stub
//        setMeasuredDimension(
//                MeasureSpec.getSize(widthMeasureSpec),
//                MeasureSpec.getSize(heightMeasureSpec));
//    }

}
