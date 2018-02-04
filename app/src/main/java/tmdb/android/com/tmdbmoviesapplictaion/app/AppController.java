package tmdb.android.com.tmdbmoviesapplictaion.app;

/**
 * Created by Gunjan on 10-07-2017.
 */

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.rey.material.widget.ProgressView;

import tmdb.android.com.tmdbmoviesapplictaion.R;
import tmdb.android.com.tmdbmoviesapplictaion.di.component.ApplicationComponent;
import tmdb.android.com.tmdbmoviesapplictaion.di.component.DaggerApplicationComponent;
import tmdb.android.com.tmdbmoviesapplictaion.di.model.ApplicationModule;
import tmdb.android.com.tmdbmoviesapplictaion.utility.Constants;


/**
 * Created by Admin7 on 3/9/2017.
 */

public class AppController extends Application {
    private static SharedPreferences sharedPreferences;
    private static AppController mInstance;
    private SharedPreferences pref;
    private Gson gson;
    private Dialog mProgressDlg;
    private ProgressView pv_circular_colors;

    private ApplicationComponent mApplicationComponent;
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initializeApplicationComponent();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppController.this);
        mInstance = this;
    }


    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, Constants.BASE_URL))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public SharedPreferences getPreference() {
        if (pref == null)
            pref = PreferenceManager.getDefaultSharedPreferences(AppController.this);
        return pref;
    }
    /**
     * This method is used to get instance of Default Shared preference editor
     * which can be used throughout the application.
     *
     * @return singleton instance of default shared preference eidtor
     */
    public SharedPreferences.Editor getPreferenceEditor() {
        if (pref == null)
            pref = PreferenceManager.getDefaultSharedPreferences(AppController.this);
        return pref.edit();
    }

    public static void savePreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getPreferences(String key, String val) {
        String value = sharedPreferences.getString(key, val);
        return value;
    }
    public Gson getGson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }
    public void showProgressDialog(Context context) {
        mProgressDlg = new Dialog(context);
        mProgressDlg.setCancelable(false);
        mProgressDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mProgressDlg.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        mProgressDlg.getWindow().setAttributes(lp);
        mProgressDlg.setContentView(R.layout.progress_dialog);
        pv_circular_colors = (ProgressView)mProgressDlg.findViewById(R.id.progress_pv_circular_colors);
        pv_circular_colors.start();
        mProgressDlg.show();

    }
    public void hideProgressDialog() {
        if (mProgressDlg != null) {
            if (mProgressDlg.isShowing())
                mProgressDlg.dismiss();
        }

    }


}
