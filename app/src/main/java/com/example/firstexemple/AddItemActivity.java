package com.example.firstexemple;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity_test";

    private EditText name;
    private EditText price;
    private ImageButton add_button;
    private TextView rub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        setTitle(R.string.new_item);
        rub = findViewById(R.id.rub);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        add_button = findViewById(R.id.add_button);
        add_button.setEnabled(false);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: test");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "beforeTextChanged: test1");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "beforeTextChanged: test2");
                add_button.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(price.getText().toString()));
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                add_button.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(name.getText().toString()));
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = name.getText().toString();
                String itemPrice = price.getText().toString();
                Log.i(TAG, "onClick: " + itemName + " - " + itemPrice);
            }
        });
    }
}