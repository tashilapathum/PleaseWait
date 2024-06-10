package com.tashila.pleasewait;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tashila.pleasewait.databinding.ActivityJavaMainBinding;

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