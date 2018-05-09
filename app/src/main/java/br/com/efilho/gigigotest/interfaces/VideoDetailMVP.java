package br.com.efilho.gigigotest.interfaces;

import br.com.efilho.gigigotest.bean.Video;

public interface VideoDetailMVP {
    interface Model{
        void getYoutubeVideoDetail(String videoId);
    }
    interface Presenter{
        void requestYoutubeVideoDetail(String videoId);
        void requestYoutubeVideoDetailSuccessfully(Video video);
        void requestYoutubeVideoDetailFailed(String mError);
    }
    interface View{
        void responseYoutubeVideoDetailSuccessfully(Video video);
        void responseYoutubeVideoDetailFailed(String mError);
    }
}
