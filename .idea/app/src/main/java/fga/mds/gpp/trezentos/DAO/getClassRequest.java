package fga.mds.gpp.trezentos.DAO;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpCookie;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getClassRequest extends AsyncTask<String, String, String>{

    private final String email;
    private final String url = "https://trezentos-api.herokuapp.com/api/class/user/find";

    public getClassRequest(String email){

        this.email = email;

    }

    @Override
    protected String doInBackground(String... params) {

        OkHttpClient client = new OkHttpClient();

        String urlWithParameters = getUrlWithParameters();

        Request request = new Request.Builder()
                .url(urlWithParameters)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getUrlWithParameters() {

        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();

        builder.addQueryParameter("email", email);

        return builder.build().toString();
    }

}
