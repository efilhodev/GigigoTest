package br.com.efilho.gigigotest.model;

import android.support.annotation.NonNull;

import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.data.AppRestManager;
import br.com.efilho.gigigotest.data.Constants;
import br.com.efilho.gigigotest.interfaces.ChannelPlaylistsMVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelPlaylistsModel implements ChannelPlaylistsMVP.Model {
    private ChannelPlaylistsMVP.Presenter presenter;

    public ChannelPlaylistsModel(ChannelPlaylistsMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getYoutubeChannelPlaylists(String channelId) {
        Call<Channel> request = AppRestManager.getInstance()
                .getAppRestEndPoint().getYoutubeChannelPlaylists(channelId, Constants.API_KEY);

        request.enqueue(new Callback<Channel>() {
            @Override
            public void onResponse(@NonNull Call<Channel> call, @NonNull Response<Channel> response) {
                switch (response.code()){
                    case 200:
                        presenter.requestYoutubeChannelPlaylistsSuccessfully(response.body());
                        break;
                    default:
                        presenter.requestYoutubeChannelPlaylistsFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Channel> call, @NonNull Throwable t) {
                presenter.requestYoutubeChannelPlaylistsFailed(t.getMessage());
            }
        });
    }
}
