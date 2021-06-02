package com.example.my1.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my1.R;
import com.example.my1.data.model.ProductListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
        implements Filterable {

    private List<ProductListModel> mList;
    private List<ProductListModel> mAllList;

    public CategoryAdapter(List<ProductListModel> list) {
        mList = list;
        mAllList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_recycler_latout, parent, false);

        return new CategoryAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        ProductListModel obj = mList.get(position);

        holder.select.setVisibility(View.GONE);
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

//    public void sort(String sort) {
//        if (sort.equalsIgnoreCase("asc")) {
//            mList.sort(new Comparator<ProductListModel>() {
//                @Override
//                public int compare(ProductListModel obj1, ProductListModel obj2) {
//                    return obj1.getPrice().compareTo(obj2.getPrice());
//                }
//            });
//        } else {
//            mList.sort(new Comparator<ProductListModel>() {
//                @Override
//                public int compare(ProductListModel obj1, ProductListModel obj2) {
//                    return obj2.getPrice().compareTo(obj1.getPrice());
//                }
//            });
//        }
//        notifyDataSetChanged();
//    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyword = constraint.toString();
                if (keyword.equals("Filter all Category")) {
                    mList = mAllList;

                } else {
                    List<ProductListModel> list = new ArrayList<>();
                    for (ProductListModel productModel : mAllList) {
                        if (productModel.getCategory().equalsIgnoreCase(keyword)) {
                            list.add(productModel);
                        }
                    }
                    mList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList = (List<ProductListModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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


        }
    }


}


