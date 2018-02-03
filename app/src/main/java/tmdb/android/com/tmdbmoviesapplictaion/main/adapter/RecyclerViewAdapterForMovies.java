package tmdb.android.com.tmdbmoviesapplictaion.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tmdb.android.com.tmdbmoviesapplictaion.R;
import tmdb.android.com.tmdbmoviesapplictaion.main.MainActivity;
import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;
import tmdb.android.com.tmdbmoviesapplictaion.moviesDetail.MoviesDeatilFragment;
import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;
import tmdb.android.com.tmdbmoviesapplictaion.utility.FragmentManagerUtil;

/**
 * Created by Gunjan on 02-02-2018.
 */
public class RecyclerViewAdapterForMovies extends RecyclerView.Adapter {
    private static OnMovieClickListener onMovieClickListener;
    private final MainActivity mainActivity;
    private final List<ModelForMoviesList.ResultsBean> data;


    public RecyclerViewAdapterForMovies(MainActivity mainActivity, List<ModelForMoviesList.ResultsBean> data) {
        this.mainActivity = mainActivity;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bindData(final ModelForMoviesList.ResultsBean resultsBean) {
            String poster_path = Constants.IMAGE_URL+resultsBean.getPoster_path();
            Glide.with(mainActivity)
                    .load(poster_path)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   onMovieClickListener.onMovieClick(resultsBean);
                }
            });
        }
    }

    public interface OnMovieClickListener{
        void onMovieClick(ModelForMoviesList.ResultsBean resultsBean);
    }

    public void setOnMovieClickListener(OnMovieClickListener listener){

        onMovieClickListener = listener;
    }
}
