package cn.ymex.cute.widget.bottomtab;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by ymexc on 2016/5/6.
 */
public class BottomTab extends BaseBottomTab {


    /**
     * Creates a new Tab for the BottomTab.
     *
     * @param iconResource a resource for the Tab icon.
     * @param title        title for the Tab.
     */
    public BottomTab( @DrawableRes int iconResource, @NonNull String title) {
        this.iconResource = iconResource;
        this.title = title;
//        this.id = id;
    }

    /**
     * Creates a new Tab for the BottomTab.
     *
     * @param icon          an icon for the Tab.
     * @param titleResource resource for the title.
     */
    public BottomTab(Drawable icon, @StringRes int titleResource) {
        this.icon = icon;
        this.titleResource = titleResource;
    }

    /**
     * Creates a new Tab for the BottomTab.
     *
     * @param icon  an icon for the Tab.
     * @param title title for the Tab.
     */
    public BottomTab(Drawable icon, @NonNull String title) {
        this.icon = icon;
        this.title = title;
    }


    /**
     * Creates a new Tab for the BottomTab.
     *
     * @param iconResource  a resource for the Tab icon.
     * @param titleResource resource for the title.
     */
    public BottomTab(@DrawableRes int iconResource, @StringRes int titleResource) {
        this.iconResource = iconResource;
        this.titleResource = titleResource;
    }
}
