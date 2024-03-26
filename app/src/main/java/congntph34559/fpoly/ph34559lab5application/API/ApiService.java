package congntph34559.fpoly.ph34559lab5application.API;

import java.util.List;

import congntph34559.fpoly.ph34559lab5application.DTO.DistributorDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

   public static String DOMAIN = "http://192.168.1.161:3000";

    ApiService API_SERVICE = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("apiDis/get-list-distributor")
    Call<List<DistributorDTO>> getListDistributor();

    @POST("apiDis/post-distributor")
    Call<DistributorDTO> createDistributor(@Body DistributorDTO distributorDTO);

    @DELETE("apiDis/delete-distributor-by-id/{id}")
    Call<DistributorDTO> deleteDistributor(@Path("id") String id);
    @PUT("apiDis/put-distributor-by-id/{id}")
    Call<DistributorDTO> updateDistributor(@Path("id") String id,@Body DistributorDTO distributorDTO);

    @GET("apiDis/search-distributor")
    Call<List<DistributorDTO>> searchDis(@Query("key") String key);



}
