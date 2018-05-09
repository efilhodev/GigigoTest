package br.com.efilho.gigigotest.presenter;

import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.interfaces.PlaylistVideosMVP;
import br.com.efilho.gigigotest.model.PlaylistVideosModel;

public class PlaylistVideosPresenter implements PlaylistVideosMVP.Presenter {
    private PlaylistVideosMVP.Model model;
    private PlaylistVideosMVP.View  view;

    public PlaylistVideosPresenter(PlaylistVideosMVP.View view) {
        this.model = new PlaylistVideosModel(this);
        this.view  = view;
    }

    @Override
    public void requestYoutubePlaylistVideos(String playlistId) {
        view.showListLoading(true);
        model.getYoutubePlaylistVideos(playlistId);
    }

    @Override
    public void requestYoutubePlaylistVideosSuccessfully(Playlist playlist) {
        view.showListLoading(false);
        view.responseYoutubePlaylistVideosSuccessfully(playlist);
    }

    @Override
    public void requestYoutubePlaylistVideosFailed(String mError) {
        view.showListLoading(false);
        view.responseYoutubePlaylistVideosFailed(mError);
    }
}
