package br.com.efilho.gigigotest.interfaces;

import br.com.efilho.gigigotest.bean.Channel;

public interface ChannelPlaylistsMVP {
    interface Model{
        void getYoutubeChannelPlaylists(String channelId);
    }
    interface Presenter{
        void requestYoutubeChannelPlaylists();
        void requestYoutubeChannelPlaylistsSuccessfully(Channel channel);
        void requestYoutubeChannelPlaylistsFailed(String mError);
    }
    interface View{
        void responseYoutubeChannelPlaylistsSuccessfully(Channel channel);
        void responseYoutubeChannelPlaylistsFailed(String mError);
        void showListLoading(boolean isVisible);
    }
}
