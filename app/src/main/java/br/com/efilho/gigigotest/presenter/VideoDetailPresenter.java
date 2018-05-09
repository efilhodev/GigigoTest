package br.com.efilho.gigigotest.presenter;


import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.interfaces.VideoDetailMVP;
import br.com.efilho.gigigotest.model.VideoDetailModel;

public class VideoDetailPresenter implements VideoDetailMVP.Presenter{
    private VideoDetailMVP.Model model;
    private VideoDetailMVP.View  view;

    public VideoDetailPresenter(VideoDetailMVP.View view) {
        this.model = new VideoDetailModel(this);
        this.view  = view;
    }

    @Override
    public void requestYoutubeVideoDetail(String videoId) {
        model.getYoutubeVideoDetail(videoId);
    }

    @Override
    public void requestYoutubeVideoDetailSuccessfully(Video video) {
        view.responseYoutubeVideoDetailSuccessfully(video);

    }

    @Override
    public void requestYoutubeVideoDetailFailed(String mError) {
        view.responseYoutubeVideoDetailFailed(mError);
    }
}
