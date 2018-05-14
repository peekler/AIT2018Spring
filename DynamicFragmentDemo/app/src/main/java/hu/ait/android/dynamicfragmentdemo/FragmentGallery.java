package hu.ait.android.dynamicfragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Peter on 2017. 12. 07..
 */

public class FragmentGallery extends Fragment {

    public static final String TAG = "FragmentGallery";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_gallery,
                        container, false);

        return rootView;
    }

}
