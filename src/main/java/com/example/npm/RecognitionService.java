package com.example.npm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecognitionService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static final OkHttpClient client = new OkHttpClient();

    public RecognitionService(String apiKey) {

    }

    public String recognition(String apiKey){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://my-api.plantnet.org/v2/species").newBuilder();
        urlBuilder.addQueryParameter("lang", "en");
        urlBuilder.addQueryParameter("type", "kt");
        urlBuilder.addQueryParameter("api-key", apiKey);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    System.out.println(responseData);

                    return responseData;
                }
                return null;
            }
        });

    }

}