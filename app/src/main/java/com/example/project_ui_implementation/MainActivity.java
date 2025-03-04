package com.example.project_ui_implementation;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView googleLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView googleLogin = findViewById(R.id.loginGoogle);
        String txtGL= googleLogin.getText().toString();
        SpannableString spannabletxtGL= new SpannableString(txtGL);
        spannabletxtGL.setSpan(new ForegroundColorSpan(Color.RED), 11, 25, 0);
        ClickableSpan ClicktxtGL= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "Redirecting", Toast.LENGTH_LONG).show();
            }
            };
        spannabletxtGL.setSpan(ClicktxtGL, 11, 25, 0);
        googleLogin.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        googleLogin.setText(spannabletxtGL);
    }
}