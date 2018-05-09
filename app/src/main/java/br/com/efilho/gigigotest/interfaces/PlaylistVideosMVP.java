package br.com.efilho.gigigotest.interfaces;

import br.com.efilho.gigigotest.bean.Playlist;

public interface PlaylistVideosMVP {
    interface Model{
        void getYoutubePlaylistVideos(String playlistId);
    }
    interface Presenter{
        void requestYoutubePlaylistVideos(String playlistId);
        void requestYoutubePlaylistVideosSuccessfully(Playlist playlist);
        void requestYoutubePlaylistVideosFailed(String mError);
    }
    interface View{
        void responseYoutubePlaylistVideosSuccessfully(Playlist playlist);
        void responseYoutubePlaylistVideosFailed(String mError);
        void showListLoading(boolean visible);
    }
}
