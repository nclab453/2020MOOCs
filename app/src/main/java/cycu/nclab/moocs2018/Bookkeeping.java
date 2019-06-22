package cycu.nclab.moocs2018;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class Bookkeeping extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnDatePickerFragmentListener,
                                                TimePickerFragment.OnTimePickerFragmentListener {
    final String TAG = this.getClass().getSimpleName();
    static int count = 0;

    TextView theDate, theTime;

    Button btSave;
    Spinner mCategory, mPayment, addItem;
    String[] expItems, items;
    EditText mItem;

    SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    // 設定日期顯示的格式
    SimpleDateFormat df = new SimpleDateFormat("hh a", Locale.US);
    // 設定時間顯示的格式

    Calendar c;

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

        expItems = getResources().getStringArray(R.array.Default_ExpenseItems);

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
                //finish();
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
        }
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
