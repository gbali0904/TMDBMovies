package tmdb.android.com.tmdbmoviesapplictaion.main.presenter;
        import android.widget.ImageView;

        import javax.inject.Inject;
        import rx.Observable;
        import rx.Observer;
        import tmdb.android.com.tmdbmoviesapplictaion.data.APIServices;
        import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;
        import tmdb.android.com.tmdbmoviesapplictaion.main.view.MoviesListView;
        import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;

/**
 * Created by Gunjan on 02-02-2018.
 */

public class MoviesPresenterCompl extends MoviesPresenter<MoviesListView> implements Observer<ModelForMoviesList> {

    public boolean isShorted;

    @Inject
    protected APIServices mApiService;

    @Inject
    public MoviesPresenterCompl() {
    }

    public void setProgressBarVisiblity(int visiblity) {
        getView().onSetProgressBarVisibility(visiblity);
    }

    public void getMoviesList(int page, String shorted_by, final boolean isShorted) {
        setIsShorted(isShorted);
        Observable<ModelForMoviesList> modelForMoviesListObservable = mApiService.getMoviesList(page,Constants.API_KEY,shorted_by,Constants.LANGUAGE);
        subscribe(modelForMoviesListObservable,this);
    }

    public void moveToDetail(ModelForMoviesList.ResultsBean resultsBean, ImageView image) {
        getView().openDetailView(resultsBean,image);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        getView().moviesServerError(e.getMessage());
    }

    @Override
    public void onNext(ModelForMoviesList modelForMoviesList) {
        getView().movieslistSuccess(modelForMoviesList, getIsShorted());

    }

    public boolean getIsShorted() {
        return isShorted;
    }

    public void setIsShorted(boolean isShorted) {
        this.isShorted=isShorted;
    }
}
