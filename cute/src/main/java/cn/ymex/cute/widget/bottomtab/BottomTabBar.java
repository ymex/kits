package cn.ymex.cute.widget.bottomtab;

import android.annotation.TargetApi;
import android.support.annotation.MenuRes;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymexc on 2016/5/6.
 */
public class BottomTabBar extends FrameLayout {
    private final static int MIN_TABS_COUNT = 3;
    private final static int MAX_TABS_COUNT = 5;
    private int mBottomTabBarHeight = 56;//  bottomtabbar default height  dip



    private List<BaseBottomTab> mBottomTabs;
    private AttributeSet mDefaultAttrs;

    public BottomTabBar(Context context) {
        super(context);
        initConstructor(context,null);
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConstructor(context,attrs);
    }

    public BottomTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConstructor(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomTabBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initConstructor(context,attrs);
    }

    private void initConstructor(Context context,AttributeSet attrs) {
        mBottomTabs = new ArrayList<>(MAX_TABS_COUNT);
    }

    public BottomTabBar addBottomTab(BaseBottomTab bottomTab){
        this.mBottomTabs.add(bottomTab);
        return this;
    }

    public BottomTabBar setBottomTabs(List<BaseBottomTab> bottomTabs){
        whenBottomTabsListNull();
        mBottomTabs.clear();
        mBottomTabs.addAll(bottomTabs);
        return this;
    }

    public BottomTabBar setBottomTabsFromMenu(@MenuRes int menuRes ){
        whenBottomTabsListNull();
        mBottomTabs.clear();
        mBottomTabs.addAll(inflateMenuFromResource(getContext(),menuRes));
        return this;
    }

    private void whenBottomTabsListNull(){
        if (null == mBottomTabs){
            mBottomTabs = new ArrayList<>(MAX_TABS_COUNT);
        }
    }

    /**
     * A hacky method for inflating menus from xml resources to an array
     * of BottomBarTabs.
     *
     * @param context the  context for retrieving the MenuInflater.
     * @param menuRes  the xml menu resource to inflate
     * @return an Array of BottomBarTabs.
     */
    private  List<BottomTab> inflateMenuFromResource(Context context, @MenuRes int menuRes) {
        PopupMenu popupMenu = new PopupMenu(context, null);
        Menu menu = popupMenu.getMenu();
        MenuInflater menuInflater = new MenuInflater(context);
        menuInflater.inflate(menuRes,menu);
        int menuSize = menu.size();
        List<BottomTab> tabs = new ArrayList<>();

        for (int i = 0; i < menuSize; i++) {
            MenuItem item = menu.getItem(i);
            BottomTab tab = new BottomTab(item.getIcon(),
                    String.valueOf(item.getTitle()));
            tab.id = item.getItemId();
            tabs.add(tab);
        }
        return tabs;
    }

    public void build(){
        if (mBottomTabs.size()<MIN_TABS_COUNT || mBottomTabs.size() > MAX_TABS_COUNT){
            throw new IllegalArgumentException("BottomTabBar allow has "+MIN_TABS_COUNT+"-" +MAX_TABS_COUNT+" tabs!");
        }

    }

}

