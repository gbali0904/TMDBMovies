package tmdb.android.com.tmdbmoviesapplictaion.utility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentManagerUtil {
    public static final String TAG = FragmentManagerUtil.class.getSimpleName();

    public FragmentManagerUtil() {
        // TODO Auto-generated constructor stub
    }



    public static void replaceFragment(FragmentManager fm, int container, Fragment target, boolean backstack, String TAG)
    {
        Fragment fragment = target;
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = fm;
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment, TAG);
        if (backstack)
            fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    public static void addFragment(FragmentManager fm, int container, Fragment target, boolean backstack, String TAG)
    {

        Fragment fragment = target;
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = fm;
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment, TAG);
        //add to backstack only if backstack flag is true
        if(backstack)
            fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }



    public FragmentManagerUtil(FragmentManager fm, int container, Fragment target) {


        Fragment fragment = target;
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = fm;
        fragmentManager.executePendingTransactions();
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void changeFragmentWithExtra(FragmentManager fm, int container, Fragment target, Bundle bundle) {

        Fragment fragment = target;
        fragment.setArguments(bundle);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = fm;
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
