package com.example.firstexemple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity_test";

    private EditText name;
    private EditText price;
    private String type;
    private ImageButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        setTitle(R.string.new_item);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        add_button = findViewById(R.id.add_button);
        add_button.setEnabled(false);

        type = getIntent().getStringExtra(ItemsFragment.KEY_TYPE);


        Toolbar toolbar = findViewById(R.id.toolbarnew);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.new_item));

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                add_button.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(price.getText().toString()));
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
                Intent result = new Intent();
                result.putExtra("result_item", new Item(itemName,itemPrice,type));
                setResult(Activity.RESULT_OK,result);
                finish();
            }
        });
    }
}