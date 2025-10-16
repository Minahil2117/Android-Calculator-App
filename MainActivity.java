package com.example.myfirstcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayTextView;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTextView = findViewById(R.id.display_text_view);

        // Number buttons
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);

        // Operator buttons
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_subtract).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_equals).setOnClickListener(this);

        // Special buttons
        findViewById(R.id.btn_c).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.btn_decimal).setOnClickListener(this);
        findViewById(R.id.btn_open_paren).setOnClickListener(this);
        findViewById(R.id.btn_close_paren).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        int id = v.getId();

        if (id == R.id.btn_c) {
            clear();
        } else if (id == R.id.btn_del) {
            delete();
        } else if (id == R.id.btn_equals) {
            calculate();
        } else if (id == R.id.btn_add || id == R.id.btn_subtract ||
                id == R.id.btn_multiply || id == R.id.btn_divide) {
            handleOperator(buttonText);
        } else if (id == R.id.btn_decimal) {
            handleDot();
        } else if (id == R.id.btn_open_paren || id == R.id.btn_close_paren) {
            handleParenthesis(buttonText);
        } else {
            handleNumber(buttonText);
        }
    }

    private void handleNumber(String number) {
        if (isNewOperation) {
            currentInput = number;
            isNewOperation = false;
        } else {
            if (currentInput.equals("0")) {
                currentInput = number;
            } else {
                currentInput += number;
            }
        }
        updateDisplay(currentInput);
    }

    private void handleOperator(String op) {
        if (!currentInput.isEmpty()) {
            if (operator.isEmpty()) {
                firstValue = Double.parseDouble(currentInput);
            } else {
                calculate();
            }
            // Convert display symbols to operators
            if (op.equals("×")) {
                operator = "*";
            } else if (op.equals("÷")) {
                operator = "/";
            } else {
                operator = op;
            }
            isNewOperation = true;
        }
    }

    private void handleDot() {
        if (isNewOperation) {
            currentInput = "0.";
            isNewOperation = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        updateDisplay(currentInput);
    }

    private void handleParenthesis(String paren) {
        currentInput += paren;
        updateDisplay(currentInput);
    }

    private void calculate() {
        if (!operator.isEmpty() && !currentInput.isEmpty()) {
            double secondValue = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    if (secondValue != 0) {
                        result = firstValue / secondValue;
                    } else {
                        updateDisplay("Error");
                        clear();
                        return;
                    }
                    break;
            }

            // Format result to remove unnecessary decimals
            if (result == (long) result) {
                currentInput = String.valueOf((long) result);
            } else {
                currentInput = String.valueOf(result);
            }

            updateDisplay(currentInput);
            firstValue = result;
            operator = "";
            isNewOperation = true;
        }
    }

    private void clear() {
        currentInput = "";
        updateDisplay("0");
    }

    private void allClear() {
        currentInput = "";
        operator = "";
        firstValue = 0;
        isNewOperation = true;
        updateDisplay("0");
    }

    private void delete() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.isEmpty()) {
                updateDisplay("0");
            } else {
                updateDisplay(currentInput);
            }
        }
    }

    private void updateDisplay(String value) {
        displayTextView.setText(value);
    }
}
