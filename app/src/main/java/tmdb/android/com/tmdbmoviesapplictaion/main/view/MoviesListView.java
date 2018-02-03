package tmdb.android.com.tmdbmoviesapplictaion.main.view;

import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;

/**
 * Created by Gunjan on 02-02-2018.
 */

public interface MoviesListView {
    void onSetProgressBarVisibility(int visiblity);

    void moviesServerError(String message);

    void movieslistSuccess(ModelForMoviesList modelForMoviesList, boolean isShorted);

    void openDetailView(ModelForMoviesList.ResultsBean resultsBean);
}
