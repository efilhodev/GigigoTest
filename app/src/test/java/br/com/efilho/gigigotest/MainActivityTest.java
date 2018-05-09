package br.com.efilho.gigigotest;


import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.view.activities.MainActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( MainActivity.class )
                .create()
                .resume()
                .get();
    }
    @Test
    public void testResponseYoutubeChannelPlaylistsSuccessfully(){
        Assert.assertEquals("Unknown", activity.tvChannelTitle.getText().toString());
        Assert.assertNull(activity.rvChannelPlaylists.getAdapter());

        Channel channel = new Channel();
        channel.setId("Test");
        channel.setTitle("Test Title");

        Playlist playlist1 = new Playlist();
        Playlist playlist2 = new Playlist();
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist1);
        playlists.add(playlist2);

        channel.setPlaylists(playlists);

        activity.responseYoutubeChannelPlaylistsSuccessfully(channel);

        Assert.assertEquals(View.GONE, activity.ivConnectionLoading.getVisibility());
        Assert.assertEquals(View.GONE, activity.tvConnectionLoading.getVisibility());
        Assert.assertNotEquals("Unknown", activity.tvChannelTitle.getText().toString());
        Assert.assertEquals("Test Title", activity.tvChannelTitle.getText().toString());
        Assert.assertEquals(playlists.size(), activity.rvChannelPlaylists.getAdapter().getItemCount());
    }

    @Test
    public void testResponseYoutubeChannelPlaylistsFailed(){
        String mError = "Error Test Message";
        activity.responseYoutubeChannelPlaylistsFailed(mError);

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
