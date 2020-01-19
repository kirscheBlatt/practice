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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    /**
     * データ保存用のリスト
     */
    List<Map<String, Object>> List = new ArrayList<>();

    /**
     *　保存のテキストファイルの名前
     */
    private String fileName = "file.txt";


    private EditText editText;
    private ImageView personalImage;
    private Button selectButton;
    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private RadioGroup mRadioGroup;
    private String gender = "male";
    private String year = "2000";
    private String month ="1";
    private String  day = "1";

    /**
     * インスタンス化の時に引数を渡すよう
     */
    static PersonalFragment newInstance() {
        PersonalFragment personalfragment = new PersonalFragment();
        return personalfragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_personal, container, false);

        //テキストファイル作成
        createFile();

        personalImage = v.findViewById(R.id.image);
        selectButton = v.findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo　画像を表示させたい
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,RESULT_PICK_IMAGEFILE);


                makeList();
            }
        });

        editText = v.findViewById(R.id.writeName);

        String string = readFile(fileName);
        if (string != null) {
            editText.setText(string);
        } else {
            editText.setText("akan");
        }

        /**
         * 登録ボタンの処理
         */
        Button button = v.findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String text = editText.getText().toString() + gender;

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

        /**
         * キャンセルボタンの処理
         */
        Button cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMainUIFragment();
            }
        });

        /**
         * ラジオボタンの処理
         */
        mRadioGroup = v.findViewById(R.id.genderRadio);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton =v.findViewById(checkedId);
                gender = radioButton.getText().toString();
            }
        });

        final Spinner yearSpinner = v.findViewById(R.id.birth_year_spinner);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) yearSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner monthSpinner = v.findViewById(R.id.birth_month_spinner);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (String) monthSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner daySpinner = v.findViewById(R.id.birth_day_spinner);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = (String) daySpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    personalImage.setImageBitmap(bmp);
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
            text = "null";
        }

        return text;
    }

    private void makeList(){
        Map<String, Object> personalData = new HashMap<>();
        personalData.put("名前", editText.getText().toString());
        personalData.put("性別", gender);
        personalData.put("生年", year);
        personalData.put("生月", month);
        personalData.put("生日", day);
        personalData.put("年齢", "");
        personalData.put("住所", "");
        personalData.put("公開", true);
        personalData.put("画像", "どうしよう");

        List.add(personalData);
    }

}
