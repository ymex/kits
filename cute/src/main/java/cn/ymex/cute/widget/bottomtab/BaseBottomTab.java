package cn.ymex.cute.widget.bottomtab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by ymexc on 2016/5/6.
 */
public abstract class BaseBottomTab {
    protected int id = -0x1;//id
    protected Drawable icon;
    protected Drawable selectIcon;
    protected int textColor;
    protected int selectTextColor;

    protected String title;

    protected Drawable getIcon(Context context, @DrawableRes int iconResource) {
        if (iconResource != 0) {
            return ContextCompat.getDrawable(context, iconResource);
        } else {
            return this.icon;
        }
    }

    protected String getTitle(Context context, @StringRes int stringRes) {
        if (stringRes != 0) {
            return context.getString(stringRes);
        } else {
            return this.title;
        }
    }
}
