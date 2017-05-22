package lachongmedia.vn.nfc.server;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by tranh on 5/20/2017.
 */

public interface ReportIssueSerice {
    @Multipart
    @POST("images/suco/{id_dd}/{id_nv}")
    Call<Void> sendReport(@PartMap Map<String, RequestBody> files, @Path("id_dd") String iddd, @Path("id_nv") String idnv);
//    Call<Void> sendReportIssue(@Body RequestBody files, @Path("id_dd") String iddd, @Path("id_nv")String idnv);
}
