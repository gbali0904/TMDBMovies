package tmdb.android.com.tmdbmoviesapplictaion.main.dialog;

        import android.app.Dialog;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Toast;

        import butterknife.Bind;
        import butterknife.ButterKnife;
        import butterknife.OnClick;
        import tmdb.android.com.tmdbmoviesapplictaion.R;
        import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;

/**
 * Created by Android on 11/2/2017.
 */

public class SortDialogForMovies extends DialogFragment {
    public static final String TAG = SortDialogForMovies.class.getSimpleName();
    private static OnCategorySortListener categorySortListener;
    private static SortDialogForMovies mInstance;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.rbDialogSortHighestRated)
    RadioButton rbDialogSortPriceHighToLow;
    @Bind(R.id.rbDialogSortMostPopular)
    RadioButton rbDialogSortPriceLowToHigh;

    public SortDialogForMovies() {
        // Required empty public constructor
    }

    public static SortDialogForMovies getInstance(OnCategorySortListener listener) {
        categorySortListener = listener;
        if (mInstance == null) {
            mInstance = new SortDialogForMovies();
        }
        return mInstance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() == null)
            return;
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btnDialogSortOk, R.id.btnDialogSortCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDialogSortOk:
                String sortingType = "";
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rbDialogSortMostPopular:
                        sortingType = Constants.SHORTING_TYPE_MOST_POPULAR;
                        break;
                    case R.id.rbDialogSortHighestRated:
                        sortingType = Constants.SHORTING_TYPE_HIGEST_RATED;
                        break;
                }

                if (!sortingType.equals("")) {
                    categorySortListener.onCategorySort(getDialog(), sortingType);
                    radioGroup.clearCheck();
                }
                else {
                    Toast.makeText(getActivity(),"Please Select one",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnDialogSortCancel:

                radioGroup.clearCheck();
                getDialog().dismiss();
                break;
        }
    }

    public interface OnCategorySortListener {
        void onCategorySort(Dialog dialog, String sortingType);
    }
}