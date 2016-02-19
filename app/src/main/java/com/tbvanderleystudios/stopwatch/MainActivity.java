package com.tbvanderleystudios.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.timerTextView) TextView mTimerTextView;
    @Bind(R.id.startButton) Button mStartButton;
    @Bind(R.id.stopButton) Button mStopButton;
    @Bind(R.id.resetButton) Button mResetButton;

    private int mMilliseconds = 0;
    private boolean mRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mMilliseconds = savedInstanceState.getInt("milliseconds");
            mRunning = savedInstanceState.getBoolean("running");
        }

        runTimer();

        onStartButtonClick();
        onStopButtonClick();
        onResetButtonClick();
    }

    private void onResetButtonClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunning = false;
                mMilliseconds = 0;
            }
        };

        mResetButton.setOnClickListener(listener);
    }

    private void onStopButtonClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunning = false;
            }
        };

        mStopButton.setOnClickListener(listener);
    }

    private void onStartButtonClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunning = true;
            }
        };
        mStartButton.setOnClickListener(listener);
    }

    private void runTimer() {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (mMilliseconds / 6000);
                int seconds = (mMilliseconds % 6000) / 100;
                int milliseconds = mMilliseconds % 100;

                String time = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
                mTimerTextView.setText(time);
                if (mRunning) {
                    mMilliseconds +=1;
                }

                handler.postDelayed(this, 1);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("milliseconds", mMilliseconds);
        outState.putBoolean("running", mRunning);
    }
}
