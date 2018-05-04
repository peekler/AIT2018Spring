package hu.ait.android.aitforum.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.aitforum.R;
import hu.ait.android.aitforum.data.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> postList;
    private List<String> postKeys;
    private String uId;
    private int lastPosition = -1;

    public PostsAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.postList = new ArrayList<Post>();
        this.postKeys = new ArrayList<String>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_post, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvAuthor.setText(
                postList.get(holder.getAdapterPosition()).getAuthor());
        holder.tvTitle.setText(
                postList.get(holder.getAdapterPosition()).getTitle());
        holder.tvBody.setText(
                postList.get(holder.getAdapterPosition()).getBody());


        if (postList.get(holder.getAdapterPosition()).getUid().equals(uId)) {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removePost(holder.getAdapterPosition());
                }
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(postList.get(holder.getAdapterPosition()).getImageUrl())) {
            Glide.with(context).load(
                    postList.get(holder.getAdapterPosition()).getImageUrl()
            ).into(holder.ivImage);
            holder.ivImage.setVisibility(View.VISIBLE);
        } else {
            holder.ivImage.setVisibility(View.GONE);
        }

        setAnimation(holder.itemView, position);
    }

    public void removePost(int index) {
        FirebaseDatabase.getInstance().getReference("posts").child(
                postKeys.get(index)).removeValue();

        postList.remove(index);
        postKeys.remove(index);
        notifyItemRemoved(index);
    }

    public void removePostByKey(String key) {
        int index = postKeys.indexOf(key);
        if (index != -1) {
            postList.remove(index);
            postKeys.remove(index);
            notifyItemRemoved(index);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addPost(Post newPost, String key) {
        postList.add(newPost);
        postKeys.add(key);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvAuthor;
        public TextView tvTitle;
        public TextView tvBody;
        public Button btnDelete;
        public ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            btnDelete = itemView.findViewById(R.id.btnDel);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }

}
