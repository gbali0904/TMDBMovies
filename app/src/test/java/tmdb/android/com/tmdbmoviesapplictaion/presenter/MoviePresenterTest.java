package tmdb.android.com.tmdbmoviesapplictaion.presenter;

import android.os.Looper;
import android.widget.ImageView;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;
import tmdb.android.com.tmdbmoviesapplictaion.data.APIServices;
import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;
import tmdb.android.com.tmdbmoviesapplictaion.main.presenter.MoviesPresenterCompl;
import tmdb.android.com.tmdbmoviesapplictaion.main.view.MoviesListView;
import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;

import rx.Subscriber;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 11/28/2016
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, ModelForMoviesList.class})
public class MoviePresenterTest {

    public static final String TEST_ERROR_MESSAGE = "error_message";

    @InjectMocks
    private MoviesPresenterCompl presenter;

    @Mock
    private APIServices mApiService;

    @Mock
    private MoviesListView mView;

    @Mock
    private Observable<ModelForMoviesList> mObservable;

    @Captor
    private ArgumentCaptor<Subscriber<ModelForMoviesList>> captor;

    private final RxJavaSchedulersHook mRxJavaSchedulersHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    @Before
    public void setUp() throws Exception {
        initMocks(this);

    }

    @Test
    public void isMovieListReturnWhenCallinggetMovieList() throws Exception {
        PowerMockito.mockStatic(Looper.class);
        when(AndroidSchedulers.mainThread()).thenReturn(mRxJavaSchedulersHook.getComputationScheduler());
        when(mApiService.getMoviesList(1, Constants.API_KEY, "", Constants.LANGUAGE)).thenReturn(mObservable);
        presenter.getMoviesList(1,"",true);
    }

    @Test
    public void onCompleted() throws Exception {
        presenter.onCompleted();
    }

    @Test
    public void onError() throws Exception {
        presenter.onError(new Throwable(TEST_ERROR_MESSAGE));
        verify(mView, times(1)).moviesServerError(TEST_ERROR_MESSAGE);
    }

    @Test
    public void onNextWhenIsShorted() throws Exception {
        ModelForMoviesList response = mock(ModelForMoviesList.class);
        presenter.setIsShorted(true);
        presenter.onNext(response);
        verify(mView, times(1)).movieslistSuccess(response,true);
    }

    @Test
    public void onNextWhenIsNotShorted() throws Exception {
        ModelForMoviesList response = mock(ModelForMoviesList.class);
        presenter.setIsShorted(false);
        presenter.onNext(response);
        verify(mView, times(1)).movieslistSuccess(response,false);
    }

    @Test
    public void isMovingTodetailPage() throws Exception {
        ModelForMoviesList.ResultsBean resultsBean = Mockito.mock(ModelForMoviesList.ResultsBean.class);
        ImageView mockedImageView = Mockito.mock(ImageView.class);
        presenter.moveToDetail(resultsBean,mockedImageView);
        verify(mView, times(1)).openDetailView(resultsBean,mockedImageView);
    }

}
