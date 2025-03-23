package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.example.project_ui_implementation.model.UserInSession;
import com.example.project_ui_implementation.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homePage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    private List<Books> bookList;


    private RecyclerView bottom_recyclerView;

    private ArrayList<Books> bottom_bookList;

    private BookAdapter bottom_BookAdapter;

    private Users CurrentUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView fieldWelcomeMessage;

    TextView fieldRecommendation;

    private BookApiService bookApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        Intent getIntent = getIntent();
        CurrentUser = (Users) getIntent.getSerializableExtra("CurrentUser");

        //Global Variable that set to the current User
        UserInSession.sessionUser = CurrentUser;


        //All the necessary information about a user
        String currentUsername = CurrentUser.getUsername();
        ArrayList<String> CG = CurrentUser.getGenre();






        StringBuilder welcomeMessage = new StringBuilder();

        fieldWelcomeMessage = findViewById(R.id.welcomeMessage);
        fieldRecommendation = findViewById(R.id.txtRecommendation);

        StringBuilder txtUserRecommendation= new StringBuilder();
        txtUserRecommendation.append("From ");
        txtUserRecommendation.append(currentUsername);
        txtUserRecommendation.append("'s Last Activity...");
        fieldRecommendation.setText(txtUserRecommendation.toString());


        welcomeMessage.append(fieldWelcomeMessage.getText().toString());
        welcomeMessage.append(" ");
        welcomeMessage.append(currentUsername);
        fieldWelcomeMessage.setText(welcomeMessage.toString());



        //-----------------------//
        //Setting the environment of the database.
        database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("Users");

        recyclerView = findViewById(R.id.bookCarousel);
        bottom_recyclerView = findViewById(R.id.bookCarousel2);
        Button search = findViewById(R.id.searchBtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(homePage.this, "Book Search Started", Toast.LENGTH_LONG).show();
                Intent goSearch = new Intent(homePage.this, SearchTest.class);
                startActivity(goSearch);
            }
        });

        bookList = new ArrayList<>();
        bottom_bookList = new ArrayList<>();
        /**

        bookList.add(new Books("Book Number One", "Test Author1", "Fantasy"));
        bookList.add(new Books("Book Number Two", "Test Author2", "Non-Fiction"));
        bookList.add(new Books("Book Number Three", "Test Author3", "Biography"));
        bookList.add(new Books("Book Number Four", "Test Author4", "Fiction"));

         **/
        bookApiService = RetrofitClient.getInstance().create(BookApiService.class);


        //This would simply use the last genre entered inside the array, meaning
        //the most recent one.
        //String lastGenre = CurrentGenre.get(CurrentGenre.size()-1);
        String lastGenre = CG.get(CG.size() -1);

        bookApiService.searchBooks(lastGenre).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bottom_bookList = (ArrayList<Books>) response.body().getBooks();
                    bottom_BookAdapter = new BookAdapter(homePage.this);
                    bottom_BookAdapter.setBooks(bottom_bookList);
                    bookAdapter.setWideBook(false);
                    bottom_recyclerView.setAdapter(bottom_BookAdapter);
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(homePage.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        bottom_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false ));

        bookAdapter = new BookAdapter(homePage.this);
        bookAdapter.setBooks(bookList);
        bookAdapter.setWideBook(false);
        //bookAdapter = new BookAdapter(bookList);

        //Use a specific layout



        recyclerView.setAdapter(bookAdapter);
        fetchBooks();
    }

    private void fetchBooks() {
        DatabaseReference booksReference = database.getReference("Books");
        booksReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Books> updatedList = new ArrayList<>();
                for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                    String title = bookSnapshot.getKey();
                    String author = bookSnapshot.child("author").getValue(String.class);
                    String genre = bookSnapshot.child("genre").getValue(String.class);
                    String description = bookSnapshot.child("description").getValue(String.class);
                    String thumbnail = bookSnapshot.child("thumbnail").getValue(String.class);
                    HashMap<String, Float> ratings = (HashMap<String, Float>) bookSnapshot.child("ratings").getValue();
                    Float averageRating = bookSnapshot.child("averageRating").getValue(Float.class);
                    /**
                    float averageRating = 0.0f;
                    if (averageRatingStr != null) {
                        averageRating = Float.parseFloat(averageRatingStr);
                    }
**/
                    Books book = new Books(title, author, genre, thumbnail, description, averageRating);
                    updatedList.add(book);
                    }

                Collections.sort(updatedList, (b1, b2) -> Float.compare(b2.getRate(), b1.getRate()));

                if (updatedList.size() > 10) {
                    updatedList = new ArrayList<>(updatedList.subList(0, 10));
                }

                bookList.clear();
                bookList.addAll(updatedList);
                bookAdapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homePage.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }
}