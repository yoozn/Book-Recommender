package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ui_implementation.model.BookAdapter;
import com.example.project_ui_implementation.model.BookApiService;
import com.example.project_ui_implementation.model.BookResponse;
import com.example.project_ui_implementation.model.Books;
import com.example.project_ui_implementation.model.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTest extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookApiService bookApiService;

    private Button goHomebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        goHomebtn = findViewById(R.id.GoBack);
        goHomebtn.setOnClickListener(v -> {
            Intent goHomeIntent = new Intent(SearchTest.this, homePage.class);
            startActivity(goHomeIntent);
        });


        searchInput = findViewById(R.id.searchInput);
        Button searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.bookSearchResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter();
        recyclerView.setAdapter(bookAdapter);

        bookApiService = RetrofitClient.getInstance().create(BookApiService.class);
        searchButton.setOnClickListener(v -> searchBooks());
    }

    private void searchBooks() {
        String query = searchInput.getText().toString().trim();
        Toast.makeText(SearchTest.this, "Executing", Toast.LENGTH_SHORT).show();
        if (query.isEmpty()) {
            Toast.makeText(this, "Empty search", Toast.LENGTH_SHORT).show();
            return;
        }

        bookApiService.searchBooks(query).enqueue(new Callback<BookResponse>() {
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Books> books = response.body().getBooks();
                    bookAdapter.setBooks(books);
                    Toast.makeText(SearchTest.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(SearchTest.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}