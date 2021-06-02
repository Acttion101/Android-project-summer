package com.example.my1.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.LocaleList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my1.EditProfileActivity;
import com.example.my1.LoginActivity;
import com.example.my1.MainActivity;
import com.example.my1.R;
import com.example.my1.RegisterActivity;
import com.example.my1.databinding.FragmentHome2Binding;
import com.example.my1.databinding.FragmentSettingBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";


    // TODO: Rename and change types of parameters
    private String mTitle;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
    FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));

            }
        });
        getCurrentLocale();
        binding.toggleLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.toggleLang.isChecked()){
                    setLocale("th");
                }else {
                    setLocale("en");
                }
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (requireActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
               requireActivity().finish();
            }
        });
        return view;



    }
    private void getCurrentLocale(){
        LocaleList current = getResources().getConfiguration().getLocales();
        Locale locale = current.get(0);
        String lang = locale.getCountry();
        if (lang.equalsIgnoreCase("th")){
            binding.toggleLang.setChecked(true);
        }else {
            binding.toggleLang.setChecked(false);
        }
    }
    private void setLocale(String lang){
        Locale myLocale = new Locale(lang);
        getResources().getConfiguration().setLocale(myLocale);

        Resources res = requireActivity().getResources();
        Configuration config = new Configuration(res.getConfiguration());
        requireActivity().getResources().updateConfiguration(config,requireActivity().getResources().getDisplayMetrics());
        binding.setting.setText(R.string.setting);
        binding.btnEditProfile.setText(R.string.editProfile);
        binding.lang.setText(R.string.language);
        binding.btnLogout.setText(R.string.logout);
    }


}