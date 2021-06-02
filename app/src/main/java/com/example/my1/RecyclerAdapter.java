package com.example.my1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my1.data.CircleTransform;
import com.example.my1.data.model.ProductListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private View.OnClickListener onClickListener;
    private List<ProductListModel> mList;

    public RecyclerAdapter(List<ProductListModel> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_recycler_latout, parent, false);

        return new RecyclerAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        ProductListModel obj = mList.get(position);

        holder.title.setText(obj.getTitle());
        holder.price.setText(String.valueOf(obj.getPrice()));
        holder.description.setText(obj.getDescription());
        holder.catagory.setText(obj.getCategory());
        Picasso.get().load(obj.getImage())

                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        TextView txt_heading, txt_detail;
        TextView title, price, description, catagory;
        ImageView image;
        Button select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            catagory = itemView.findViewById(R.id.category);
            image = itemView.findViewById(R.id.image);
            select = itemView.findViewById(R.id.select);

            select.setTag(this);
            select.setOnClickListener(onClickListener);

//            itemView.setTag(this);
//            itemView.setOnClickListener(onClickListener);
//            txt_heading =itemView.findViewById(R.id.heading);
//            txt_detail =itemView.findViewById(R.id.detail);
//
//            txt_heading.setTag(this);
//            txt_heading.setOnClickListener(onClickListener);

        }
    }

    public void setOnClickListener(View.OnClickListener OnClickListener) {
        this.onClickListener = OnClickListener;
    }

    private List<ProductListModel> mSelectedList = new ArrayList<>();

    public boolean addToCart(int position) {
        boolean isAdded = false;
        for (ProductListModel obj : mSelectedList) {
            if (mList.get(position).equals(obj)) {
                isAdded = true;
                break;
            }
        }

        if (isAdded) {
            mSelectedList.remove(mList.get(position));
        } else {
            mSelectedList.add(mList.get(position));
        }
        return isAdded;
    }

    public List<ProductListModel> getAddToCardList() {
        return mSelectedList;
    }

    public void updateCart(List<ProductListModel> currentList) {
        mSelectedList = currentList;
        notifyDataSetChanged();
    }

    public void updateList(List<ProductListModel> currentList) {
        mList = currentList;
        notifyDataSetChanged();

    }

}