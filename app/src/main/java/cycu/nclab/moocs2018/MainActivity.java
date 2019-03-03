package cycu.nclab.moocs2018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = this.getClass().getSimpleName();
    static int count = 0;
    final int demoCase = 1; // 0: click to switch； 1: use handler


    @Override   // annotation, 註解
    protected void onCreate(Bundle savedInstanceState) { // function, 函式
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count++; // ++: 遞增運算子；等於 count = count + 1;

        Log.d(TAG, "enter onCreate(), #" + count); // 3 + 4 , + : 運算子；3, 4 運算元
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "enter onStart(), #" + count);

        switch (demoCase) {
            case 0:
                // 找到此Activity的root View，並增加click監聽
                (this.findViewById(android.R.id.content)).setOnClickListener(this);
                break;
            case 1:
                // 使用Handler自動切換螢幕畫面
                Message msg = myHandler.obtainMessage();    // 從Global Message Pool裡面取一個message出來，
                                                            // 比新建立一個有效率。
                                                            // Ctr+Q 查看指令文件
                myHandler.sendMessageDelayed(msg, 500); // 0.5秒後執行
                break;
        }

    }



    @Override
    protected void onStop() {
        Log.d(TAG, "enter onStop(), #" + count);

        super.onStop();
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "enter onDestroy(), #" + count);
        count--;
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
            case android.R.id.content:
                startActivity(new Intent(this, Bookkeeping.class));

                // 增加過場動畫
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);

                // 強制activity終止
                MainActivity.this.finish();
                break;
        }
    }

    // ---------------------使用handler的範例------------------------------------------------------

    private Handler myHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(MainActivity.this, Bookkeeping.class));

            // 增加過場動畫
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            MainActivity.this.finish();
        }
    };

    // -------------------------------------------------------------------------------------------

}

