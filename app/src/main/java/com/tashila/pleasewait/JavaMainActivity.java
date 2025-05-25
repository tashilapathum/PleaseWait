package com.tashila.pleasewait;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.tashila.pleasewait.sample.databinding.ActivityJavaMainBinding;

public class JavaMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJavaMainBinding binding = ActivityJavaMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.show.setOnClickListener(view -> showDialog());
    }

    private void showDialog() {
        JavaCustomDialog dialog = new JavaCustomDialog(this);
        dialog.show();
    }
}