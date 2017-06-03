package vn.lachongmedia.ksmartg.chupanhlibrary.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import vn.lachongmedia.ksmartg.chupanhlibrary.R;
import vn.lachongmedia.ksmartg.chupanhlibrary.customcamera.CameraPreview;
import vn.lachongmedia.ksmartg.chupanhlibrary.customcamera.MyView;


public class ChupAnhActivity extends AppCompatActivity {
    Button btn_chupanh, btnClose, btnFlash, btnQuality;
    RelativeLayout mLayout, toochlayout;
    RelativeLayout ll_flash, ll_quality;
    private LinearLayout camblink;
    boolean isSafeToTakePicture = true;
    int Orient = 0;
    int LastOrient = 0;
    CameraPreview cameraPreview;
    final Handler handler = new Handler();
    public static String stateFlash;
    public static String stateQuality;
    TextView tvFlash, tvQuality;
    ImageView imgPreview;
    private static final int PICTURE_QUALITY = 90;
    private MyView drawingView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chup_anh);
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }


        toochlayout = (RelativeLayout) findViewById(R.id.toochlayout);
        mLayout = (RelativeLayout) findViewById(R.id.layout);
        btn_chupanh = (Button) findViewById(R.id.btnCapture);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnFlash = (Button) findViewById(R.id.btnFlash);
        btnQuality = (Button) findViewById(R.id.btnQuality);
        camblink = (LinearLayout) findViewById(R.id.camblink);
        imgPreview = (ImageView) findViewById(R.id.img_capture_preview);
        ll_flash = (RelativeLayout) findViewById(R.id.ll_flash);
        ll_quality = (RelativeLayout) findViewById(R.id.ll_quality);
        tvFlash = (TextView) findViewById(R.id.tvFlash);
        tvQuality = (TextView) findViewById(R.id.tvQuality);

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChupAnhActivity.this, ConfigFlashActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        btnQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChupAnhActivity.this, ConfigQualityActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        ll_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFlash.performClick();
            }
        });
        ll_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnQuality.performClick();
            }
        });

        btn_chupanh.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                try {
                    if (isSafeToTakePicture) {
                        LastOrient = getLastOrient(Orient);
                        cameraPreview.getmCamera().takePicture(shutterCallback, null, jpegCallback);
                        new Handler().postDelayed(new Runnable() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {
                            }
                        }, 150);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
                        String dateTime = sdf.format(Calendar.getInstance().getTime());
                        date = dateTime;
                        isSafeToTakePicture = false;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {

                }
            }
        });

        stateFlash = "Tự động";
        stateQuality = "Mặc định";
