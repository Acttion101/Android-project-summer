package com.example.my1.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my1.R;
import com.example.my1.data.model.ProductListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter2 extends  RecyclerView.Adapter<RecyclerAdapter2.ViewHolder>{
    private View.OnClickListener onClickListener;
    private List<ProductListModel> mList;
    public RecyclerAdapter2(List<ProductListModel> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_recycler_layout2, parent, false);

        return new RecyclerAdapter2.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter2.ViewHolder holder, int position) {
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

    public void removeFromCart(int position){
        mList.remove(mList.get(position)) ;
        notifyDataSetChanged();
    }

    public  List<ProductListModel> getAddToCartList(){ return  mList;}



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title , price, description, catagory ;
        ImageView image ;
        Button select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            catagory = itemView.findViewById(R.id.category);
            image = itemView.findViewById(R.id.image);
            select= itemView.findViewById(R.id.select);

            select.setTag(this);
            select.setOnClickListener(onClickListener);


        }
    }

    public void  setOnClickListener(View.OnClickListener OnClickListener){
        this.onClickListener = OnClickListener;
    }

}
