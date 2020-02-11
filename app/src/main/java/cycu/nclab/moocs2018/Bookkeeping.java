package cycu.nclab.moocs2018;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import cycu.nclab.moocs2018.room.DB_r;
import cycu.nclab.moocs2018.room.MoneyEntity;

public class Bookkeeping extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnDatePickerFragmentListener,
                                                TimePickerFragment.OnTimePickerFragmentListener {
    final String TAG = this.getClass().getSimpleName();
    static int count = 0;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 33216;
    final int MY_IMAGE_CAPTURE_CODE = 22753;

    TextView theDate, theTime;

    Button btSave;
    Spinner mCategory, mPayment, addItem;
    String[] expItems, items;
    EditText mItem, mPrice, mMemo;
    ImageView mCamera;

    SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    /*用來存在資料庫裏面的日期與時間格式*/

    SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    // 設定日期顯示的格式
    SimpleDateFormat df = new SimpleDateFormat("hh a", Locale.US);
    // 設定時間顯示的格式

    Calendar c;
//    DB db = new DB(this);
//    DB_s db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookkeeping_constraintlayout);

        uiInit();
        count++;
        Log.d(TAG, "enter onCreate(), #" + count);
    }

    private void uiInit() {
        theDate = findViewById(R.id.textView6);
        theTime = findViewById(R.id.textView7);

        btSave = findViewById(R.id.button);
        mCategory = findViewById(R.id.spinner);
        mPayment = findViewById(R.id.spinner1);
        addItem = findViewById(R.id.spinner2);
        mItem = findViewById(R.id.editText1);
        mMemo = findViewById(R.id.editText2);
        mPrice = findViewById(R.id.editText);

        expItems = getResources().getStringArray(R.array.Default_ExpenseItems);

        mCamera = findViewById(R.id.imageView);
    }


    @Override
    protected void onStart() {
        super.onStart();

        varInit();
        registerListener();
        Log.d(TAG, "enter onStart(), #" + count);
    }

    private void varInit() {

        if(c == null) {
            c = Calendar.getInstance();
        }

        theDate.setText(df2.format(c.getTime()));
        theTime.setText(df.format(c.getTime()));


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mCategory.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Default_PaymentMethods, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPayment.setAdapter(adapter1);

        items = expItems[0].split(",");
        mItem.setText(items[0]);

        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(this, R.layout.simple_spinner_item, items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addItem.setAdapter(adapter2);
    }


    private AdapterView.OnItemSelectedListener setItem = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mItem.setText(parent.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener categroyChanged = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            items = expItems[position].split(",");
            ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_item, items);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            addItem.setAdapter(adapter2);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void registerListener() {
        theDate.setOnClickListener(this);
        theTime.setOnClickListener(this);
        btSave.setOnClickListener(this);
        addItem.setOnItemSelectedListener(setItem);
        mCategory.setOnItemSelectedListener(categroyChanged);

        mCamera.setOnClickListener(this);
    }


    @Override
    protected void onStop() {
        Log.d(TAG, "enter onStop(), #" + count);
        releaseListener();
        super.onStop();
    }

    private void releaseListener() {
        theDate.setOnClickListener(null);
        theTime.setOnClickListener(null);
        btSave.setOnClickListener(null);
        addItem.setOnItemSelectedListener(null);
        mCategory.setOnItemSelectedListener(null);

        mCamera.setOnClickListener(null);
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "enter onDestroy(), #" + count);
        count--; // -- 遞減運算子； 等於 count = count - 1;
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "enter onPause(), #" + count);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "enter onResume(), #" + count);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "enter onRestart(), #" + count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // 儲存帳務資料
                saveItem();
                // 呼叫本日帳務紀錄
                // 轉跳另一個Activity
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.textView6:
                // 設定日期
                DialogFragment dateFragment = DatePickerFragment.newInstance(c);
                dateFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.textView7:
                // 設定時間
                DialogFragment timeFragment = TimePickerFragment.newInstance(c);
                timeFragment.show(getSupportFragmentManager(), "timePicker");
                break;
            case R.id.imageView:
                // 開啟照相機
                useCamera();
                break;
        }
    }

    private void useCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            Log.d(TAG, "check camera permission");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "需要權限才能夠使用照相機", Toast.LENGTH_LONG).show();
                Log.d(TAG, "show toast.");

                // 若有需要，再次詢問使用者，要求同意權限
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.CAMERA},
//                        MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cInt, MY_IMAGE_CAPTURE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cInt,MY_IMAGE_CAPTURE_CODE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "需要權限才能夠使用照相機", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == MY_IMAGE_CAPTURE_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data.hasExtra("data")) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    mCamera.setImageBitmap(bitmap);
                }
            }
        }
    }


    MoneyEntity oldOne;

    private boolean saveItem() {

        MoneyEntity itemValue = new MoneyEntity();

        // 1. 金額
        String tmp = mPrice.getText().toString();
        if (tmp == null || "".equals(tmp))
            return false;
        else {
            // 這裡沒有做輸入檢查
            itemValue.setPrice(Double.valueOf(tmp));
        }

        // 2. 其他，沒填就是空白
        itemValue.setCategory(mCategory.getSelectedItem().toString());
        itemValue.setItem(mItem.getText().toString().trim());
        itemValue.setPayStyle(mPayment.getSelectedItem().toString());
        itemValue.setMemo(mMemo.getText().toString());
        itemValue.setTimestamp(dbFormat.format(c.getTime()));

        // TODO 3. 照片縮圖

        if (oldOne == null || !oldOne.equals(itemValue)) {
            oldOne = itemValue;
            DB_r.insert(this, itemValue);
        }
        else {
            return false;
        }

        return true;
    }

    @Override
    public void onDateSet(Calendar c) {
        this.c.setTimeInMillis(c.getTimeInMillis());
        theDate.setText(df2.format(c.getTime()));
    }

    @Override
    public void onTimeSet(Calendar c) {
        this.c.setTimeInMillis(c.getTimeInMillis());
        theTime.setText(df.format(c.getTime()));
    }
}
