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
import br.com.efilho.gigigotest.bean.Playlist;
import br.com.efilho.gigigotest.utils.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelPlaylistsAdapter extends RecyclerView.Adapter<ChannelPlaylistsAdapter.ChannelPlaylistsViewHolder>{
    private List<Playlist> playlists;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Playlist obj);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ChannelPlaylistsAdapter(Context context, List<Playlist> playlists) {
        this.playlists = playlists;
        this.context = context;
    }

    @NonNull
    @Override
    public ChannelPlaylistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_youtube_playlists, parent, false);
        return new ChannelPlaylistsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelPlaylistsViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.bind(playlist);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    class ChannelPlaylistsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.root)
        View root;
        @BindView(R.id.tv_playlists_title)
        TextView tvPlaylistsTitle;
        @BindView(R.id.tv_playlists_description)
        TextView tvPlaylistsDescription;
        @BindView(R.id.tv_playlists_published_at)
        TextView tvPlaylistsPublishedAt;
        @BindView(R.id.iv_playlists_thumb)
        ImageView ivPlaylistsThumb;


        ChannelPlaylistsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Playlist playlist){
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null != mOnItemClickListener){
                        mOnItemClickListener.onItemClick(ivPlaylistsThumb, playlist);
                    }
                }
            });

            tvPlaylistsTitle.setText(playlist.getTitle());
            tvPlaylistsDescription.setText(playlist.getDesc());
            tvPlaylistsPublishedAt.setText(DateUtil.convertDateToString(playlist.getPublishedAt(), DateUtil.APP_DATE_FORMAT));
            Glide.with(context).load(playlist.getThumbnail().getUrl()).into(ivPlaylistsThumb);
        }
    }
}
