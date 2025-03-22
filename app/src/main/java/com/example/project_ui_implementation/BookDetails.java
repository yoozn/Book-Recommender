package com.example.project_ui_implementation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class BookDetails extends AppCompatActivity {

    String title, author, description, genre, thumbnail;
    TextView vTitle, vAuthor, vDescription, vGenre;
    ImageView vThumbnail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vTitle = findViewById(R.id.titlePreview);
        vAuthor = findViewById(R.id.authorPreview);
        vDescription = findViewById(R.id.descriptionPreview);
        vThumbnail = findViewById(R.id.thumbnailPreview);

        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");
        genre = getIntent().getStringExtra("genre");
        thumbnail = getIntent().getStringExtra("thumbnail");

        vTitle.setText(title);
        vAuthor.setText(author);
        vDescription.setText(description);
        Glide.with(this).load(thumbnail).into(vThumbnail);

        //vGenre = findViewById(R.id.genrePreview);
    }
}