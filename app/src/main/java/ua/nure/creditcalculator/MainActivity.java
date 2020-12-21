package ua.nure.creditcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int BASE_PERCENT = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((EditText)findViewById(R.id.et_percent)).setText(Integer.toString(BASE_PERCENT));
        findViewById(R.id.et_percent).setEnabled(false);
        findViewById(R.id.btn_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int credit = Integer.parseInt(((EditText)findViewById(R.id.et_credit)).getText().toString());
                int duration = Integer.parseInt(((EditText)findViewById(R.id.et_duration)).getText().toString());
                int percent = changePercent(credit, duration);
                ((EditText)findViewById(R.id.et_percent)).setText(Integer.toString(percent));
                ((TextView)findViewById(R.id.tv_result)).setText(Long.toString(calculateCredit(credit, duration, percent)));
            }
        });
        findViewById(R.id.et_duration).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    try {
                        setPercentEnable(0, Integer.parseInt(((EditText) view).getText().toString()));
                    } catch(NumberFormatException ex) {
                        ex.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error in input!", Toast.LENGTH_LONG).show();
                        ((EditText)findViewById(R.id.et_duration)).setText("");
                    }
                }
            }
        });
        findViewById(R.id.et_credit).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    try {
                        setPercentEnable(Integer.parseInt(((EditText)view).getText().toString()), 0);
                    } catch(NumberFormatException ex) {
                        Toast.makeText(MainActivity.this, "Error in input!", Toast.LENGTH_LONG).show();
                        ((EditText)findViewById(R.id.et_credit)).setText("");
                    }
                }
            }
        });
    }

    private int changePercent(int credit, int duration) {
        int percent = BASE_PERCENT;
        if(credit > 9999 && credit < 20000) {
            percent--;
        } else if(credit > 19999 && credit < 40000) {
            percent -= 2;
        }
        if(duration >= 3*12 && duration < 5*12) {
            percent++;
        } else if(duration >= 5*12 && duration < 10*12) {
            percent += 2;
        }
        return percent;
    }

    private long calculateCredit(int sum, int duration, int percent) {
        int monthSum = sum * percent / 100 / 12;
        return duration * monthSum + sum;
    }
    private void setPercentEnable(int sum, int duration) throws NumberFormatException {
        findViewById(R.id.et_percent).setEnabled(sum >= 40000 || duration >= 120);
    }
}