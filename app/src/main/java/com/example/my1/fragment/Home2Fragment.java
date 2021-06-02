package com.example.my1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.my1.APIInterface;
import com.example.my1.R;
import com.example.my1.data.model.ProductListModel;
import com.example.my1.databinding.FragmentHome2Binding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home2Fragment extends Fragment {

    ProductListModel body;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRODUCT1 = "product_id";


    // TODO: Rename and change types of parameters
    private int mProduct_id;


    public Home2Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home2Fragment newInstance(int product_id) {
        Home2Fragment fragment = new Home2Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT1, product_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct_id = getArguments().getInt(ARG_PRODUCT1);

        }
    }
    private FragmentHome2Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHome2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getProductList();
        return view;

    }

    private void  getProductList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIInterface service = retrofit.create(APIInterface.class);
        Call<ProductListModel> call = service. getPostByIdProduct(mProduct_id);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {

                body = response.body();
                if(body != null ) {
                    binding.title.setText(body.getTitle());
                    binding.category.setText(body.getCategory());
                    binding.description.setText(body.getDescription());
                    Picasso.get().load(body.getImage())
                            .into(binding.image);
                    binding.price.setText(String.valueOf(body.getPrice()));
                }
            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {
                Toast.makeText(requireActivity(), "STATUS : failure",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}