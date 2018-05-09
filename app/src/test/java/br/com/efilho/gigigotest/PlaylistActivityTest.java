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
import br.com.efilho.gigigotest.view.activities.PlaylistActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PlaylistActivityTest {
    private PlaylistActivity activity;

    @Before
    public void setUp() {
        Playlist playlist = new Playlist();
        playlist.setTitle("Test Title");

        Intent intent = new Intent();
        intent.putExtra(Playlist.class.getSimpleName(), playlist);
        activity = Robolectric.buildActivity(PlaylistActivity.class, intent)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testNavigate(){
        PlaylistActivity.navigate(activity, new Playlist());

        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        Assert.assertEquals(PlaylistActivity.class.getCanonicalName(), Objects.requireNonNull(intent.getComponent()).getClassName());
        Assert.assertTrue(intent.hasExtra(Playlist.class.getSimpleName()));
    }

    @Test
    public void testSetupHeader(){
        Assert.assertEquals(activity.tbPlaylist.getTitle(), "Test Title");
        Assert.assertNotEquals(activity.tbPlaylist.getTitle(), "Unknown");
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
