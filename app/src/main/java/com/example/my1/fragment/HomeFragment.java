package com.example.my1.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my1.APIInterface;
import com.example.my1.CategoryActivity;
import com.example.my1.R;
import com.example.my1.RecyclerActivity;
import com.example.my1.data.adapter.HomeAdapter;
import com.example.my1.data.adapter.ViewPagerAdapter;
import com.example.my1.data.model.ProductListModel;
import com.example.my1.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    HomeAdapter homeAdapter;
    List<ProductListModel> body;
    int currentPage = 0;
    private TextView[] dots;
    int colorsActive;
    int colorInactive;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";


    // TODO: Rename and change types of parameters
    private String mTitle;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        List<String> list = new ArrayList<>();
        list.add("https://www.avtechguide.com/wp-content/uploads/2020/10/lazada-11-11-promotion-brand-ambassador_01-800x445.jpg");
        list.add("https://laz-img-cdn.alicdn.com/images/ims-web/TB1ATeVfET1gK0jSZFhXXaAtVXa.jpg_1200x1200q75.jpg_.webp");
        list.add("https://kasikornbank.com/th/promotion/credit-card/shopping/publishingimages/lazadamidyear11062020_2280x980.jpg");
        list.add("https://www.matichon.co.th/wp-content/uploads/2020/11/OPPO_11.11-Promotion-1.jpg");

        ViewPagerAdapter mAdapter = new ViewPagerAdapter(list);
        binding.viewPager.setAdapter(mAdapter);


        final int NUM_PAGES = list.size();

        Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        binding.viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        },100,5000);

        colorInactive = requireActivity().getColor(R.color.white);
        colorInactive = requireActivity().getColor(R.color.purple_200);
        addBottomDots(currentPage, NUM_PAGES);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeBottomDOts(position);
            }
        });



        getProductList();
        binding.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecyclerActivity.class));
            }
        });

        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CategoryActivity.class));
            }
        });
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
                    homeAdapter = new HomeAdapter(body);
                    binding.recyclerview.setAdapter(homeAdapter);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(
                            requireActivity(), LinearLayoutManager.HORIZONTAL, false));

                    homeAdapter.setOnClickListener(new View.OnClickListener() {
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

    private void addBottomDots(int currentPage,int viewPageSize){
        dots = new TextView[viewPageSize];
        binding.layoutDots.removeAllViews();
        for (int i=0;i <dots.length; i++){
            dots[i] = new TextView(requireActivity());
            dots[i].setText(Html.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive);
            binding.layoutDots.addView(dots[i]);
        }
        if(dots.length > 0)
            dots[currentPage].setTextColor(colorsActive);
    }

    private void changeBottomDOts(int currentPage){
        for (int i =0; i< dots.length; i++){
            if(i == currentPage){
                dots[i].setTextColor(colorsActive);
            }else {
                dots[i].setTextColor(colorInactive);
            }
        }
    }
}