package tmdb.android.com.tmdbmoviesapplictaion.main.view;

        import android.widget.ImageView;

        import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;

/**
 * Created by Gunjan on 02-02-2018.
 */

public interface MoviesListView extends BaseView {
    void onSetProgressBarVisibility(int visiblity);

    void moviesServerError(String message);
    void movieslistSuccess(ModelForMoviesList modelForMoviesList, boolean isShorted);

    void openDetailView(ModelForMoviesList.ResultsBean resultsBean, ImageView image);


}
