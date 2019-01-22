package cycu.nclab.moocs2018;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Bookkeeping extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnDatePickerFragmentListener,
                                                TimePickerFragment.OnTimePickerFragmentListener {
    final String TAG = this.getClass().getSimpleName();
    static int count = 0;

    TextView theDate, theTime;

    Button btSave;

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

    }


    private void registerListener() {
        theDate.setOnClickListener(this);
        theTime.setOnClickListener(this);
        btSave.setOnClickListener(this);
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
                finish();
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
