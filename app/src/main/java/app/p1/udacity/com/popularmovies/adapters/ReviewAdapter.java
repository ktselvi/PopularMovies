package app.p1.udacity.com.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.R;
import app.p1.udacity.com.popularmovies.datamodels.ReviewItem;

/**
 * Created by HOME on 30-04-2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewItem> reviewsdata;

    public ReviewAdapter(List<ReviewItem> data) {
        reviewsdata = data;
    }

    public void addData(List<ReviewItem> reviews) {
        reviewsdata.addAll(reviews);
        notifyDataSetChanged();
    }

    public void clear() {
        reviewsdata.clear();
        notifyDataSetChanged();
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ReviewItem review = reviewsdata.get(position);
        holder.authorholder.setText(review.getAuthor());
        holder.contentholder.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView authorholder;
        public TextView contentholder;

        public ViewHolder(View itemView) {
            super(itemView);
            authorholder = (TextView) itemView.findViewById(R.id.author_name);
            contentholder = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
