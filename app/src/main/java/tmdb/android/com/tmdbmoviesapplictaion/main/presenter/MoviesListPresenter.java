package tmdb.android.com.tmdbmoviesapplictaion.main.presenter;

import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;

/**
 * Created by Gunjan on 02-02-2018.
 */

public interface MoviesListPresenter {
    void setProgressBarVisiblity(int visibilty);

    void getMoviesList(int page, String shorted_by, boolean isShorted);

    void moveToDetail(ModelForMoviesList.ResultsBean resultsBean);
}
