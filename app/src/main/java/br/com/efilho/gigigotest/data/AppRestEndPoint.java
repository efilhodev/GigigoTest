package br.com.efilho.gigigotest.data;

import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Video;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * Interface para os métodos REST de comunição com o servidor da aplicação.
 */

public interface AppRestEndPoint {
    @GET("playlists?part=snippet")
    Call<Channel> getYoutubeChannelPlaylists(@Query("channelId") String channelId, @Query("key") String apiKey);

    @GET("playlistItems?part=snippet")
    Call<Playlist> getYoutubePlaylistVideos(@Query("playlistId") String playListId, @Query("key") String apiKey);

    @GET("videos?part=snippet")
    Call<Video> getYoutubeVideoDetail(@Query("id") String videoId, @Query("key") String apiKey);
}
