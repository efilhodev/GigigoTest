package br.com.efilho.gigigotest.view.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.efilho.gigigotest.R;
import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.interfaces.ChannelPlaylistsMVP;
import br.com.efilho.gigigotest.presenter.ChannelPlayListPresenter;
import br.com.efilho.gigigotest.view.adapters.ChannelPlaylistsAdapter;
import butterknife.BindView;

public class MainActivity extends BaseNetworkActivity implements ChannelPlaylistsMVP.View {
    @BindView(R.id.tv_channel_title)
    public TextView tvChannelTitle;
    @BindView(R.id.tv_channel_playlists_count)
    public TextView tvChannelPlaylistsCount;
    @BindView(R.id.rv_channel_playlists)
    public RecyclerView rvChannelPlaylists;
    @BindView(R.id.vw_channel_header_divider)
    public View vwChannelHeaderDivider;


    private ChannelPlaylistsMVP.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void responseYoutubeChannelPlaylistsSuccessfully(Channel channel) {
        showConnectionErrorLayout(false, null);
        if(!channel.getPlaylists().isEmpty()) {
            setupHeader(channel);
            setupList(channel.getPlaylists());
        }
    }

    @Override
    public void responseYoutubeChannelPlaylistsFailed(String mError) {
        showHeader(false);
        showConnectionErrorLayout(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestYoutubeChannelPlaylists();
            }
        });
        showToast(mError, Toast.LENGTH_LONG);
    }

    @Override
    public void showListLoading(boolean isVisible) {
        showConnectionProgressBar(isVisible);
    }

    @Override
    protected void showConnectionProgressBar(boolean visible) {
        showHeader(false);
        super.showConnectionProgressBar(visible);
    }

    private void init(){
        if(null == presenter) presenter = new ChannelPlayListPresenter(this);
        presenter.requestYoutubeChannelPlaylists();
    }

    private void setupList(List<Playlist> playlists){
        ChannelPlaylistsAdapter adapter = new ChannelPlaylistsAdapter(this, playlists);
        rvChannelPlaylists.setLayoutManager(new LinearLayoutManager(this));
        rvChannelPlaylists.setHasFixedSize(true);
        rvChannelPlaylists.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChannelPlaylistsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Playlist obj) {
                PlaylistActivity.navigate(MainActivity.this, obj);
            }
        });
    }

    private void setupHeader(Channel channel){
        tvChannelTitle.setText(channel.getTitle());
        tvChannelPlaylistsCount.setText(
                String.format(tvChannelPlaylistsCount.getText().toString() + ": %01d", channel.getPlaylists().size()));
        showHeader(true);
    }

    private void showHeader(boolean isVisible){
        tvChannelTitle.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        tvChannelPlaylistsCount.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        rvChannelPlaylists.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        vwChannelHeaderDivider.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


}
