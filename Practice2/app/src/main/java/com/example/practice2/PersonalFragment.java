package com.example.practice2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class PersonalFragment extends Fragment {


    public PersonalFragment() {

    }


    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    private EditText editText = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, container, false);


        editText = v.findViewById(R.id.writeName);
        Button button = v.findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editText.getText().toString();
                JSONObject jsonObject = createJSON();
 //               new CachePref().put("name",jsonObject.toString());

                File file = new File("c:¥¥tmp¥¥test.txt");
                try {
                    FileWriter filewriter = new FileWriter(file);

                    filewriter.write(jsonObject.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showMainUIFragment();
            }
        });

        return v;
    }

    private void showMainUIFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, MainUIFragment.newInstance());
        fragmentTransaction.commit();
    }

    private JSONObject createJSON(){
       JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",editText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
