package yuan.com.androidarchitecture.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;
import yuan.com.androidarchitecture.data.local.JokeDatabase;
import yuan.com.androidarchitecture.data.local.dao.JokeDao;
import yuan.com.androidarchitecture.data.remote.ApiConstants;
import yuan.com.androidarchitecture.data.remote.JokeDBService;
import yuan.com.androidarchitecture.data.remote.RequestInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.connectTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        okHttpClient.readTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(logging);
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    JokeDBService provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(JokeDBService.class);
    }

    @Provides
    @Singleton
    JokeDatabase provideMovieDatabase(Application application) {
        return Room.databaseBuilder(application, JokeDatabase.class, "aa.db").build();
    }

    @Provides
    @Singleton
    JokeDao provideMovieDao(JokeDatabase jokeDatabase) {
        return jokeDatabase.movieDao();
    }

}
