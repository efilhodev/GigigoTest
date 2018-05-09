package br.com.efilho.gigigotest.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.efilho.gigigotest.bean.Channel;
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.data.deserializers.ChannelDeserializer;
import br.com.efilho.gigigotest.data.deserializers.PlaylistDeserializer;
import br.com.efilho.gigigotest.data.deserializers.VideoDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe responsável pela instancia e gerenciamento do retrofit
 */

public class AppRestManager {
    private static final AppRestManager ourInstance = new AppRestManager();
    private Retrofit retrofit;

    public static AppRestManager getInstance(){
        return ourInstance;
    }

    private AppRestManager(){
        if(null == retrofit){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGsonConfig()))
                    .build();
        }
    }

    public AppRestEndPoint getAppRestEndPoint(){
        return retrofit.create(AppRestEndPoint.class);
    }

    private Gson getGsonConfig(){
        return  new GsonBuilder()
                .registerTypeAdapter(Channel.class, new ChannelDeserializer())
                .registerTypeAdapter(Playlist.class, new PlaylistDeserializer())
                .registerTypeAdapter(Video.class, new VideoDeserializer())
                .create();
    }
}
