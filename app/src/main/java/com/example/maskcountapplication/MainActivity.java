package com.example.maskcountapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button start,end,click;
    Boolean start_status = true;
    TextView time,count;
    ImageView refresh;
    int item_count = 0;
    long startTime = 0;
    boolean pause_status = true;
    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = seconds / 3600;
            seconds = seconds % 60;

            time.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        end = findViewById(R.id.stop);
        time = findViewById(R.id.timer);
        count = findViewById(R.id.count);
        click = findViewById(R.id.click);
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(timerRunnable);
                count.setText("000");
                time.setText("00:00:00");
                item_count = 0;
                startTime = 0;
                pause_status = true;
                start_status = true;
                start.setText("Start");
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_status) {
                    if(pause_status) {
                        count.setText("000");
                        startTime = System.currentTimeMillis();
                    }
                    pause_status = true;
                    timerHandler.postDelayed(timerRunnable, 0);
                    start.setText("Pause");
                    start_status = false;
                }else{
                    timerHandler.removeCallbacks(timerRunnable);
                    start_status = true;
                    start.setText("Start");
                    pause_status = false;
                }
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(timerRunnable);
                start.setText("Start");
                pause_status = true;
                start_status = true;
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!start_status) {
                    item_count = item_count + 2;
                    count.setText(String.format("%03d", item_count));
                }
            }
        });
    }
}
