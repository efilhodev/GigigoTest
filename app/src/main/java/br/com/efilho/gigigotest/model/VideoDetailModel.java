package br.com.efilho.gigigotest.model;

import android.support.annotation.NonNull;

import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.data.AppRestManager;
import br.com.efilho.gigigotest.data.Constants;
import br.com.efilho.gigigotest.interfaces.VideoDetailMVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDetailModel implements VideoDetailMVP.Model {
    private VideoDetailMVP.Presenter presenter;

    public VideoDetailModel(VideoDetailMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getYoutubeVideoDetail(String videoId) {
        Call<Video> request = AppRestManager.getInstance().getAppRestEndPoint()
                .getYoutubeVideoDetail(videoId, Constants.API_KEY);

        request.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(@NonNull Call<Video> call, @NonNull Response<Video> response) {
                switch (response.code()){
                    case 200:
                        presenter.requestYoutubeVideoDetailSuccessfully(response.body());
                        break;
                    default:
                        presenter.requestYoutubeVideoDetailFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Video> call, @NonNull Throwable t) {
                presenter.requestYoutubeVideoDetailFailed(t.getMessage());
            }
        });
    }
}
