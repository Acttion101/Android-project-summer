package com.example.my1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.my1.APIInterface;
import com.example.my1.R;
import com.example.my1.RecyclerActivity;
import com.example.my1.RecyclerAdapter;
import com.example.my1.data.adapter.DiscountAdapter;
import com.example.my1.data.adapter.HomeAdapter;
import com.example.my1.data.model.ProductListModel;
import com.example.my1.databinding.FragmentDiscountBinding;
import com.example.my1.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscountFragment extends Fragment {
    DiscountAdapter discountAdapter;
    List<ProductListModel> body;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";

    // TODO: Rename and change types of parameters
    private String mTitle;


    public DiscountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscountFragment newInstance(String param1, String param2) {
        DiscountFragment fragment = new DiscountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);

        }
    }
    private FragmentDiscountBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getProductList();
        // Inflate the layout for this fragment
        return view;
    }

    private void getProductList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIInterface service = retrofit.create(APIInterface.class);
        Call<List<ProductListModel>> call = service.getAllProduct();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {

                body = response.body();
                if (body != null && body.size() != 0) {
                    body = random(body);
                    discountAdapter = new DiscountAdapter(body);
                    binding.recyclerview.setAdapter(discountAdapter);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(
                            requireActivity(), LinearLayoutManager.VERTICAL, false));

                    discountAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                            int position = viewHolder.getAdapterPosition();

                            Bundle bundle = new Bundle();
                            bundle.putInt("product_id", body.get(position).getId());
                            Navigation.findNavController(v).navigate(R.id.action_menu1_to_home2, bundle);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {
                Toast.makeText(requireActivity(), "STATUS : failure",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ProductListModel> random(List<ProductListModel> list) {
        Random random = new Random();
        List<ProductListModel> randomList = new ArrayList<>();

        int size = 5;
        if (list.size() < size) {
            size = list.size() - 1;
        }

        for (int i = 0; i < size; i++) {
            randomList.add(list.get(random.nextInt(list.size())));
        }
        return randomList;
    }
}