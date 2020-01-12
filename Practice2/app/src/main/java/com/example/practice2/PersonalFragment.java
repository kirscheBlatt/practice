package com.example.practice2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class PersonalFragment extends Fragment {


    public PersonalFragment() {

    }


    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    private EditText editText = null;
    private ImageView imageView;
    private Button selectButton;
    private static final int RESULT_PICK_IMAGEFILE = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, container, false);

        imageView = v.findViewById(R.id.image);
        selectButton = v.findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,RESULT_PICK_IMAGEFILE);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_PICK_IMAGEFILE && requestCode == RESULT_OK){
            Uri uri = null;
            if (data != null){
                uri = data.getData();
                
                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    imageView.setImageBitmap(bmp);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException{
        ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
