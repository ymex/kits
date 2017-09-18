package cn.ymex.kits;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * fragment显示与隐藏管理类
 */

public class FragmentManagerWrap {

    private List<Fragment> mFragments;
    private FragmentManager fragmentManager;

    private FragmentManagerWrap(FragmentManager manager) {
        this.fragmentManager = manager;
    }

    public static FragmentManagerWrap build(FragmentManager manager) {
        return new FragmentManagerWrap(manager);
    }


    /**
     * 是否已经存在依附的fragment
     * @return
     */
    public boolean attached() {
        return this.getFragmentsSize() > 0;
    }

    /**
     * 添加新的fragment 到FragmentManagerWrap中
     * @param fragments
     * @return
     */
    public FragmentManagerWrap add(Fragment... fragments) {
        if (fragments.length <= 0) {
            return this;
        }
        if (getFragments().size() == 0) {
            getFragments().addAll(Arrays.asList(fragments));
            return this;
        }
        for (Fragment fg : fragments) {
            if (!getFragments().contains(fg)) {
                getFragments().add(fg);
            }
        }
        return this;
    }

    public void restore() {
        if (fragmentManager == null) {
            throw new IllegalArgumentException("fragmentManager is null");
        }
        add(fragmentManager.getFragments().toArray(new Fragment[fragmentManager.getFragments().size()]));

    }

    public int getFragmentsSize() {
        return fragmentManager.getFragments().size();
    }

    /**
     * 切换显示指定fragment 序列，添加顺序
     *
     * @param index
     */
    public void showFragment(int index) {

        this.showFragment(getFragments().get(index));
    }

    /**
     * 切换显示指定fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fg : mFragments) {
            if (fg == fragment) {
                transaction.show(fg);
            } else {
                transaction.hide(fg);
            }
        }
        transaction.commit();
    }

    /**
     * 把fragment 依附于view 并显示列表中第一个Fragment
     *
     * @param containerViewId
     */
    public void attach(@IdRes int containerViewId) {
        if (getFragments().size() <= 0) {
            throw new IllegalArgumentException("Fragment list is null");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fg : getFragments()) {
            transaction.add(containerViewId, fg).hide(fg);
        }
        transaction.show(getFirst()).commit();
    }

    public Fragment getFirst() {
        return getFragments().size() > 0 ? getFragments().get(0) : null;
    }

    private List<Fragment> getFragments() {
        return mFragments == null ? mFragments = new ArrayList<>(4) : mFragments;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}