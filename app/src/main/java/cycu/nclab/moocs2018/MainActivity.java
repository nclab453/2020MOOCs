package cycu.nclab.moocs2018;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tx;
    Button bt;

    @Override   // annotation, 註解
    protected void onCreate(Bundle savedInstanceState) { // function, 函式
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tx = findViewById(R.id.textView);
        bt = findViewById(R.id.button);

        bt.setOnClickListener(this);
        tx.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                tx.setTextColor(Color.RED);
                tx.setText("Hello World!");
                break;
            case R.id.button:
                tx.setTextColor(Color.GREEN);
                tx.setText("點我、點我！");
                break;
        }

    }

    //    第二種方式，建立獨立的監聽器
//    private View.OnClickListener myClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            tx.setTextColor(Color.BLUE);
//        }
//    };


    //    第一種方式，使用xml onClick屬性
//    public void onButtonClick(View view) {
//        tx.setTextColor(Color.BLUE);
//    }

}
