package br.com.efilho.gigigotest;

import android.content.Intent;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.view.activities.VideoDetailActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class VideoDetailActivityTest {
    private VideoDetailActivity activity;

    @Before
    public void setUp() {
        Video video = new Video();
        video.setId("Test Video ID");
        video.setTitle("Test Video Title");
        video.setDesc("Test Video Desc");
        video.setLanguage("PT");
        video.setAudioLanguage("EN");

        Playlist playlist = new Playlist();
        playlist.setId("Test Playlist ID");
        playlist.setTitle("Test Playlist Title");
        playlist.setDesc("Test Playlist Desc");


        Intent intent = new Intent();
        intent.putExtra(Video.class.getSimpleName(), video);
        intent.putExtra(Playlist.class.getSimpleName(), playlist);
        activity = Robolectric.buildActivity(VideoDetailActivity.class, intent)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testNavigate(){
        VideoDetailActivity.navigate(activity, new Video(), new Playlist());

        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        Assert.assertEquals(VideoDetailActivity.class.getCanonicalName(), Objects.requireNonNull(intent.getComponent()).getClassName());
        Assert.assertTrue(intent.hasExtra(Video.class.getSimpleName()));
        Assert.assertTrue(intent.hasExtra(Playlist.class.getSimpleName()));
    }

    @Test
    public void testInitHeader(){
        Assert.assertEquals(activity.tvVideoTitle.getText().toString(), "Test Video Title");
        Assert.assertEquals(activity.tvVideoDescription.getText().toString(), "Test Video Desc");
        Assert.assertEquals(activity.tvVideoAudioLanguage.getText().toString(), "EN");
        Assert.assertEquals(activity.tvVideoSubtitleLanguage.getText().toString(), "PT");
    }

    @Test
    public void testResponseYoutubeVideoDetailSuccessfully(){

        Video video = new Video();
        video.setTitle("Test Video Title 2");
        video.setDesc("Test Video Desc 2");
        video.setLanguage("PT 2");
        video.setAudioLanguage("EN 2");

        activity.responseYoutubeVideoDetailSuccessfully(video);

        Assert.assertEquals(activity.tvVideoTitle.getText().toString(), "Test Video Title 2");
        Assert.assertEquals(activity.tvVideoDescription.getText().toString(), "Test Video Desc 2");
        Assert.assertEquals(activity.tvVideoAudioLanguage.getText().toString(), "EN 2");
        Assert.assertEquals(activity.tvVideoSubtitleLanguage.getText().toString(), "PT 2");
    }

    @Test
    public void testResponseYoutubeListVideosSuccessfully(){
        Playlist playlist = new Playlist();

        Video video1 = new Video();
        Video video2 = new Video();

        List<Video> videos = new ArrayList<>();

        videos.add(video1);
        videos.add(video2);

        playlist.setVideos(videos);

        activity.responseYoutubePlaylistVideosSuccessfully(playlist);

        Assert.assertEquals(View.GONE, activity.ivConnectionLoading.getVisibility());
        Assert.assertEquals(View.GONE, activity.tvConnectionLoading.getVisibility());
        Assert.assertEquals(videos.size(), activity.rvPlaylistVideos.getAdapter().getItemCount());
    }

    @Test
    public void testResponseYoutubeChannelPlaylistsFailed(){
        String mError = "Error Test Message";
        activity.responseYoutubePlaylistVideosFailed(mError);

        Assert.assertEquals(View.VISIBLE, activity.ivConnectionLoading.getVisibility());
        Assert.assertEquals(View.VISIBLE, activity.tvConnectionLoading.getVisibility());
        Assert.assertEquals(activity.getResources().getString(R.string.err_connection),
                activity.tvConnectionLoading.getText().toString());
    }

    @Test
    public void testShowListLoading(){
        activity.showListLoading(true);
        Assert.assertEquals(View.VISIBLE, activity.pbConnectionLoading.getVisibility());
        Assert.assertNotEquals(View.INVISIBLE, activity.pbConnectionLoading.getVisibility());

        activity.showListLoading(false);
        Assert.assertEquals(View.GONE, activity.pbConnectionLoading.getVisibility());
        Assert.assertNotEquals(View.INVISIBLE, activity.pbConnectionLoading.getVisibility());
    }
}
