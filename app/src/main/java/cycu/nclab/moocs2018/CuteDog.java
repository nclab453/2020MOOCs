package cycu.nclab.moocs2018;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CuteDog extends AppCompatActivity {

    ImageView iv;
    final int IMAGE_ONE = 10001;
    final int IMAGE_TWO = 10002;
    final int IMAGE_THREE = 10003;

    final int interval = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cute_dog);

        uiInit();
    }

    private void uiInit() {
        iv = findViewById(R.id.imageView3);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        SystemClock.sleep(100000);
        // 使用Handler自動切換螢幕畫面
        Message msg = myHandler.obtainMessage();
        msg.what = IMAGE_TWO;
        myHandler.sendMessageDelayed(msg, interval); // 0.5秒後執行
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private Handler myHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMAGE_ONE:
                    iv.setImageResource(R.drawable.ic_dog_rotate_right_1);
                    msg = this.obtainMessage();
                    msg.what = IMAGE_TWO;
                    myHandler.sendMessageDelayed(msg, interval); // 0.2秒後執行
                    break;
                case IMAGE_TWO:
                    iv.setImageResource(R.drawable.ic_dog_rotate_right_2);
                    msg = this.obtainMessage();
                    msg.what = IMAGE_THREE;
                    myHandler.sendMessageDelayed(msg, interval); // 0.2秒後執行
                    break;
                case IMAGE_THREE:
                    iv.setImageResource(R.drawable.ic_dog_rotate_right_3);
                    msg = this.obtainMessage();
                    msg.what = IMAGE_ONE;
                    myHandler.sendMessageDelayed(msg, interval); // 0.2秒後執行
                    break;
            }
        }
    };
}
