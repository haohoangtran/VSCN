package lachongmedia.vn.nfc.server;

import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tranh on 5/15/2017.
 */

public interface LoginService {
    @GET("login/{id}")
    Call<LoginRespon> login(@Path("id") String id);
}
