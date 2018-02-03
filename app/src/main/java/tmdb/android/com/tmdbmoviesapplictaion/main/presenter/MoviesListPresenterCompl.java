package tmdb.android.com.tmdbmoviesapplictaion.main.presenter;


        import android.util.Log;

        import rx.Subscriber;
        import rx.Subscription;
        import rx.android.schedulers.AndroidSchedulers;
        import tmdb.android.com.tmdbmoviesapplictaion.app.AppController;
        import tmdb.android.com.tmdbmoviesapplictaion.data.ServiceFactory;
        import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;
        import tmdb.android.com.tmdbmoviesapplictaion.main.view.MoviesListView;
        import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;

/**
 * Created by Gunjan on 02-02-2018.
 */

public class MoviesListPresenterCompl implements MoviesListPresenter {
    private final MoviesListView moviesListView;
    private Subscription subscription;

    public MoviesListPresenterCompl(MoviesListView moviesListView) {
        this.moviesListView=moviesListView;
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        moviesListView.onSetProgressBarVisibility(visiblity);
    }

    @Override
    public void getMoviesList(int page, String shorted_by, final boolean isShorted) {
        subscription = ServiceFactory.getInstance().getRetrofitServices().getMoviesList(page,Constants.API_KEY,shorted_by,Constants.LANGUAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(ServiceFactory.getInstance().defaultSubscribeScheduler())
                .subscribe(new Subscriber<ModelForMoviesList>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("this", "signIn Error:" + e.getMessage());
                        moviesListView.moviesServerError(e.getMessage());
                        e.printStackTrace();
                    }
                    @Override
                    public void onNext(ModelForMoviesList modelForMoviesList) {
                        moviesListView.movieslistSuccess(modelForMoviesList,isShorted);
                    }
                });

    }

    @Override
    public void moveToDetail(ModelForMoviesList.ResultsBean resultsBean) {

        moviesListView.openDetailView(resultsBean);

    }
}
