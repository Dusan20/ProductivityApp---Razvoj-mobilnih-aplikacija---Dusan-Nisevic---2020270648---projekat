package com.example.productivityapp_ver12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    public EditText TimerF;
    public EditText TimerO;
    private TextView mTextViewCountDown;
    private TextView naslov;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonRestMode;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    public boolean mTimerStarted = false;

    private long mTimeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        naslov = findViewById(R.id.TimerN);

        mButtonRestMode = findViewById(R.id.BOdmor);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        Button BBack = findViewById(R.id.BBack2);

        TimerF = findViewById(R.id.EditFocusTimer);
        TimerO = findViewById(R.id.EditOdmorTimer);

        BBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TimerO.getText().toString().matches("")&&mTimerStarted==false||TimerF.getText().toString().matches("")&&mTimerStarted==false){naslov.setText("Polja za minute moraju biti popunjena");}
                else {
                    if (mTimerRunning) {
                        pauseTimer();
                    } else {
                        if (mTimerStarted == false) {
                            naslov.setText("FOKUS TIMER");
                            mTimeLeftInMillis = sconveret(Long.parseLong(TimerF.getText().toString()));
                        }
                        startTimer();
                    }
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        mButtonRestMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    if(mTimerStarted==false) {
                        naslov.setText("ODMOR TIMER");
                        mTimeLeftInMillis = sconveret(Long.parseLong(TimerO.getText().toString()));
                    }
                    startTimer();
                }
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mTimerStarted = true;
        mButtonRestMode.setVisibility(View.INVISIBLE);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTimerStarted = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonRestMode.setVisibility(View.VISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonStartPause.setVisibility(View.VISIBLE);
        mButtonReset.setVisibility(View.INVISIBLE);
        TimerF.setVisibility(View.INVISIBLE);
        TimerO.setVisibility(View.INVISIBLE);

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        naslov.setText("");
        mButtonRestMode.setVisibility(View.INVISIBLE);
        TimerF = findViewById(R.id.EditFocusTimer);
        mTimeLeftInMillis = sconveret(Long.parseLong(TimerF.getText().toString()));
        updateCountDownText();
        mTimerStarted=false;
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        TimerF.setVisibility(View.VISIBLE);
        TimerO.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
    public long sconveret(long x){
        return x*60000;
    }
}