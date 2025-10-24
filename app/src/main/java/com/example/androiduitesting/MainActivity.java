package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Views
    private ListView cityList;
    private EditText newName;
    private LinearLayout nameField;

    // Data
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        nameField = findViewById(R.id.field_nameEntry);
        newName   = findViewById(R.id.editText_name);
        cityList  = findViewById(R.id.city_list);

        // Set up data + adapter
        dataList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Add City → reveal input field
        final Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> nameField.setVisibility(View.VISIBLE));

        // Confirm → add city to list
        final Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(v -> {
            String cityName = newName.getText().toString().trim();
            if (!cityName.isEmpty()) {
                cityAdapter.add(cityName);
            }
            newName.getText().clear();
            nameField.setVisibility(View.INVISIBLE);
        });

        final Button deleteButton = findViewById(R.id.button_clear);
        deleteButton.setOnClickListener(v -> cityAdapter.clear());

        // OPEN ShowActivity when a list item is tapped
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String cityName = cityAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
            intent.putExtra(ShowActivity.EXTRA_CITY_NAME, cityName);
            startActivity(intent);
        });
    }
}
