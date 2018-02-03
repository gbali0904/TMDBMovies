package tmdb.android.com.tmdbmoviesapplictaion.moviesDetail;

        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.like.LikeButton;
        import com.like.OnAnimationEndListener;
        import com.like.OnLikeListener;

        import java.util.ArrayList;

        import butterknife.Bind;
        import butterknife.ButterKnife;
        import butterknife.OnClick;
        import tmdb.android.com.tmdbmoviesapplictaion.R;
        import tmdb.android.com.tmdbmoviesapplictaion.database.SQLiteHelper;
        import tmdb.android.com.tmdbmoviesapplictaion.main.MainActivity;
        import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;
        import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;

        import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Gunjan on 03-02-2018.
 */
public class MoviesDeatilFragment extends Fragment implements  OnLikeListener {
    public static final String TAG = MoviesDeatilFragment.class.getSimpleName();
    private static ModelForMoviesList.ResultsBean resultsBean;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.txtForOriginalTitle)
    TextView txtForOriginalTitle;
    @Bind(R.id.txtForOverview)
    TextView txtForOverview;
    @Bind(R.id.rating)
    RatingBar rating;
    @Bind(R.id.txtForReleaseDate)
    TextView txtForReleaseDate;
    @Bind(R.id.thumb_button)
    LikeButton thumbButton;
    private SQLiteHelper sQLiteHelper;
    private String like_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
    public static MoviesDeatilFragment newInstance(ModelForMoviesList.ResultsBean resultsBean) {
        MoviesDeatilFragment.resultsBean = resultsBean;
        return new MoviesDeatilFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_movies_deatil, container, false);
        ButterKnife.bind(this, view);

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sQLiteHelper = new SQLiteHelper(getActivity());
        like_status=  sQLiteHelper.getAllRecordsAlternate(resultsBean.getId());
        Glide.with(getActivity())
                .load(Constants.IMAGE_URL + resultsBean.getPoster_path())
                .into(image);
        txtForOriginalTitle.setText("" + resultsBean.getOriginal_title());
        txtForOverview.setText("" + resultsBean.getOverview());

        rating.setRating((float) resultsBean.getVote_average());
        txtForReleaseDate.setText("Release Date:  " + resultsBean.getRelease_date());
        if (like_status.equals("1"))
        {
            Log.e("this","status active");
            thumbButton.setLiked(true);
        }
        else {
            Log.e("this","status not active");
            thumbButton.setLiked(false);
        }

        thumbButton.setOnLikeListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void liked(LikeButton likeButton) {
        like_status="1";
        sQLiteHelper.updateRecord(resultsBean.getId(),like_status);
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        like_status="0";
        sQLiteHelper.updateRecord(resultsBean.getId(),like_status);
    }
}
