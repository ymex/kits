package cn.ymex.cute.widget.bottomtab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by ymexc on 2016/5/6.
 */
public abstract class BaseBottomTab {
    protected int iconResource;
    protected int titleResource;
    protected int color;
    protected int id = -0x1;

    protected Drawable icon;
    protected String title;

    protected Drawable getIcon(Context context) {
        if (this.iconResource != 0) {
            return ContextCompat.getDrawable(context, this.iconResource);
        } else {
            return this.icon;
        }
    }

    protected String getTitle(Context context) {
        if (this.titleResource != 0) {
            return context.getString(this.titleResource);
        } else {
            return this.title;
        }
    }
}
