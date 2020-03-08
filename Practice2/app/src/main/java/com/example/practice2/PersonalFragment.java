package com.example.practice2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class PersonalFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    // ファイルパス
    private String filePath = Environment.getExternalStorageDirectory().toString() + "/Android/data/com/.example.practice2/setting.json";
    private EditText editNameText;
    private ImageView personalImage;
    private Button selectButton;
    private RadioGroup mRadioGroup;
    private String gender = "男性";
    private String year = "2000";
    private String month ="1";
    private String  day = "1";
    private int age = 1;
    private TextView ageNumberText = null;
    private EditText addressText = null;
    private CheckBox addressCheck = null;
    private static boolean addMode = false;
    private static int position = -1;
    private String imagePath = "";


    //データクラスのインスタンスを取得
    Data mData = Data.getInstance();

    /**
     * コンストラクタ
     */
    public PersonalFragment() {
    }

    /**
     * データ保存用のリスト
     */
    List<Map<String, Object>> list = new ArrayList<>();

    /**
     * インスタンス化の時にモードとポジションをもらう
     */
    static PersonalFragment newInstance(boolean mode,int p) {
        PersonalFragment personalfragment = new PersonalFragment();
        addMode = mode;
        position = p;
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

        //リストをうけとる
        list = mData.getPersonalDataList();
        personalImage = v.findViewById(R.id.image);
        selectButton = v.findViewById(R.id.selectButton);
        editNameText = v.findViewById(R.id.writeName);
        addressText = v.findViewById(R.id.editAddress);
        addressCheck = v.findViewById(R.id.openAddress);

        //権限チェック
        checkPermission();

        //選択ボタンの処理
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo　画像を表示させたい
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,Define.RESULT_PICK_IMAGE_FILE);
                makeList();
            }
        });

        //新規なら空白、編集なら中身あり
        if (!addMode){
            String s ="";
            Map m =  mData.personalDataList.get(position);
            s = m.get(Define.NAME).toString();
            editNameText.setText(s);
        }


        /**
         * 登録ボタンの処理
         */
        Button button = v.findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                makeList();
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
        final Spinner monthSpinner = v.findViewById(R.id.birth_month_spinner);
        final Spinner daySpinner = v.findViewById(R.id.birth_day_spinner);

        ageNumberText = v.findViewById(R.id.ageNumber);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) yearSpinner.getSelectedItem();
                month = (String) monthSpinner.getSelectedItem();
                day = (String) daySpinner.getSelectedItem();
                calcAge(year,month,day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) yearSpinner.getSelectedItem();
                month = (String) monthSpinner.getSelectedItem();
                day = (String) daySpinner.getSelectedItem();
                calcAge(year,month,day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) yearSpinner.getSelectedItem();
                month = (String) monthSpinner.getSelectedItem();
                day = (String) daySpinner.getSelectedItem();
                calcAge(year,month,day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }


    //メイン画面に遷移するメソッド
    private void showMainUIFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, MainUIFragment.newInstance());
        fragmentTransaction.commit();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Define.RESULT_PICK_IMAGE_FILE && resultCode == RESULT_OK){
            Uri uri = null;
            if (data != null){
                uri = data.getData();

                if (uri !=null){
                    imagePath = uri.toString();
                }

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

    private void calcAge(String yea,String  mon,String  da){

        int y = Integer.valueOf(yea);
        int m = Integer.valueOf(mon);
        int d = Integer.valueOf(da);

        Calendar target = Calendar.getInstance();
//今日の日付を取るならsetメソッドは不要，Calendarクラスで月は1小さい数で表す
        Calendar birthday = Calendar.getInstance();
        birthday.set(y, m-1, d); //誕生日の年月日をセット

        age = target.get(Calendar.YEAR) - y;

        birthday.set(Calendar.YEAR, Calendar.YEAR); //誕生日の年を対象の年にスライド
        if(target.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR))
            age -= 1; //求めた年齢を1引く
        ageNumberText.setText(String.valueOf(age));
    }


    private void makeList(){
        Map<String, Object> personalData = new HashMap<>();
        personalData.put(Define.NAME, editNameText.getText().toString());
        personalData.put(Define.JENDER, gender);
        personalData.put(Define.YEAR, year);
        personalData.put(Define.MONTH, month);
        personalData.put(Define.DAY, day);
        personalData.put(Define.AGE, age);
        personalData.put(Define.ADDRESS, addressText.getText().toString());
        personalData.put(Define.ADDRESS_CHECK, addressCheck.isChecked());
        if (imagePath !=null){
            personalData.put(Define.IMAGE_PATH,imagePath);
        }

        if (addMode) {
            list.add(personalData);
        }else{
            list.remove(position);
            list.add(position,personalData);
        }
        mData.setPersonalDataList(list);
        mData.saveFile(filePath);
    }


    // permissionの確認
    public void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Define.REQUEST_PERMISSION);

        } else {
            Toast toast =
                    Toast.makeText(this.getActivity(), "アプリ実行に許可が必要です", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    Define.REQUEST_PERMISSION);
        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Define.REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // それでも拒否された時の対応
                Toast toast =
                        Toast.makeText(this.getActivity(), "何もできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
