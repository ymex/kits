package cn.ymex.kits;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * fragment manager
 */
public class FragmentManagerWrap {

    private List<Fragment> mFragments;
    private FragmentManager supportManager;
    private int containerViewId = 0;
    private boolean lazyInit = true;//延迟初始化

    private FragmentManagerWrap(FragmentManager manager) {
        this.supportManager = manager;
    }

    public static FragmentManagerWrap build(FragmentManager manager) {
        return new FragmentManagerWrap(manager);
    }

    /**
     * Optional identifier of the container this fragment is
     * to be placed in.
     *
     * @param containerViewId id
     * @return this
     */
    public FragmentManagerWrap setContainerViewId(@IdRes int containerViewId) {
        this.containerViewId = containerViewId;
        return this;
    }

    public boolean attached() {
        return getFragments().size() > 0;
    }

    /**
     * add fragment to manager
     *
     * @param fragments need manage fragment
     * @return this
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
        if (supportManager == null) {
            throw new IllegalArgumentException("supportManager is null");
        }
        add(getFragments().toArray(new Fragment[getFragments().size()]));
    }


    /**
     * 切换显示指定fragment 序列
     *
     * @param index 添加时的顺序
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
        FragmentTransaction transaction = supportManager.beginTransaction();
        for (Fragment fg : mFragments) {
            if (fg == fragment) {
                if (fg.isAdded()) {
                    transaction.show(fg);
                } else {
                    transaction.add(containerViewId, fragment).show(fragment);
                }
            } else {
                if (fg.isAdded()) {
                    transaction.hide(fg);
                }
            }
        }
        transaction.commit();
    }

    public void attach() {
        this.attach(0);
    }

    /**
     * fragment 依附于view 并显示列表中第 index Fragment
     *
     * @param index index
     */
    public void attach(int index) {
        if (containerViewId <= 0) {
            throw new IllegalArgumentException("Fragment attach id is null");
        }

        if (getFragments().size() <= 0) {
            throw new IllegalArgumentException("Fragment list is null");
        }
        if (index < 0) {
            return;
        }
        FragmentTransaction transaction = supportManager.beginTransaction();
        if (lazyInit) {
            transaction.add(containerViewId, getFragment(index));
        } else {
            for (Fragment fg : getFragments()) {
                transaction.add(containerViewId, fg).hide(fg);
            }
        }
        transaction.show(getFragment(index)).commit();
    }

    public Fragment getFragment(int index) {
        if (getFragments().size() < 0) {
            return null;
        }
        if (index >= getFragments().size()) {
            return getFragments().get(getFragments().size() - 1);
        }

        return getFragments().get(index <= 0 ? 0 : index);
    }

    private List<Fragment> getFragments() {
        return mFragments == null ? mFragments = new ArrayList<>(4) : mFragments;
    }

    public FragmentManager getFragmentManager() {
        return supportManager;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public FragmentManagerWrap setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
        return this;
    }
}
