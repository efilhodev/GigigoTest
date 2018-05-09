package br.com.efilho.gigigotest.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.efilho.gigigotest.R;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.interfaces.PlaylistVideosMVP;
import br.com.efilho.gigigotest.interfaces.VideoDetailMVP;
import br.com.efilho.gigigotest.presenter.PlaylistVideosPresenter;
import br.com.efilho.gigigotest.presenter.VideoDetailPresenter;
import br.com.efilho.gigigotest.view.adapters.PlaylistVideosAdapter;
import butterknife.BindView;

public class VideoDetailActivity extends BaseNetworkActivity implements VideoDetailMVP.View , PlaylistVideosMVP.View{
    @BindView(R.id.iv_video_detail_thumb)
    public ImageView ivVideoThumb;
    @BindView(R.id.tv_video_detail_title)
    public TextView tvVideoTitle;
    @BindView(R.id.tv_video_detail_description)
    public TextView tvVideoDescription;
    @BindView(R.id.tv_video_audio_language)
    public TextView tvVideoAudioLanguage;
    @BindView(R.id.tv_video_subtitle_language)
    public TextView tvVideoSubtitleLanguage;
    @BindView(R.id.rv_playlist_videos)
    public RecyclerView rvPlaylistVideos;

    private VideoDetailMVP.Presenter vdPresenter;
    private PlaylistVideosMVP.Presenter pvPresenter;
    private Video video;
    private Playlist playlist;

    public static void navigate(Activity activity, Video video, Playlist playlist){
        Intent intent = new Intent(activity, VideoDetailActivity.class);
        intent.putExtra(Video.class.getSimpleName(), video);
        intent.putExtra(Playlist.class.getSimpleName(), playlist);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        init();
    }

    @Override
    public void responseYoutubeVideoDetailSuccessfully(Video video) {
        if(null != video) setupHeader(video);
    }

    @Override
    public void responseYoutubeVideoDetailFailed(String mError) {
        showToast(mError, Toast.LENGTH_LONG);
    }

    @Override
    public void responseYoutubePlaylistVideosSuccessfully(Playlist playlist) {
        showConnectionErrorLayout(false, null);
        if(null != playlist) this.playlist = playlist;
        if(!this.playlist.getVideos().isEmpty())  setupList(this.playlist.getVideos());
    }

    @Override
    public void responseYoutubePlaylistVideosFailed(String mError) {
        showConnectionErrorLayout(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvPresenter.requestYoutubePlaylistVideos(playlist.getId());
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
        video = getIntent().getParcelableExtra(Video.class.getSimpleName());
        playlist = getIntent().getParcelableExtra(Playlist.class.getSimpleName());
        if(null == vdPresenter) vdPresenter = new VideoDetailPresenter(this);
        if(null == pvPresenter) pvPresenter = new PlaylistVideosPresenter(this);
        pvPresenter.requestYoutubePlaylistVideos(playlist.getId());
        vdPresenter.requestYoutubeVideoDetail(video.getId());
        setupHeader(video);
    }

    private void setupHeader(Video video){
        tvVideoTitle.setText(video.getTitle());
        tvVideoDescription.setText(video.getDesc());
        if(null != video.getAudioLanguage()) tvVideoAudioLanguage.setText(video.getAudioLanguage());
        if(null != video.getLanguage()) tvVideoSubtitleLanguage.setText(video.getLanguage());
        if(null != video.getThumbnail()) Glide.with(this).load(video.getThumbnail().getUrl()).into(ivVideoThumb);
    }

    private void setupList(List<Video> videos){
        videos.remove(video);
        PlaylistVideosAdapter adapter = new PlaylistVideosAdapter(this, videos);
        rvPlaylistVideos.setLayoutManager(new LinearLayoutManager(this));
        rvPlaylistVideos.setHasFixedSize(true);
        rvPlaylistVideos.setAdapter(adapter);

        adapter.setOnItemClickListener(new PlaylistVideosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Video obj) {
                VideoDetailActivity.navigate(VideoDetailActivity.this, obj, playlist);
            }
        });
    }
}
