package br.com.efilho.gigigotest.view.activities;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.efilho.gigigotest.R;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class BaseNetworkActivity extends AppCompatActivity {

    @BindView(R.id.pb_connection_loading)
    public ProgressBar pbConnectionLoading;
    @BindView(R.id.iv_err_connection_loading)
    public ImageView ivConnectionLoading;
    @BindView(R.id.tv_err_connection_loading)
    public TextView tvConnectionLoading;
    @BindView(R.id.connection_loading_root)
    public View vwConnectionLoadingRoot;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showConnectionProgressBar(boolean visible){
        pbConnectionLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
        ivConnectionLoading.setVisibility(View.GONE);
        tvConnectionLoading.setVisibility(View.GONE);
        vwConnectionLoadingRoot.setOnClickListener(null);
    }

    protected void showConnectionErrorLayout(boolean visible, View.OnClickListener listener){
        pbConnectionLoading.setVisibility(View.GONE);
        ivConnectionLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvConnectionLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
        vwConnectionLoadingRoot.setOnClickListener(listener);
    }

    protected void showToast(String message, int duration){
        Toast.makeText(this, message, duration).show();
    }
}
