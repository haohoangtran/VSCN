package lachongmedia.vn.nfc.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.adapters.ImagesAdapter;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import lachongmedia.vn.nfc.networks.NetContext;
import lachongmedia.vn.nfc.server.ReportIssueSerice;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportIssueActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1001;
    private static final int PERMISSION_ALL = 1002;
    private static final int PERMISSION_CAMERA = 1003;
    private static final String TAG = ReportIssueActivity.class.getSimpleName();
    @BindView(R.id.sp_places)
    AppCompatSpinner spPlace;
    @BindView(R.id.bt_capture)
    AppCompatButton btCapture;
    @BindView(R.id.til_content)
    TextInputLayout tilContent;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;
    LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Vector<String> strings = new Vector<>();
        for (int i = 0; i < loginRespon.getKehoach().getDsdiadiem().size(); i++) {
            strings.add(loginRespon.getKehoach().getDsdiadiem().get(i).getDiadiem().getTendiadiem());
        }
        strings.add("Khác");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlace.setAdapter(adapter);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Báo hỏng");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Map<String, RequestBody> files = new HashMap<>();
                ReportIssueSerice serice = NetContext.instance.create(ReportIssueSerice.class);
                for (int i = 0; i < DbContext.instance.getPathImageIssue().size(); i++) {

                    RequestBody requestBody = Utils.createRequestBody(new File(DbContext.instance.getPathImageIssue().get(i)));
                    files.put("a" + i, requestBody);
                }
                serice.sendReport(files, "1", "1").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e(TAG, "onResponse: hihi");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(TAG, "onFailure: Looix");
                    }
                });


            }
        });
        addListener();
    }

    public void upload() {
        ReportIssueSerice serice = NetContext.instance.create(ReportIssueSerice.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (int i = 0; i < DbContext.instance.getPathImageIssue().size(); i++) {
            File file1 = new File(DbContext.instance.getPathImageIssue().get(i));
            builder.addFormDataPart("files", file1.getName(), RequestBody.create(MediaType.parse("image/*"), file1));
        }
        MultipartBody requestBody = builder.build();

    }

    private void addListener() {
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSION = {Manifest.permission.CAMERA};
                if (!Utils.hasPermissions(ReportIssueActivity.this, PERMISSION)) {
                    ActivityCompat.requestPermissions(ReportIssueActivity.this, PERMISSION, PERMISSION_CAMERA);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            File f = savebitmap(photo);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            // CALL THIS METHOD TO GET THE ACTUAL PATH

            File finalFile = new File(getRealPathFromURI(tempUri));
            Log.e(TAG, String.format("onActivityResult: %s %s", finalFile.getTotalSpace(), finalFile.getPath()));
            DbContext.instance.getPathImageIssue().add(finalFile.getPath());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImagesAdapter adapter = new ImagesAdapter();
        rvImages.setAdapter(adapter);
        rvImages.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private File savebitmap(Bitmap bmp) {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (!Utils.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        String path = Utils.dateToString(new Date());
        path = path + ".jpg";

        File file = new File(extStorageDirectory + "/Aden", path);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, path);
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void uploadMultipart() {
        loginRespon.getNhanvien().getIdNhanvien();
        try {
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(this, "http://svaden.ksmart.vn/api/images/suco/1/1")
                    // starting from 3.1+, you can also use content:// URI string instead of absolute file
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2);
            for (int i = 0; i < DbContext.instance.getPathImageIssue().size(); i++) {
                multipartUploadRequest.addFileToUpload(DbContext.instance.getPathImageIssue().get(i), "jpg");
            }
            String uploadId = multipartUploadRequest.startUpload();
            Log.e(TAG, String.format("uploadMultipart: %s", uploadId));
        } catch (Exception e) {
            Log.e(TAG, String.format("uploadMultipart: %s", e.toString()));
        }

    }
}
