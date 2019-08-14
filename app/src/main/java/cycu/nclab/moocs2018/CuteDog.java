package cycu.nclab.moocs2018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import cycu.nclab.moocs2018.pref.Prefs;
import cycu.nclab.moocs2018.pref.PrefsActivity;

public class CuteDog extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();
    ImageView iv;
    final int IMAGE_ONE = 10001;
    final int IMAGE_TWO = 10002;
    final int IMAGE_THREE = 10003;

    int interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cute_dog);

        PreferenceManager.setDefaultValues(this, R.xml.preference, false);
        uiInit();
    }

    private void uiInit() {
        iv = findViewById(R.id.imageView3);
    }

    @Override
    protected void onStart() {
        super.onStart();

        interval = (int) (1000 /(3*Prefs.getCuteDogCircles(this)));
//        SystemClock.sleep(100000);
        // 使用Handler自動切換螢幕畫面

        if (Prefs.isRun(this)) {
            Message msg = myHandler.obtainMessage();
            msg.what = IMAGE_TWO;
            myHandler.sendMessageDelayed(msg, interval); // interval毫秒後執行
        }
    }

    @Override
    protected void onStop() {
        myHandler.removeCallbacksAndMessages(null);
        super.onStop();
    }

    private Handler myHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMAGE_ONE:
                    iv.setImageResource(R.drawable.ic_dog_rotate_right_1);
                    Log.d(TAG, "ONE: " + SystemClock.elapsedRealtime());
                    msg = this.obtainMessage();
                    msg.what = IMAGE_TWO;
                    myHandler.sendMessageDelayed(msg, interval); // interval毫秒後執行
                    break;
                case IMAGE_TWO:
                    iv.setImageResource(R.drawable.ic_dog_rotate_right_2);
                    Log.d(TAG, "TWO: " + SystemClock.elapsedRealtime());
                    msg = this.obtainMessage();
                    msg.what = IMAGE_THREE;
                    myHandler.sendMessageDelayed(msg, interval); // interval毫秒後執行
                    break;
                case IMAGE_THREE:
                    iv.setImageResource(R.drawable.ic_dog_rotate_right_3);
                    Log.d(TAG, "THR: " + SystemClock.elapsedRealtime());
                    msg = this.obtainMessage();
                    msg.what = IMAGE_ONE;
                    myHandler.sendMessageDelayed(msg, interval); //interval毫秒後執行
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, PrefsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
