package cn.ymex.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ymex.kits.Finder;

/**
 * Created by ymexc on 2017/8/12.
 */

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Finder finder = Finder.build(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
