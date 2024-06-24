package com.ashmit.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, b_equal, b_multi, b_divide, b_add, b_sub, b_clear, b_dot, b_para1, b_para2;
    private TextView t1, t2;
    private final char ADDITION = '+';
    private final char SUBTRACTION = '-';
    private final char MULTIPLICATION = '*';
    private final char DIVISION = '/';
    private final char EQU = '=';
    private final char MODULUS = '%';
    private char ACTION;
    private double val1 = Double.NaN;
    private double val2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSetup();

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ACTION == EQU) {
                    t2.setText("");
                    t1.setText("");
                    val1 = Double.NaN;
                    val2 = Double.NaN;
                    ACTION = '0';
                }
                ifErrorOnOutput();
                exceedLength();
                Button button = (Button) view;
                t1.setText(t1.getText().toString() + button.getText().toString());
            }
        };

        b1.setOnClickListener(numberListener);
        b2.setOnClickListener(numberListener);
        b3.setOnClickListener(numberListener);
        b4.setOnClickListener(numberListener);
        b5.setOnClickListener(numberListener);
        b6.setOnClickListener(numberListener);
        b7.setOnClickListener(numberListener);
        b8.setOnClickListener(numberListener);
        b9.setOnClickListener(numberListener);
        b0.setOnClickListener(numberListener);

        b_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exceedLength();
                t1.setText(t1.getText().toString() + ".");
            }
        });

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOperation(ADDITION, "+");
            }
        });

        b_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOperation(SUBTRACTION, "-");
            }
        });

        b_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOperation(MULTIPLICATION, "×");
            }
        });

        b_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOperation(DIVISION, "/");
            }
        });

        b_para1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOperation(MODULUS, "%");
            }
        });

        b_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t1.getText().length() > 0) {
                    val2 = Double.parseDouble(t1.getText().toString());
                    operation();
                    ACTION = EQU;
                    if (!ifReallyDecimal()) {
                        t2.setText(String.valueOf(val1));
                    } else {
                        t2.setText(String.valueOf((int) val1));
                    }
                    t1.setText(null);
                } else {
                    t2.setText("Error");
                }
            }
        });

        b_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t1.getText().length() > 0) {
                    CharSequence name = t1.getText().toString();
                    t1.setText(name.subSequence(0, name.length() - 1));
                } else {
                    val1 = Double.NaN;
                    val2 = Double.NaN;
                    t1.setText("");
                    t2.setText("");
                    ACTION = '0';
                }
            }
        });

        b_clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                val1 = Double.NaN;
                val2 = Double.NaN;
                t1.setText("");
                t2.setText("");
                ACTION = '0';
                return true;
            }
        });
    }

    private void viewSetup() {
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);
        b0 = findViewById(R.id.button0);
        b_equal = findViewById(R.id.button_equal);
        b_multi = findViewById(R.id.button_multi);
        b_divide = findViewById(R.id.button_divide);
        b_add = findViewById(R.id.button_add);
        b_sub = findViewById(R.id.button_sub);
        b_clear = findViewById(R.id.button_clear);
        b_dot = findViewById(R.id.button_dot);
        b_para1 = findViewById(R.id.button_para1);
        b_para2 = findViewById(R.id.button_para2);
        t1 = findViewById(R.id.input);
        t2 = findViewById(R.id.output);
    }

    private void performOperation(char operation, String symbol) {
        if (t1.getText().length() > 0) {
            if (ACTION != EQU) {
                operation();
            }
            ACTION = operation;
            if (!ifReallyDecimal()) {
                t2.setText(val1 + symbol);
            } else {
                t2.setText((int) val1 + symbol);
            }
            t1.setText(null);
        } else {
            t2.setText("Error");
        }
    }

    private void operation() {
        if (!Double.isNaN(val1)) {
            if (ACTION != EQU) {
                val2 = Double.parseDouble(t1.getText().toString());
                switch (ACTION) {
                    case ADDITION:
                        val1 = val1 + val2;
                        break;
                    case SUBTRACTION:
                        val1 = val1 - val2;
                        break;
                    case MULTIPLICATION:
                        val1 = val1 * val2;
                        break;
                    case DIVISION:
                        if (val2 != 0) {
                            val1 = val1 / val2;
                        } else {
                            val1 = Double.NaN; // Indicate division by zero error
                        }
                        break;
                    case MODULUS:
                        val1 = val1 % val2;
                        break;
                    default:
                        break;
                }
            }
        } else {
            val1 = Double.parseDouble(t1.getText().toString());
        }
    }

    private void ifErrorOnOutput() {
        if (t2.getText().toString().equals("Error")) {
            t2.setText("");
        }
    }

    private boolean ifReallyDecimal() {
        return val1 == (int) val1;
    }

    private void exceedLength() {
        if (t1.getText().toString().length() > 10) {
            t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
    }
}
