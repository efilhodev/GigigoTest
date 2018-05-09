package br.com.efilho.gigigotest.data.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Thumbnail;
import br.com.efilho.gigigotest.utils.DateUtil;

public class ChannelDeserializer implements JsonDeserializer<Channel> {
    @Override
    public Channel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Channel channel = null;
        List<Playlist> playlists = new ArrayList<>();

        JsonArray array = json.getAsJsonObject().get("items").getAsJsonArray();
        for(JsonElement element : array){

            Playlist playlist = new Playlist();
            playlist.setId(element.getAsJsonObject().get("id").getAsString());
            playlist.setTitle(element.getAsJsonObject().get("snippet").getAsJsonObject().get("title").getAsString());
            playlist.setDesc(element.getAsJsonObject().get("snippet").getAsJsonObject().get("description").getAsString());

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

            playlist.setThumbnail(thumbnail);
            playlist.setPublishedAt(DateUtil.convertStringToDate(element.getAsJsonObject().get("snippet")
                    .getAsJsonObject().get("publishedAt").getAsString(), DateUtil.API_DATE_FORMAT));
            playlists.add(playlist);

            if(null == channel){
                channel = new Channel();
                channel.setId(element.getAsJsonObject().get("snippet").getAsJsonObject().get("channelId").getAsString());
                channel.setTitle(element.getAsJsonObject().get("snippet").getAsJsonObject().get("channelTitle").getAsString());
            }
        }
        if(null != channel && !playlists.isEmpty()){
            channel.setPlaylists(playlists);
        }


        return channel;
    }
}
