package cn.ymex.cocccute.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import cn.ymex.cocccute.R;
import cn.ymex.cocccute.samf.HomeFragment;
import cn.ymex.cute.samf.RootActivity;
import cn.ymex.cute.samf.RootFragment;

public class SAMFActivity extends RootActivity {


    @Override
    protected RootFragment getRootFragment() {
        return new HomeFragment();
    }

    @Override
    public void onCreateNow(Bundle savedInstanceState) {
        setAnim(R.anim.next_in, R.anim.next_out, R.anim.quit_in, R.anim.quit_out);
    }

    /**
     * Set the time to click to Prevent repeated clicks,default 500ms
     *
     * @param CLICK_SPACE Repeat click time(ms)
     */
    public void setClickSpace(long CLICK_SPACE) {
        manager.setClickSpace(CLICK_SPACE);
    }
}

