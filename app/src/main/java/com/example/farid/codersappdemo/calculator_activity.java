package com.example.farid.codersappdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class calculator_activity extends AppCompatActivity implements View.OnClickListener{

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnXOR, btnAND, btnOR, btnMOD, btnMultiply, btnDivide, btnMinus, btnPlus, btnEqual, btnCancel;

    private TextView operatorView, inputView, decView, binView;
    StringBuilder input;

    int calculationMode = 0;
    final int BASE_DECIMAL = 0;
    final int BASE_BINARY = 1;

    Stack<String> stack;
    int ans = 0, temp = 0;

    final String ZERO = "0", ONE = "1", TWO = "2", THREE = "3", FOUR = "4", FIVE = "5", SIX = "6",
            SEVEN = "7", EIGHT = "8", NINE = "9";

    final String PLUS = "+", MINUS = "-", DIVISION = "/", MULTIPLICATION = "*", XOR = "^", AND = "&",
            OR = "|", MOD = "%", NONE = "";

    //String operand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        init();
        stack = new Stack<>();

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnXOR.setOnClickListener(this);
        btnAND.setOnClickListener(this);
        btnOR.setOnClickListener(this);
        btnMOD.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Initializes all the widgets.
     */
    public void init() {
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnXOR = findViewById(R.id.btnXOR);
        btnAND = findViewById(R.id.btnAND);
        btnOR = findViewById(R.id.btnOR);
        btnMOD = findViewById(R.id.btnMOD);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnCancel = findViewById(R.id.btnCancel);
        btnEqual = findViewById(R.id.btnEqual);

        operatorView = findViewById(R.id.operatorView);
        inputView = findViewById(R.id.inputView);
        decView = findViewById(R.id.decView);
        binView = findViewById(R.id.binView);

        input = new StringBuilder();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *  On RadioButton Click
     *  If Binary is selected it disables the keypad
     *  If Decimal is selected it enables the keypad
     *  Also all the persisting data is cleared.
     *
     * @param view
     */
    public void onBaseRadioClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.radioBaseBin:
                if (checked) {
                    keypadDisabled();
                    allClear();
                    calculationMode = BASE_BINARY;
                    break;
                }
            case R.id.radioBaseDec:
                if (checked) {
                    keypadEnabled();
                    allClear();
                    calculationMode = BASE_DECIMAL;
                    break;
                }
        }
    }

    /**
     * Enables the keypad.
     * Called when Base Decimal is selected.
     */
    private void keypadEnabled() {
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);

        btn2.setTextColor(Color.parseColor("#c9c8c8"));
        btn3.setTextColor(Color.parseColor("#c9c8c8"));
        btn4.setTextColor(Color.parseColor("#c9c8c8"));
        btn5.setTextColor(Color.parseColor("#c9c8c8"));
        btn6.setTextColor(Color.parseColor("#c9c8c8"));
        btn7.setTextColor(Color.parseColor("#c9c8c8"));
        btn8.setTextColor(Color.parseColor("#c9c8c8"));
        btn9.setTextColor(Color.parseColor("#c9c8c8"));

    }

    /**
     * Disables the keypad.
     * Called when Base Binary is selected.
     */
    private void keypadDisabled() {
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);

        btn2.setTextColor(Color.parseColor("#565a60"));
        btn3.setTextColor(Color.parseColor("#565a60"));
        btn4.setTextColor(Color.parseColor("#565a60"));
        btn5.setTextColor(Color.parseColor("#565a60"));
        btn6.setTextColor(Color.parseColor("#565a60"));
        btn7.setTextColor(Color.parseColor("#565a60"));
        btn8.setTextColor(Color.parseColor("#565a60"));
        btn9.setTextColor(Color.parseColor("#565a60"));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn0:
                numberInput(ZERO);
                break;

            case R.id.btn1:
                numberInput(ONE);
                break;

            case R.id.btn2:
                numberInput(TWO);
                break;

            case R.id.btn3:
                numberInput(THREE);
                break;

            case R.id.btn4:
                numberInput(FOUR);
                break;

            case R.id.btn5:
                numberInput(FIVE);
                break;

            case R.id.btn6:
                numberInput(SIX);
                break;

            case R.id.btn7:
                numberInput(SEVEN);
                break;

            case R.id.btn8:
                numberInput(EIGHT);
                break;

            case R.id.btn9:
                numberInput(NINE);
                break;

            case R.id.btnXOR:
                operandInput(XOR);
                break;

            case R.id.btnAND:
                operandInput(AND);
                break;

            case R.id.btnOR:
                operandInput(OR);
                break;

            case R.id.btnMOD:
                operandInput(MOD);
                break;

            case R.id.btnMultiply:
                operandInput(MULTIPLICATION);
                break;

            case R.id.btnDivide:
                operandInput(DIVISION);
                break;

            case R.id.btnPlus:
                operandInput(PLUS);
                break;

            case R.id.btnMinus:
                operandInput(MINUS);
                break;

            case R.id.btnCancel:
                allClear();
                break;

            case R.id.btnEqual:
                operandInput(NONE);
                break;
        }
    }

    public void numberInput(String str) {
        input.append(str);
        inputView.setText(input);
//        operatorView.setText(NONE);
    }

    public void operandInput(String str) {

        //Check if thes user presses Equals button
        if (stack.isEmpty() && !(input.toString().isEmpty()) && str != NONE) {
            operatorView.setText(str);
            stack.push(input.toString());
            stack.push(str);

            input.setLength(0); //Clear the StringBuilder
        }
        //Stack contains only a number, and user doesn't press Equals button
        else if (stack.size() == 1 && (input.toString().isEmpty()) && str != NONE) {
            operatorView.setText(str);
            stack.push(str);
        }
        // The previous answer is present in the stack, which will be removed. Also check for Equals button press.
        else if (stack.size() == 1 && !(input.toString().isEmpty()) && str != NONE) {
            operatorView.setText(str);
            stack.clear();
            stack.push(input.toString());
            stack.push(str);

            input.setLength(0); //Clear the StringBuilder
        }
        //There is a number and an operator
        else if (stack.size() == 2 && !(input.toString().isEmpty())) {

            calculate();

            inputView.setText(String.valueOf((calculationMode == BASE_DECIMAL)? ans : Integer.toBinaryString(ans)));
            binView.setText(Integer.toBinaryString(ans));
            decView.setText(String.valueOf(ans));
            operatorView.setText(str);

            stack.push(String.valueOf(ans));
            input.setLength(0); //Clear the StringBuilder

            if (str != NONE) {
                stack.push(str);
            }
        }
    }

    public void calculate() {

        try {

            // If calculationMode is decimal, then just parse int else, parse it and convert binary to decimal
            // Similarly for all calculations below this condition is checked.
            ans = (calculationMode == BASE_DECIMAL)? Integer.parseInt(input.toString()) : Integer.parseInt(input.toString(), 2);

            switch (stack.peek()) {

                case XOR:
                    stack.pop();
                    ans ^= (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    break;
                case AND:
                    stack.pop();
                    ans &= (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    break;
                case OR:
                    stack.pop();
                    ans |= (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    break;
                case MOD:
                    stack.pop();
                    temp = (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    temp %= ans;
                    ans = temp;
                    break;
                case MULTIPLICATION:
                    stack.pop();
                    ans *= (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    break;
                case DIVISION:
                    stack.pop();
                    int temp = (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    temp /= ans;
                    ans = temp;
                    break;
                case PLUS:
                    stack.pop();
                    ans += (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    break;
                case MINUS:
                    stack.pop();
                    temp = (calculationMode == BASE_DECIMAL)? Integer.parseInt(stack.pop()) : Integer.parseInt(stack.pop(), 2);
                    temp -= ans;
                    ans = temp;
                    break;
            }
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "SUM TING WONG", Toast.LENGTH_SHORT);
        }
    }

    private void allClear() {
        input.setLength(0);
        inputView.setText("0");
        decView.setText("0");
        binView.setText("0000");
        operatorView.setText(NONE);
        stack.clear();
    }
}
