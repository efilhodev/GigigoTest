package br.com.efilho.gigigotest.model;

import android.support.annotation.NonNull;

import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.data.AppRestManager;
import br.com.efilho.gigigotest.data.Constants;
import br.com.efilho.gigigotest.interfaces.PlaylistVideosMVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistVideosModel implements PlaylistVideosMVP.Model {
    private PlaylistVideosMVP.Presenter presenter;

    public PlaylistVideosModel(PlaylistVideosMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getYoutubePlaylistVideos(String playlistId) {
        Call<Playlist> request = AppRestManager.getInstance()
                .getAppRestEndPoint().getYoutubePlaylistVideos(playlistId, Constants.API_KEY);

        request.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(@NonNull Call<Playlist> call,@NonNull Response<Playlist> response) {
                switch (response.code()){
                    case 200:
                        presenter.requestYoutubePlaylistVideosSuccessfully(response.body());
                        break;
                    default:
                        presenter.requestYoutubePlaylistVideosFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Playlist> call,@NonNull Throwable t) {
                presenter.requestYoutubePlaylistVideosFailed(t.getMessage());
            }
        });
    }
}
