package congntph34559.fpoly.ph34559lab5application.API;

import static congntph34559.fpoly.ph34559lab5application.API.ApiService.DOMAIN;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {

    private final ApiService requestInterface;

    public HttpRequest() {
        requestInterface = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);
    }
    public ApiService callAPI() {
        return requestInterface;
    }
}
