package com.example.my1;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.my1.databinding.ActivityLoginBinding;
import com.example.my1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavController navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment);
        int backStackEntryCount = navHostFragment.getChildFragmentManager().getBackStackEntryCount();

        if(backStackEntryCount == 0){
            confirmLogout();
        }else {
            super.onBackPressed();
        }
    }
    private void confirmLogout(){
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("Do you want to exit an application")
                .setPositiveButton("Exit",((dialog1, which) -> {
                    Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }))

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}