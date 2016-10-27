package cn.ymex.cocccute.flux;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.base.BaseActivity;
import cn.ymex.cute.kits.Optional;
import cn.ymex.cute.kits.ViewKit;
import cn.ymex.cute.mode.flux.Store;

public class FluxActivity extends BaseActivity {
    private EditText etInput;
    private Button btnPost;
    private TextView tvPlay;

    MessageStore store ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flux_hi);
        init();
        initflux();
    }

    private void initflux() {
        store = new MessageStore();
        store.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        store.unregister(this);
    }

    private void init() {
        etInput = ViewKit.find(this, R.id.et_input);
        btnPost = ViewKit.find(this, R.id.btn_post);
        btnPost.setOnClickListener(this);
        tvPlay = ViewKit.find(this, R.id.tv_play);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String message = etInput.getText().toString();
        switch (v.getId()) {
            case R.id.btn_post:
                message = Optional.isEmpty(message)?"Empty - -,":message;
                MessageAction.sendMessage(message);
                break;
        }
    }

    @Subscribe
    public void onAction(Store.StoreChangeEvent event) {
        switch (event.getAction().getType()) {
            case MessageAction.ACTION_SEND_MESSAGE:
                tvPlay.setText(store.getMessage().getContent());
                break;
        }
    }
}
