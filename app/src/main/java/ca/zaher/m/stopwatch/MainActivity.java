package ca.zaher.m.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //properties
    private Handler timerHandler;
    private ArrayAdapter<String> itemAdapeter;
    private TextView txtTimer;
    private Button btnStartPause, btnLapReset;

    private long millisecondTime, startTime, pausedTime, updateTime = 0;

    private int seconds, minutes, milliSeconds;

    private boolean stopWatchStarted, stopWatchPacused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvLaps;

        timerHandler = new Handler();

        itemAdapeter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        txtTimer = findViewById(R.id.tv_timer);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnLapReset = findViewById(R.id.btnLapReset);
        lvLaps = findViewById(R.id.lvLaps);

        lvLaps.setAdapter(itemAdapeter);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!stopWatchStarted || stopWatchPacused) {
                    stopWatchStarted = true;
                    stopWatchPacused = false;

                    startTime = SystemClock.uptimeMillis();

                    timerHandler.postDelayed(timerRunnable, 0);

                    btnStartPause.setText(R.string.pause);
                    btnLapReset.setText(R.string.lap);
                } else {
                    pausedTime += millisecondTime;
                    stopWatchPacused = true;
                    timerHandler.removeCallbacks(timerRunnable);
                    btnStartPause.setText(R.string.start);
                    btnLapReset.setText(R.string.reset);
                }
            }
        });

        btnLapReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopWatchStarted && !stopWatchPacused) {
                    String laptime = minutes + ":"
                            + String.format("%02d", seconds) + ":"
                            + String.format("%03d", milliSeconds);
                    itemAdapeter.add(laptime);
                } else if (stopWatchStarted) {
                    stopWatchStarted = false;
                    stopWatchPacused = false;

                    timerHandler.removeCallbacks(timerRunnable);

                    millisecondTime = 0;
                    startTime = 0;
                    pausedTime = 0;
                    updateTime = 0;
                    seconds = 0;
                    minutes = 0;
                    milliSeconds = 0;

                    txtTimer.setText(R.string.timer_start);
                    btnLapReset.setText(R.string.lap);

                    itemAdapeter.clear();
                } else {
                    Toast.makeText(getApplicationContext(), "Timer hasn't started yet!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;

            updateTime = pausedTime + millisecondTime;
            milliSeconds = (int) (updateTime % 100);
            seconds = (int) (updateTime / 1000);

            minutes = seconds / 60;
            seconds = seconds % 60;
            String updateTime = minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds);

            txtTimer.setText(updateTime);

            timerHandler.postDelayed(this, 0);

        }
    };

}
