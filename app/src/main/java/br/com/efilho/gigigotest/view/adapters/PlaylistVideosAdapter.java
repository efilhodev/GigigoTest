package br.com.efilho.gigigotest.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.efilho.gigigotest.R;
import br.com.efilho.gigigotest.bean.Video;
import br.com.efilho.gigigotest.utils.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistVideosAdapter extends RecyclerView.Adapter<PlaylistVideosAdapter.PlaylistVideosViewHolder> {
    private List<Video>videos;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Video obj);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public PlaylistVideosAdapter(Context context, List<Video> videos) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_youtube_video, parent, false);
        return new PlaylistVideosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistVideosViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class PlaylistVideosViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.root)
        View root;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.tv_video_description)
        TextView tvVideoDescription;
        @BindView(R.id.tv_video_published_at)
        TextView tvVideoPublishedAt;
        @BindView(R.id.iv_video_thumb)
        ImageView ivVideoThumb;


         PlaylistVideosViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Video video){
             root.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(null != mOnItemClickListener){
                         mOnItemClickListener.onItemClick(ivVideoThumb, video);
                     }
                 }
             });

            tvVideoTitle.setText(video.getTitle());
            tvVideoDescription.setText(video.getDesc());
            tvVideoPublishedAt.setText(DateUtil.convertDateToString(video.getPublishedAt(), DateUtil.APP_DATE_FORMAT));
            Glide.with(context).load(video.getThumbnail().getUrl()).into(ivVideoThumb);
        }
    }
}
