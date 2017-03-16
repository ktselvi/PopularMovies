package app.p1.udacity.com.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.R;
import app.p1.udacity.com.popularmovies.datamodels.TrailerItem;

/**
 * Created by HOME on 11-06-2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<TrailerItem> trailers;
    private final Context context;

    public TrailerAdapter(Context context, List<TrailerItem> data) {
        trailers = new ArrayList<>(data);
        this.context = context;
    }

    public void addData(List<TrailerItem> trailersData) {
        trailers.addAll(trailersData);
        notifyDataSetChanged();
    }

    public void clear() {
        trailers.clear();
        notifyDataSetChanged();
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TrailerItem trailer = trailers.get(position);
        if(trailer.getType().equalsIgnoreCase("Trailer")) {
            String baseimagepath = "https://img.youtube.com/vi/";
            String videoid = trailer.getKey();
            String name = "/0.jpg";

            Picasso.with(context).load(baseimagepath+videoid+name).into(holder.thumbnail);
            holder.playbutton.setImageResource(R.drawable.playbutton);

            holder.playbutton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    final String VIDEO_URL = "https://www.youtube.com/watch?v="+trailer.getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URL));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView thumbnail;
        public ImageView playbutton;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.trailer_image);
            playbutton = (ImageView) itemView.findViewById(R.id.play_button);
        }
    }
}

