package lachongmedia.vn.nfc.networks;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tranh on 5/15/2017.
 */


public class NetContext {
    public static final NetContext instance = new NetContext();

    private Retrofit retrofit;

    private NetContext() {

        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://svaden.ksmart.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private String getResponseString(Response response) {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        return buffer.clone().readString(Charset.forName("UTF-8"));
    }

    public <T> T create(Class<T> classz) {
        return retrofit.create(classz);
    }

    class LoggerInterceptor implements Interceptor {

        private static final String TAG = "LoggerInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {
            //1 Get request
            Request request = chain.request();

            //2 Process request (print out)
            Log.d(TAG, String.format("url: %s", request.toString()));

            RequestBody body = request.body();
            if (body != null) {
                Log.d(TAG, String.format("body: %s", body.toString()));
            }

            Headers headers = request.headers();
            if (headers != null) {
                Log.d(TAG, String.format(String.format("headers: %s", headers.toString())));
            }

            //3 Proceed
            Response response = chain.proceed(request);

            //4 Process response
            Log.d(TAG, String.format("response: %s", response.toString()));

            Log.d(TAG, String.format("response body: %s", getResponseString(response)));

            return response;
        }
    }
}
