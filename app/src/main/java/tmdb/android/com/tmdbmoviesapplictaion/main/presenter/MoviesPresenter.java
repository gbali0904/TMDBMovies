package tmdb.android.com.tmdbmoviesapplictaion.main.presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tmdb.android.com.tmdbmoviesapplictaion.main.view.BaseView;

/**
 * Created by Gunjan on 02-02-2018.
 */

public class MoviesPresenter<V extends BaseView>  {

    @Inject
    protected V mView;

    protected V getView() {
        return mView;
    }

    protected <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
