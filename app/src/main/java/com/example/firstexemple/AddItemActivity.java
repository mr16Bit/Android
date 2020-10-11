package com.example.firstexemple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity_test";
    public static final String KEY_TYPE = "type";

    private EditText name;
    private EditText price;
    private ImageButton add_button;
    private TextView rub;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        rub = findViewById(R.id.rub);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        add_button = findViewById(R.id.add_button);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.new_item);

        add_button.setEnabled(false);
        setTitle(R.string.new_item);
        name.addTextChangedListener(nameWatcher);
        price.addTextChangedListener(priceWatcher);
        add_button.setOnClickListener(addListener);
    }


/*===================ACTION METHOD==================*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String itemName = name.getText().toString();
            String itemPrice = price.getText().toString();
            String type = getIntent().getStringExtra(KEY_TYPE);
            Item item = new Item(itemName,itemPrice,type);

            Intent intent = new Intent();
            intent.putExtra("item",item);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };


    TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            add_button.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(price.getText().toString()));
        }
    };

    TextWatcher priceWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            add_button.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(name.getText().toString()));
        }
    };

}