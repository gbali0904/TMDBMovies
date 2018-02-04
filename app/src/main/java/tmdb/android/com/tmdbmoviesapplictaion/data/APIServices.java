package tmdb.android.com.tmdbmoviesapplictaion.data;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;

/**
 * Created by gunjan on 2017/06/28.
 */

public interface APIServices {

    @GET("discover/movie")
    Observable<ModelForMoviesList>  getMoviesList(@Query("page") int page,
                                                  @Query("api_key") String api_key,
                                                  @Query("sort_by") String sort_by,
                                                  @Query("with_original_language") String with_original_language);


}
