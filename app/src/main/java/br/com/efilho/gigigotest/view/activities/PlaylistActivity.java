package br.com.efilho.gigigotest.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.List;

import br.com.efilho.gigigotest.R;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.interfaces.PlaylistVideosMVP;
import br.com.efilho.gigigotest.presenter.PlaylistVideosPresenter;
import br.com.efilho.gigigotest.view.adapters.PlaylistVideosAdapter;
import butterknife.BindView;

public class PlaylistActivity extends BaseNetworkActivity implements PlaylistVideosMVP.View {
    @BindView(R.id.tb_playlist)
    public Toolbar tbPlaylist;
    @BindView(R.id.iv_playlist_thumb)
    public ImageView ivPlaylistThumb;
    @BindView(R.id.rv_playlist_videos)
    public RecyclerView rvPlaylistVideos;

    private PlaylistVideosMVP.Presenter presenter;
    private Playlist playlist;

    public static void navigate(Activity activity, Playlist playlist){
        Intent intent = new Intent(activity, PlaylistActivity.class);
        intent.putExtra(Playlist.class.getSimpleName(), playlist);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        init();
    }

    @Override
    public void responseYoutubePlaylistVideosSuccessfully(Playlist playlist) {
        showConnectionErrorLayout(false, null);
        if(!playlist.getVideos().isEmpty())  setupList(playlist.getVideos());
    }

    @Override
    public void responseYoutubePlaylistVideosFailed(String mError) {
        showConnectionErrorLayout(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestYoutubePlaylistVideos(playlist.getId());
            }
        });
        showToast(mError, Toast.LENGTH_SHORT);
    }

    @Override
    public void showListLoading(boolean isVisible) {
        showConnectionProgressBar(isVisible);
    }

    @Override
    protected void showConnectionProgressBar(boolean visible) {
        super.showConnectionProgressBar(visible);
    }

    private void init(){
        playlist = getIntent().getParcelableExtra(Playlist.class.getSimpleName());
        if(null == presenter) presenter = new PlaylistVideosPresenter(this);
        presenter.requestYoutubePlaylistVideos(playlist.getId());
        setupHeader(playlist);
    }

    private void setupHeader(Playlist playlist){
        tbPlaylist.setTitle(playlist.getTitle());
        if(null !=playlist.getThumbnail()) Glide.with(this).load(playlist.getThumbnail().getUrl()).into(ivPlaylistThumb);
    }

    private void setupList(List<Video> videos){
        PlaylistVideosAdapter adapter = new PlaylistVideosAdapter(this, videos);
        rvPlaylistVideos.setLayoutManager(new LinearLayoutManager(this));
        rvPlaylistVideos.setHasFixedSize(true);
        rvPlaylistVideos.setAdapter(adapter);

        adapter.setOnItemClickListener(new PlaylistVideosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Video obj) {
                VideoDetailActivity.navigate(PlaylistActivity.this, obj, playlist);
            }
        });
    }

}
