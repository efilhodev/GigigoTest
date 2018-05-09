package br.com.efilho.gigigotest.presenter;

import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.data.Constants;
import br.com.efilho.gigigotest.interfaces.ChannelPlaylistsMVP;
import br.com.efilho.gigigotest.model.ChannelPlaylistsModel;

public class ChannelPlayListPresenter implements ChannelPlaylistsMVP.Presenter {
    private ChannelPlaylistsMVP.Model model;
    private ChannelPlaylistsMVP.View  view;

    public ChannelPlayListPresenter(ChannelPlaylistsMVP.View view) {
        this.model = new ChannelPlaylistsModel(this);
        this.view  = view;
    }

    @Override
    public void requestYoutubeChannelPlaylists() {
        view.showListLoading(true);
        model.getYoutubeChannelPlaylists(Constants.CHANNEL_ID_MOCK);
    }

    @Override
    public void requestYoutubeChannelPlaylistsSuccessfully(Channel channel) {
        view.showListLoading(false);
        view.responseYoutubeChannelPlaylistsSuccessfully(channel);
    }

    @Override
    public void requestYoutubeChannelPlaylistsFailed(String mError) {
        view.showListLoading(false);
        view.responseYoutubeChannelPlaylistsFailed(mError);
    }
}
