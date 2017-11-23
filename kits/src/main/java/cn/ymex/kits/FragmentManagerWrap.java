package cn.ymex.kits;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.LinkedList;
import java.util.List;

/**
 * simple fragment manager
 */
public class FragmentManagerWrap {

    private LinkedList<Fragment> mFragments;
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

    public void getFragment(int index, Fragment fragment) {
        fragment = getFragment(index);
    }

    public <T extends Fragment> T getFragment(int index) {
        if (getFragments().size() < 0 || index >= getFragments().size()) {
            return null;
        }
        return (T) getFragments().get(index);
    }


    private List<Fragment> getFragments() {
        return mFragments == null ? mFragments = new LinkedList<>() : mFragments;
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

    /**
     * show fragment
     *
     * @param index fragment index
     */
    public void showFragment(int index) {
        this.showFragment(getFragments().get(index), false);
    }


    /**
     * show fragment
     *
     * @param index     fragment index
     * @param commitNow commit now
     */
    public void showFragment(int index, boolean commitNow) {
        this.showFragment(getFragments().get(index), commitNow);
    }

    /**
     * show fragment
     *
     * @param fragment fragment
     */
    public void showFragment(Fragment fragment) {
        showFragment(fragment, false);
    }

    /**
     * show fragment
     *
     * @param fragment  fragment
     * @param commitNow commit now
     */
    public void showFragment(Fragment fragment, boolean commitNow) {
        FragmentTransaction transaction = supportManager.beginTransaction();
        for (Fragment fg : getFragments()) {
            if (fg == fragment) {
                if (fg.isAdded()) {
                    transaction.show(fg);
                } else {
                    transaction.add(containerViewId, fg, getFragmentTag(fg)).show(fragment);
                }
            } else {
                if (fg.isAdded()) {
                    transaction.hide(fg);
                }
            }
        }
        if (commitNow) {
            transaction.commitNow();
        } else {
            transaction.commit();
        }
    }

    /**
     * instance in Activity.onCreate() function
     * @param fragments  fragments
     * @return this
     */
    public FragmentManagerWrap attach(Fragment... fragments) {
        if (containerViewId <= 0) {
            throw new IllegalArgumentException("Fragment attach id is null");
        }
        if (supportManager == null) {
            throw new IllegalArgumentException("supportManager is null");
        }
        getFragments().clear();

        for (Fragment f : fragments) {
            Fragment fragment = supportManager.findFragmentByTag(getFragmentTag(f));
            getFragments().add(fragment == null ? f : fragment);
        }
        if (!lazyInit) {
            FragmentTransaction transaction = supportManager.beginTransaction();
            for (Fragment fg : getFragments()) {
                if (!fg.isAdded()) {
                    transaction.add(containerViewId, fg, getFragmentTag(fg)).hide(fg);
                }
            }
            transaction.commitNow();
        }
        return this;
    }


    public String getFragmentTag(Fragment fragment) {
        String tag = fragment.getClass().getName();
        if (fragment instanceof Alias) {
            return tag + ((Alias) fragment).getAlias();
        }
        return tag;
    }

    public interface Alias {
        String getAlias();
    }
}
