package br.com.efilho.gigigotest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Channel implements Parcelable {
    private String id;
    private String title;
    private List<Playlist> playlists;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", playlists=" + playlists +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeTypedList(this.playlists);
    }

    public Channel() {
    }

    protected Channel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.playlists = in.createTypedArrayList(Playlist.CREATOR);
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
