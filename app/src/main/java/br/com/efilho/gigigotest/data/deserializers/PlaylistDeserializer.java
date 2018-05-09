package br.com.efilho.gigigotest.data.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Thumbnail;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.utils.DateUtil;

public class PlaylistDeserializer implements JsonDeserializer<Playlist> {
    @Override
    public Playlist deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Playlist playlist = null;
        List<Video> videos = new ArrayList<>();

        JsonArray array = json.getAsJsonObject().get("items").getAsJsonArray();
        for(JsonElement element : array){

            Video video = new Video();
            video.setId(element.getAsJsonObject().get("snippet").getAsJsonObject().get("resourceId")
                                                 .getAsJsonObject().get("videoId").getAsString());
            video.setTitle(element.getAsJsonObject().get("snippet").getAsJsonObject().get("title").getAsString());
            video.setDesc(element.getAsJsonObject().get("snippet").getAsJsonObject().get("description").getAsString());

            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl(element.getAsJsonObject().get("snippet")
                    .getAsJsonObject().get("thumbnails").getAsJsonObject().get("high")
                    .getAsJsonObject().get("url").getAsString());

            thumbnail.setWidth(element.getAsJsonObject().get("snippet")
                    .getAsJsonObject().get("thumbnails").getAsJsonObject().get("high")
                    .getAsJsonObject().get("width").getAsInt());

            thumbnail.setHeight(element.getAsJsonObject().get("snippet")
                    .getAsJsonObject().get("thumbnails").getAsJsonObject().get("high")
                    .getAsJsonObject().get("height").getAsInt());

            video.setThumbnail(thumbnail);
            video.setPublishedAt(DateUtil.convertStringToDate(element.getAsJsonObject().get("snippet")
                    .getAsJsonObject().get("publishedAt").getAsString(), DateUtil.API_DATE_FORMAT));
            videos.add(video);

            if(null == playlist){
                playlist = new Playlist();
                playlist.setId(element.getAsJsonObject().get("snippet").getAsJsonObject().get("playlistId").getAsString());
            }
        }
        if(null != playlist && !videos.isEmpty()){
            playlist.setVideos(videos);
        }

        return playlist;
    }
}
