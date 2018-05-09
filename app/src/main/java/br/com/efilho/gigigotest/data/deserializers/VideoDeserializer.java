package br.com.efilho.gigigotest.data.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.efilho.gigigotest.bean.Thumbnail;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.utils.DateUtil;

public class VideoDeserializer implements JsonDeserializer<Video> {

    @Override
    public Video deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Video video = null;
        JsonArray array = json.getAsJsonObject().get("items").getAsJsonArray();
        for(JsonElement element : array){

            video = new Video();
            video.setId(element.getAsJsonObject().get("id").getAsString());
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

            if(element.getAsJsonObject().get("snippet").getAsJsonObject().has("defaultLanguage")){
                video.setLanguage(element.getAsJsonObject().get("snippet").getAsJsonObject().get("defaultLanguage").getAsString());
            }

            video.setAudioLanguage(element.getAsJsonObject().get("snippet").getAsJsonObject().get("defaultAudioLanguage").getAsString());
        }

        return video;
    }
}
