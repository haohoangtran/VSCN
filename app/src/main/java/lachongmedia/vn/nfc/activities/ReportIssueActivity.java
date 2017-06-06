package lachongmedia.vn.nfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.ImagesAdapter;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import lachongmedia.vn.nfc.networks.NetContext;
import lachongmedia.vn.nfc.server.ReportIssueService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lachongmedia.ksmartg.chupanhlibrary.activities.ChupAnhActivity;

public class ReportIssueActivity extends AppCompatActivity {
    private static final String TAG = ReportIssueActivity.class.getSimpleName();
    private static final String IMG_JPEG = "image/jpeg";
    private static final int CAPTURE_CODE = 12412;
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
    File finalFile;
    ImagesAdapter adapter;
    private Uri fileUri;

    private static Uri getOutputMediaFileUri(Activity activity) {

        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "AdenService");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("AdenService", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        Log.e(TAG, String.format("getOutputMediaFile: %s", mediaFile.getPath()));

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Vector<String> strings = new Vector<>();
        for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().size(); i++) {
            for (int i1 = 0; i1 < loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().size(); i1++) {
                strings.add(loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().get(i1).getTendiadiem());
            }
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

                requestUploadSurvey();

            }
        });
        addListener();
        hihihi();
    }

    private void requestUploadSurvey() {

        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[DbContext.instance.getPathImageIssue().size()];

        for (int index = 0; index < DbContext.instance.getPathImageIssue().size(); index++) {
            Log.d(TAG, "requestUploadSurvey: survey image " + index + "  " + DbContext.instance.getPathImageIssue().get(index));
            File file = new File(DbContext.instance.getPathImageIssue().get(index));
            RequestBody surveyBody = RequestBody.create(okhttp3.MediaType.parse(IMG_JPEG), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("files", file.getName(), surveyBody);
        }
        ReportIssueService service = NetContext.instance.create(ReportIssueService.class);
        service.uploadSurvey(surveyImagesParts, "4", "1").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.e(TAG, "onResponse: a");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.toString()));
            }
        });
        uploadMultipart();
    }

    public void uploadMultipart() {
        try {
            MultipartUploadRequest upload =
                    new MultipartUploadRequest(this, "http://svaden.ksmart.vn/api/images/suco/4/1")
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2);
            for (int i = 0; i < DbContext.instance.getPathImageIssue().size(); i++) {
                upload.addFileToUpload(DbContext.instance.getPathImageIssue().get(i), "files");
            }
            String uploadId = upload.startUpload();

            Log.e(TAG, "uploadMultipart: " + uploadId);

        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }


    private void addListener() {
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportIssueActivity.this, ChupAnhActivity.class);
                startActivityForResult(intent, CAPTURE_CODE);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ImagesAdapter();
        rvImages.setAdapter(adapter);
        rvImages.setLayoutManager(new GridLayoutManager(this, 4));
    }


    public void hihihi() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // do UI updates here
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                        if (finalFile != null) {
                            Log.e(TAG, String.format("run: %s %s", finalFile.getTotalSpace(), finalFile.getPath()));
                        }
                    }
                });

            }

        }, 0, 60000);//Update text every 60 seconds

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, String.format("onActivityResult: %s", requestCode));
        if (requestCode == CAPTURE_CODE) {
            Log.e(TAG, "onActivityResult: %s");
            DbContext.instance.setPathImageIssue(ChupAnhActivity.pathsList);
        }
    }
}
