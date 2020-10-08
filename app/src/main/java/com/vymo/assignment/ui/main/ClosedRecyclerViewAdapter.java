package com.vymo.assignment.ui.main;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vymo.assignment.R;
import com.vymo.assignment.model.GitResponse;

import java.util.ArrayList;


public class ClosedRecyclerViewAdapter extends RecyclerView.Adapter<ClosedRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<GitResponse> mValues;

    public ClosedRecyclerViewAdapter(ArrayList<GitResponse> items) {
        mValues =  items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_closed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPullId.setText(String.valueOf(holder.mItem.getNumber()));
        holder.mContentView.setText(holder.mItem.getTitle());
        holder.mUser.setText(holder.mItem.getUser().getLogin());
        if (holder.mItem.getPull_request() != null) {
            if (  holder.mItem.getPull_request().getPatch_url() !=  null) {
                holder.mPullUrl.setText(holder.mItem.getPull_request().getPatch_url());
            } else {
                holder.mPullUrl.setText("Not Available");
            }
        } else {
            holder.mPullUrl.setText("Not Available");
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPullId;
        public final TextView mContentView;
        public final TextView mUser;
        public final TextView mPullUrl;
        public GitResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPullId = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.title);
            mUser = view.findViewById(R.id.user_name);
            mPullUrl = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}