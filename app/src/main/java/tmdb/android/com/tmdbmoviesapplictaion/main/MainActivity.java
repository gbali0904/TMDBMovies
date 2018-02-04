package tmdb.android.com.tmdbmoviesapplictaion.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import tmdb.android.com.tmdbmoviesapplictaion.R;
import tmdb.android.com.tmdbmoviesapplictaion.app.AppController;
import tmdb.android.com.tmdbmoviesapplictaion.database.MoviesDataBaseHelper;
import tmdb.android.com.tmdbmoviesapplictaion.di.component.ApplicationComponent;
import tmdb.android.com.tmdbmoviesapplictaion.di.component.DaggerCakeComponent;
import tmdb.android.com.tmdbmoviesapplictaion.di.model.MovieModule;
import tmdb.android.com.tmdbmoviesapplictaion.main.adapter.RecyclerViewAdapterForMovies;
import tmdb.android.com.tmdbmoviesapplictaion.main.dialog.SortDialogForMovies;
import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;
import tmdb.android.com.tmdbmoviesapplictaion.main.presenter.MoviesPresenterCompl;
import tmdb.android.com.tmdbmoviesapplictaion.main.view.MoviesListView;
import tmdb.android.com.tmdbmoviesapplictaion.moviesDetail.MoviesDeatilFragment;
import tmdb.android.com.tmdbmoviesapplictaion.utility.DialogUtils;

public class MainActivity extends AppCompatActivity implements MoviesListView , OnMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.rvMovies)
    SuperRecyclerView rvMovies;
    private RecyclerViewAdapterForMovies adapter;
    String sorted_by;
    private List<ModelForMoviesList.ResultsBean> movieList =new ArrayList<>();
    int page=1;
    private int total_pages;
    private MoviesDataBaseHelper sQLiteHelper;


    @Inject
    protected MoviesPresenterCompl moviesListPresenterCompl;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort:
                SortDialogForMovies sortDialog = SortDialogForMovies.getInstance(new SortDialogForMovies.OnCategorySortListener() {
                    @Override
                    public void onCategorySort(Dialog dialog, String sortingType) {
                        dialog.dismiss();
                        sortListAccTo(sortingType);
                    }
                });
                sortDialog.show(getSupportFragmentManager(), SortDialogForMovies.TAG);
                return true;

            default:
                setTitle();
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Rest Title to Movie List
     */
    private void setTitle() {
        getSupportActionBar().setTitle("Movies List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle();
    }

    /**
     *
     * @param sortingType
     */
    private void sortListAccTo(String sortingType) {
        page=1;
        sorted_by =sortingType;

        moviesListPresenterCompl.setProgressBarVisiblity(0);
        moviesListPresenterCompl.getMoviesList(page, sorted_by,true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        resolveDaggerDependency();
        getSupportActionBar().setTitle("Movies List");
        sQLiteHelper = new MoviesDataBaseHelper(MainActivity.this);
        moviesListPresenterCompl.setProgressBarVisiblity(0);
        moviesListPresenterCompl.getMoviesList(page, sorted_by, false);
        //Initial Setup of List
        setUpList();


    }

    private void resolveDaggerDependency() {
        DaggerCakeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .movieModule(new MovieModule(this))
                .build().inject(this);
    }

    private ApplicationComponent getApplicationComponent() {
        return ((AppController) getApplication()).getApplicationComponent();
    }
    /**
     * Initial Setup
     */
    private void setUpList() {
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecyclerViewAdapterForMovies(this, movieList);
        rvMovies.setAdapter(adapter);
        rvMovies.setOnMoreListener(this);
        rvMovies.setRefreshListener(this);
        rvMovies.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        adapter.setOnMovieClickListener(new RecyclerViewAdapterForMovies.OnMovieClickListener() {
            @Override
            public void onMovieClick(ModelForMoviesList.ResultsBean resultsBean, ImageView image) {
                moviesListPresenterCompl.moveToDetail(resultsBean,image);
            }
        });
    }

    /**
     * Toggle progress bar visibility
     * @param visiblity
     */
    @Override
    public void onSetProgressBarVisibility(int visiblity) {
        AppController.getInstance().showProgressDialog(this);
    }

    /**
     * Show Server Error Message
     * @param message
     */
    @Override
    public void moviesServerError(String message) {
        AppController.getInstance().hideProgressDialog();
        DialogUtils.showDialog(getApplication(), message);
        Log.e(TAG, "EnquiryList :" +message);

    }

    /**
     * Show Success Movies List
     * @param modelForMoviesList
     * @param isShorted
     */
    @Override
    public void movieslistSuccess(ModelForMoviesList modelForMoviesList, boolean isShorted) {
        AppController.getInstance().hideProgressDialog();
        if (!modelForMoviesList.getResults().isEmpty()) {
            if (isShorted) {
                movieList.clear();
            }
            total_pages = modelForMoviesList.getTotal_pages();
            movieList.addAll(modelForMoviesList.getResults());
            adapter.notifyDataSetChanged();
            //Insert Data In Local DataBase Sqlite
            sQLiteHelper.insertRecord(movieList);
        }
    }


    /**
     * Show Movie's Detail
     * @param resultsBean
     * @param image
     */
    @Override
    public void openDetailView(ModelForMoviesList.ResultsBean resultsBean, ImageView image) {
        MoviesDeatilFragment moviesdeatilFragment = MoviesDeatilFragment.newInstance(resultsBean);
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(image, ViewCompat.getTransitionName(image))
                .addToBackStack(TAG)
                .replace(R.id.activity_main, moviesdeatilFragment)
                .commit();

        getSupportActionBar().setTitle("Movie Detail");
    }

    /**
     * Load More Data form Server
     * @param overallItemsCount
     * @param itemsBeforeMore
     * @param maxLastVisiblePosition
     */
    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        Log.e("this","More Items");
        if (total_pages > page) {
            page=++page;
            moviesListPresenterCompl.getMoviesList(page, sorted_by, false);
        }

    }

    /**
     * Refresh Movies Lists
     */
    @Override
    public void onRefresh() {
        //Refresh Data from scratch
        page=1;
        sorted_by="";
        movieList.clear();
        moviesListPresenterCompl.getMoviesList(page, sorted_by, false);
    }
}
