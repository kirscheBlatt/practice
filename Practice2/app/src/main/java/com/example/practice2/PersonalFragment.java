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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class PersonalFragment extends Fragment {

    public PersonalFragment() {
    }


    List<Map<String, Object>> List = new ArrayList<>();
    private String fileName = "file.txt";


    private static PersonalFragment newInstance() {
        PersonalFragment personalfragment = new PersonalFragment();
        return personalfragment;
    }

    private EditText editText;
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

        createFile();

        imageView = v.findViewById(R.id.image);
        selectButton = v.findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,RESULT_PICK_IMAGEFILE);
                makeList();
            }
        });

        editText = v.findViewById(R.id.writeName);
        Button button = v.findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String text = editText.getText().toString();
                saveFile(fileName,text);
//              JSONObject jsonObject = createJSON();
//               new CachePref().put("name",jsonObject.toString());
//
//                File file = new File("c:¥¥tmp¥¥test.txt");
//                try {
//                    FileWriter filewriter = new FileWriter(file);
//
//                    filewriter.write(jsonObject.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                showMainUIFragment();
            }
        });

        Button cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = readFile(fileName);
                if (string != null) {
                    editText.setText(string);
                } else {
                    editText.setText("akan");
                }
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

//    private JSONObject createJSON(){
//       JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("name",editText.getText().toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }

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
    public void createFile() {
        File file = new File(getContext().getFilesDir() + "/" + fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    public void saveFile(String file, String str) {
        try {
            FileOutputStream fos = getContext().openFileOutput(file, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String file) {
        String text = null;
        try {
            FileInputStream fileInputStream = getContext().openFileInput(file);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(fileInputStream, "UTF-8"));
            String lineBuffer;
            while( (lineBuffer = reader.readLine()) != null ) {
                text = lineBuffer ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    private void makeList(){
        Map<String, Object> personalData = new HashMap<>();
        personalData.put("名前", editText.getText().toString());
        personalData.put("男", true);
        personalData.put("生年月日", "");
        personalData.put("年齢", "");
        personalData.put("住所", "");
        personalData.put("公開", true);
        personalData.put("画像", "どうしよう");

        List.add(personalData);
    }

}
