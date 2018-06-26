package yuan.com.androidarchitecture.data.remote;

import yuan.com.androidarchitecture.data.remote.model.JokeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface JokeDBService {

    @GET("satinApi")
    Call<JokeResponse> loadJokes(@Query("type") String type, @Query("page") String page);

}
