package com.example.practice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String mFileName = "file.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getFilesDir() + "/" + mFileName);
        if (file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MainUIFragment mainUIFragment = new MainUIFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // BackStackを設定
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container,mainUIFragment);
        fragmentTransaction.commit();
    }

}