//        stateFlash = "Tự động";
//        stateQuality = "Mặc định";
        tvFlash.setText(stateFlash);
        tvQuality.setText(stateQuality);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int newHeight = (int) (width * ((float) 4 / (float) 3));
        if (drawingView == null) {
            drawingView = new MyView(this);
            toochlayout.addView(drawingView, new RelativeLayout.LayoutParams(width, newHeight));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cameraPreview != null) {

            try {
                cameraPreview.stop();
                mLayout.removeView(cameraPreview);
                cameraPreview = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            cameraPreview = new CameraPreview(this, 0,
                    CameraPreview.LayoutMode.FitToParent);

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            int newHeight = (int) (width * ((float) 4 / (float) 3));
            mLayout.addView(cameraPreview, new RelativeLayout.LayoutParams(width, newHeight));
            cameraPreview.setCamera(3);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            Toast.makeText(ChupAnhActivity.this,"Không thể kết nối máy ảnh, vui lòng thử lại",Toast.LENGTH_SHORT).show();
//            HamDungChung.ShowToast(this, "Không thể kết nối máy ảnh, vui lòng thử lại");
        } catch (Exception e) {
            e.printStackTrace();
//            HamDungChung.ShowToast(this, "Không thể kết nối máy ảnh, vui lòng thử lại");
            Toast.makeText(ChupAnhActivity.this,"Không thể kết nối máy ảnh, vui lòng thử lại",Toast.LENGTH_SHORT).show();

        } finally {
            try {
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (!data.getStringExtra("batFlash").equals("")) {
                stateFlash = data.getStringExtra("batFlash");
                tvFlash.setText(stateFlash);
            }
        }
        if (resultCode == 2) {
            if (!data.getStringExtra("tatFlash").equals("")) {
                stateFlash = data.getStringExtra("tatFlash");
                tvFlash.setText(stateFlash);
            }
        }
        if (resultCode == 3) {
            if (!data.getStringExtra("tudongFlash").equals("")) {
                stateFlash = data.getStringExtra("tudongFlash");
                tvFlash.setText(stateFlash);
            }
        }
        if (resultCode == 4) {
            if (!data.getStringExtra("qualityCao").equals("")) {
                stateQuality = data.getStringExtra("qualityCao");
                tvQuality.setText(stateQuality);
            }
        }
        if (resultCode == 5) {
            if (!data.getStringExtra("qualityMacdinh").equals("")) {
                stateQuality = data.getStringExtra("qualityMacdinh");
                tvQuality.setText(stateQuality);
            }
        }
    }

    public int getLastOrient(int orient) {
        //Log.d("orient: ", orient + "");
        if (orient == -1 || (orient >= 0 && orient < 45) || (orient >= 315)) {
            return 90;
        } else if (orient >= 45 && orient < 180) {
            return 180;
        } else if (orient < 315 && orient >= 180) {
            return 0;
        }
        return 0;
    }

    public int getOrientDeConvertXoayFocusRect(int orient) {
        Log.d("orient: ", orient + "");
        //Từ góc 270 đến 360 thì xoay 270 độ: ok
        if (orient >= 270 && orient <= 360) {
            return 270;
        }

        //Từ góc 180 đến góc 270 thì xoay 270 độ: ok
        if (orient >= 180 && orient < 270) {
            return 270;
        }

        //Từ góc 90 đến góc 180 thì xoay -90 độ: ok
        if (orient >= 90 && orient < 180) {
            return -90;
        }

        //Từ góc 0 đến góc 90 thì xoay -90 độ: ok
        if (orient >= 0 && orient < 90) {
            return -90;
        }


        return 0;
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            // Log.d(TAG, "onShutter'd");
            try {
                AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = null;
            try {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = true;
                opt.inMutable = true;
                opt.inJustDecodeBounds = false;
                opt.inDither = false;
                opt.inPurgeable = true;
                opt.inInputShareable = true;

                Matrix matrix = new Matrix();
                int witdh = 800, height = 600;
                if (!stateQuality.equals("Mặc định")) {
                    witdh = 1600;
                    height = 1200;
                }
                matrix.postRotate(LastOrient);
                opt.inSampleSize = calculateInSampleSize(opt, witdh, height);
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
                bitmap = getResizedBitmap(bitmap, witdh, height, matrix);
                bitmap = themTimestampVaoAnh(bitmap);

                if (!SaveImageToDisk(bitmap)) {
                    Toast.makeText(ChupAnhActivity.this, "Lỗi trong quá trình lưu ảnh, vui lòng chụp lại", Toast.LENGTH_LONG).show();
                }
                //Thread.sleep(200);
            } catch (OutOfMemoryError ex) {
                Toast.makeText(ChupAnhActivity.this, "Lỗi trong quá trình chụp và lưu ảnh, vui lòng chụp lại", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(ChupAnhActivity.this, "Lỗi trong quá trình chụp và lưu ảnh, vui lòng chụp lại", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                try {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.gc();
                } catch (Exception e) {

                }

                try {
                    Camera.Parameters para = cameraPreview.getmCamera().getParameters();
                    cameraPreview.setFocusModeForParameter(para, Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//                    setFocusArea(para, tfocusRect, drawingView);
//                    setMetering(para, tfocusRect, drawingView);

                    cameraPreview.getmCamera().setParameters(para);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
//                    flashLightOff();
                    cameraPreview.getmCamera().startPreview();
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isSafeToTakePicture = true;


            }
        }
    };

    @TargetApi(14)
    public void touchFocus(final Rect tfocusRect) {

        //btn_chupanh.setEnabled(false);

        //camera.stopFaceDetection();

        try {
            Camera.Parameters para = cameraPreview.getmCamera().getParameters();
            cameraPreview.setFocusModeForParameter(para, Camera.Parameters.FOCUS_MODE_AUTO);
            setFocusArea(para, tfocusRect, drawingView);
            setMetering(para, tfocusRect, drawingView);

            cameraPreview.getmCamera().setParameters(para);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cameraPreview.getmCamera().autoFocus(myAutoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            drawingView.setHaveTouch(true, tfocusRect);
            drawingView.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, Matrix matrix) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        //Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
        }
        return resizedBitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private Bitmap themTimestampVaoAnh(Bitmap toEdit) {
        //  Bitmap dest = Bitmap.createBitmap(toEdit.getWidth(), toEdit.getHeight(), Bitmap.Config.ARGB_8888);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // Lay thoi gian hien tai


        Canvas cs = new Canvas(toEdit);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setColor(Color.RED);
        tPaint.setStyle(Paint.Style.FILL);
        float height = tPaint.measureText("yY");
        float weight = tPaint.measureText(dateTime);
        cs.drawText(dateTime, cs.getWidth() - weight - 10f, cs.getHeight() - height, tPaint);
//        try {
//            toEdit.compress(Bitmap.CompressFormat.JPEG, PICTURE_QUALITY, new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/timestamped")));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
        return toEdit;
    }
    public static List<String> pathsList=new Vector<>();
    public static String pathFile = null;
    public static String date = null;

    public String getPathFile() {
        return pathFile;
    }

    public boolean SaveImageToDisk(Bitmap bm) {

        FileOutputStream outStream = null;
        //xin quyền lưu ảnh trên android 6.0
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    112);
        }
        // Write to SD Card
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/Ksmart");
            dir.mkdirs();
            String fileName = String.format("%d.jpg",
                    System.currentTimeMillis());

            File outFile = new File(dir, fileName);
            pathFile = outFile.getPath();
            pathsList.add(pathFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, PICTURE_QUALITY, stream);
            Bitmap anhNho = bm.createScaledBitmap(bm, 128, 160, false);

            try {
                if (imgPreview != null && imgPreview.getDrawable() != null) {
                    Bitmap bmNho = ((BitmapDrawable) imgPreview.getDrawable()).getBitmap();
                    if (bmNho != null && !bmNho.isRecycled()) {
                        bmNho.recycle();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                imgPreview.setImageDrawable(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                imgPreview.setImageBitmap(anhNho);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] byteArray = stream.toByteArray();
            outStream = new FileOutputStream(outFile);
            outStream.write(byteArray);
            outStream.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bm != null && !bm.isRecycled()) {
                try {
                    bm.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {

        if (cameraPreview != null) {
            try {
                cameraPreview.stop();
                mLayout.removeView(cameraPreview);
                cameraPreview = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (imgPreview != null) {
            try {
                if (imgPreview != null && imgPreview.getDrawable() != null) {
                    Bitmap bmNho = ((BitmapDrawable) imgPreview.getDrawable()).getBitmap();
                    if (bmNho != null && !bmNho.isRecycled()) {
                        bmNho.recycle();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                imgPreview.setImageDrawable(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }


    public Rect rectFToRect(RectF rectF) {
        Rect rect = new Rect();
        rect.left = Math.round(rectF.left);
        rect.top = Math.round(rectF.top);
        rect.right = Math.round(rectF.right);
        rect.bottom = Math.round(rectF.bottom);
        return rect;
    }

    private List<Camera.Area> buildTouchArea(Rect tfocusRect, MyView drawingView) {
//        Rect targetFocusRect = new Rect(
//                tfocusRect.left,
//                tfocusRect.top,
//                tfocusRect.right,
//                tfocusRect.bottom);
//        Rect targetFocusRect = new Rect(
//                tfocusRect.left * 2000 / drawingView.getWidth() - 1000,
//                tfocusRect.top * 2000 / drawingView.getHeight() - 1000,
//                tfocusRect.right * 2000 / drawingView.getWidth() - 1000,
//                tfocusRect.bottom * 2000 / drawingView.getHeight() - 1000);
        //int o = getLastOrient(Orient);
        RectF targetFocusRect = new RectF(
                tfocusRect.left * 2000 / drawingView.getWidth() - 1000,
                tfocusRect.top * 2000 / drawingView.getHeight() - 1000,
                tfocusRect.right * 2000 / drawingView.getWidth() - 1000,
                tfocusRect.bottom * 2000 / drawingView.getHeight() - 1000);
//        if (o == 90) {
//            targetFocusRect = new RectF(
//                    tfocusRect.left * 2000 / drawingView.getHeight() - 1000,
//                    tfocusRect.top * 2000 / drawingView.getWidth() - 1000,
//                    tfocusRect.right * 2000 / drawingView.getHeight() - 1000,
//                    tfocusRect.bottom * 2000 / drawingView.getWidth() - 1000);
//        }
        Matrix m = new Matrix();
//        m.postScale(drawingView.getWidth() / 2000f, drawingView.getHeight() / 2000f);
//        m.postTranslate(drawingView.getWidth() / 2f, drawingView.getHeight() / 2f);
        int o = getOrientDeConvertXoayFocusRect(Orient);
        m.postRotate(o);
        m.mapRect(targetFocusRect);

        Rect targetFocusRectNew = rectFToRect(targetFocusRect);
        Log.d("touchfocusRect: ", tfocusRect.left + " | " + tfocusRect.right
                + " | " + tfocusRect.top + " | " + tfocusRect.bottom);
        Log.d("drawingView: ", drawingView.getWidth() + " | " + drawingView.getHeight());
        Log.d("targetFocusRectNew: ", targetFocusRectNew.left + " | " + targetFocusRectNew.right
                + " | " + targetFocusRectNew.top + " | " + targetFocusRectNew.bottom);

        return Collections.singletonList(
                new Camera.Area(targetFocusRectNew, 1000));
    }

    public void setFocusArea(Camera.Parameters parameters, Rect touchRect, MyView drawingView) {
        if (parameters.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> middleArea = buildTouchArea(touchRect, drawingView);
            parameters.setFocusAreas(middleArea);
        } else {
            Log.i("setFocusArea", "Device does not support focus areas");
        }
    }

    public void setMetering(Camera.Parameters parameters, Rect touchRect, MyView drawingView) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> middleArea = buildTouchArea(touchRect, drawingView);
            parameters.setMeteringAreas(middleArea);
        } else {
            Log.i("setMeteringArea", "Device does not support metering areas");
        }
    }

    Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            // TODO Auto-generated method stub
            if (arg0) {
                //btn_chupanh.setEnabled(true);
                try {
                    cameraPreview.getmCamera().cancelAutoFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            float focusDistances[] = new float[3];
//            arg1.getParameters().getFocusDistances(focusDistances);
//            float focusDistances[] = new float[3];
//            arg1.getParameters().getFocusDistances(focusDistances);
//            prompt.setText("Optimal Focus Distance(meters): "
//                    + focusDistances[Camera.Parameters.FOCUS_DISTANCE_OPTIMAL_INDEX]);

        }
    };

}
