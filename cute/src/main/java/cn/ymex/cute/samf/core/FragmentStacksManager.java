package cn.ymex.cute.samf.core;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Stack;


import cn.ymex.cute.samf.SamfFragment;

/**
 * Created by ymexc on 2016/5/3.
 */
public class FragmentStacksManager {
    private AppCompatActivity mContext;
    private ArrayList<Stack<SamfFragment>> mFragmentTaskList;
    private Stack<SamfFragment> mFragmentStack;


    {
        mFragmentTaskList = new ArrayList<Stack<SamfFragment>>(5);
        if (null == mFragmentStack) {
            mFragmentStack = new Stack<>();
        }
        mFragmentTaskList.add(mFragmentStack);

    }

    public FragmentStacksManager(AppCompatActivity context) {
        this.mContext = context;
    }


    /**
     *
     * @param fragment
     */
    public void putStandard(SamfFragment fragment) {

        return ;
    }



    public static final int STANDARD = 0x10;
    public static final int SINGLE_TOP = 0x20;
    public static final int SINGLE_TASK = 0x30;
    public static final int SINGLE_INSTANCE = 0x40;
    public static final int KEEP_CURRENT = 0x50;
    @IntDef({STANDARD, SINGLE_TOP, SINGLE_TASK,SINGLE_INSTANCE,KEEP_CURRENT})
    public @interface LaunchMode {

    }
}
