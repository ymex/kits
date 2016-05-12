package cn.ymex.cute.widget.bottomtab;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by ymexc on 2016/5/6.
 */
public class BottomTab extends BaseBottomTab {

    private Context context;

    private BottomTab(Context context){
        this.context = context;
    }

    public BottomTab(Drawable icon,String title){
        this.icon = icon;
        this.title = title;
    }

    public void setIcon(int icon , int selectIcon){
        this.icon = getIcon(context,icon);
        this.selectIcon = getIcon(context,selectIcon);
    }

    public void setIcon(Drawable icon , Drawable selectIcon){
        this.icon = icon;
        this.selectIcon = selectIcon;
    }



    public void setTextColor(int textColor, int selectTextColor ){
        this.textColor = textColor;
        this.selectTextColor = selectTextColor;
    }

    public void setTextColorRes(int textColorRes, int selectTextColorRes ){
        this.textColor = textColor;
        this.selectTextColor = selectTextColor;
    }


    public void setTitle(int textRes){
        this.title = getTitle(context,textRes);
    }

    public void setTitle(String title){
        this.title = title;
    }


    public static class Bulider{
        BottomTab bottomTab;


        public Bulider(Context context){
            this.bottomTab= new BottomTab(context);
        }

        public BottomTab icon(int icon){
            bottomTab.setIcon(icon, 0);
            return this.bottomTab;
        }

        public BottomTab icon(int icon , int selectIcon){
            bottomTab.setIcon(icon,selectIcon);
            return bottomTab;
        }

        public BottomTab titleColor(int textColor, int selectTextColor){
            bottomTab.setTextColor(textColor,selectTextColor);
            return bottomTab;
        }

        public BottomTab title(int titleRes){
            bottomTab.setTitle(titleRes);
            return bottomTab;
        }



    }
}
