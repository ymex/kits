package cn.ymex.cute.samf.core;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;


import cn.ymex.cute.samf.SamfFragment;

/**
 * http://blog.csdn.net/guolin_blog/article/details/41087993#t5
 * Created by ymexc on 2016/5/3.
 */
public class FragmentStacksManager {
    private AppCompatActivity mContext;
    private Stack<Stack<SamfFragment>> mFragmentTaskStacks;
    private Stack<SamfFragment> mFragmentStack;


    {
        mFragmentTaskStacks =new Stack<Stack<SamfFragment>>();
        if (null == mFragmentStack) {
            mFragmentStack = new Stack<>();
        }
        mFragmentTaskStacks.add(mFragmentStack);

    }

    public FragmentStacksManager(AppCompatActivity context) {
        this.mContext = context;
    }


    /**
     * standard mode,Directly add to the current task stack
     *
     * @param fragment
     */
    public void putStandard(SamfFragment fragment) {
        getCurrentTask().add(fragment);
    }


    /**
     * SingleTop mode ,If the top is not created
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean putSingleTop(SamfFragment fragment) {
        Stack<SamfFragment> currentTask = getCurrentTask();
        if (currentTask.empty()) {
            currentTask.add(fragment);
            return false;
        } else {
            SamfFragment lastItem = currentTask.peek();
            if (lastItem.getClass().getName().equals(fragment.getClass().getName())) {
                lastItem.OnNewIntent(fragment.getArguments());
                return true;
            } else {
                currentTask.push(fragment);
                return false;
            }
        }
    }


    /**
     * singTask mode ,If the current task stack does not create and empty all of the upper instance
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean putSingleTask(SamfFragment fragment) {
        Stack<SamfFragment>lastList = getCurrentTask();
        if (lastList.isEmpty()) {
            lastList.push(fragment);
            return false;
        }
        int location = getPositionInStack(fragment);
        if (location<0){
            lastList.push(fragment);
            return false;
        }else {
            lastList.removeAll(lastList.subList(location,lastList.size()));
            return true;
        }
    }

    /**
     * singleInstance mode,Create a new task stack at a time.
     *
     * @param fragment
     */
    public void putSingleInstance(SamfFragment fragment) {
        Stack<SamfFragment> frags = new Stack<SamfFragment>();
        frags.push(fragment);
        mFragmentTaskStacks.push(frags);
    }


    /**
     * pop stack item
     */
    public void onBackStack() {
        if (mFragmentTaskStacks.empty()){
            mFragmentTaskStacks.clear();
            return;
        }
        //// TODO: 2016/5/4

    }

    /**
     * get current task stack
     * @return
     */
    private Stack<SamfFragment> getCurrentTask() {
        return mFragmentTaskStacks.peek();
    }

    /**
     * find item position in stack
     * @param fragment
     * @return  if not find return -0x1 else return position
     */
    private int getPositionInStack(SamfFragment fragment){
        Stack<SamfFragment> stack = getCurrentTask();
        for (int i = 0; i<stack.size(); i++){
            if (stack.get(i).getClass().getName().equals(fragment.getClass().getName())) {
                return i;
            }
        }
        return -0x1;
    }

    public static final int STANDARD = 0x10;
    public static final int SINGLE_TOP = 0x20;
    public static final int SINGLE_TASK = 0x30;
    public static final int SINGLE_INSTANCE = 0x40;

    @IntDef({STANDARD, SINGLE_TOP, SINGLE_TASK, SINGLE_INSTANCE})
    public @interface LaunchMode {
    }
}
