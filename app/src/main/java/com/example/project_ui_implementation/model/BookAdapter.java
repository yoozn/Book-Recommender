package com.example.project_ui_implementation.model;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_ui_implementation.BookDetails;
import com.example.project_ui_implementation.R;
import com.example.project_ui_implementation.SearchTest;
import com.example.project_ui_implementation.homePage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Books> bookList;

    //Adding a new attribute so that there is a way to check which Layout is needed.
    private boolean isWideBook;
    Context context;
    private boolean isEditable;





    /**
    public BookAdapter(List<Books> bookList) {
        this.bookList = bookList;
    }
     **/

    public BookAdapter(Context context) {
        this.context = context;
    }

    public void setBooks (List<Books> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    //Adding a method to set which display will be needed.
    public void setWideBook(boolean displayType){
        isWideBook = displayType;
    }

    //Depending on the value of the isWideBook then use a specific book.xml file
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int SelectedLayutID = isWideBook ? R.layout.wide_book : R.layout.book_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(SelectedLayutID, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Books book = bookList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());

        //holder.bookCover.setBackgroundColor(Color.parseColor("#FF5733")); // Purple-ish
        Log.d("GlideCheck", "Thumbnail URL: " + book.getThumbnail());
        Glide.with(holder.itemView.getContext()).load(book.getThumbnail()).into(holder.bookCover);

        if (isEditable) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
            DatabaseReference booksReference = database.getReference("Books");

            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);

            holder.editButton.setOnClickListener(v -> showEditDialog(holder.itemView.getContext(), book));
            holder.deleteButton.setOnClickListener(v -> {
                booksReference.child(book.getTitle()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Book deleted", Toast.LENGTH_SHORT).show();
                            bookList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, bookList.size()); // Adjust indexes
                        })
                        .addOnFailureListener(e -> Toast.makeText(context, "Failed deletion", Toast.LENGTH_SHORT).show());
            });
        }

        else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent i = new Intent(context, BookDetails.class);
                i.putExtra("title", book.getTitle());
                i.putExtra("thumbnail", book.getThumbnail());
                i.putExtra("author", book.getAuthor());
                String genre = book.getGenre();
                i.putExtra("genre", genre);

                i.putExtra("description", book.getDescription());
                i.putExtra("id", book.getId());
                context.startActivity(i);
                //Toast.makeText(context, "Title: " + book.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        }


    }


    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookAuthor;
        ImageView bookCover;
        Button editButton, deleteButton;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookCover = itemView.findViewById(R.id.bookCover);
            editButton = itemView.findViewById(R.id.editBookButton);
            deleteButton = itemView.findViewById(R.id.deleteBookButton);
        }
    }

    private void showEditDialog(Context context, Books book) {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        DatabaseReference booksReference = database.getReference("Books");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Book");

        View view = LayoutInflater.from(context).inflate(R.layout.activity_edit_book_dialog, null);
        EditText titleInput = view.findViewById(R.id.editTitle);
        EditText authorInput = view.findViewById(R.id.editAuthor);
        titleInput.setText(book.getTitle());
        authorInput.setText(book.getAuthor());

        builder.setView(view);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newTitle = titleInput.getText().toString().trim();
            String newAuthor = authorInput.getText().toString().trim();

            if (!newTitle.isEmpty() && !newAuthor.isEmpty()) {
                //HashMap<String, Object> updates = new HashMap<>();
                //updates.put("title", newTitle);
                //updates.put("author", newAuthor);
                /**
                booksReference.child(book.getTitle()).updateChildren(updates)
                                .addOnSuccessListener(aVoid -> {
                                    book.setTitle(newTitle);
                                    book.setAuthor(newAuthor);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "book updated", Toast.LENGTH_SHORT).show();
                                })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(context, "Failed to update book", Toast.LENGTH_SHORT).show();
                                        });
**/
                booksReference.child(book.getId()).child("title").setValue(newTitle);
                booksReference.child(book.getId()).child("author").setValue(newAuthor)
                        .addOnSuccessListener(aVoid -> {
                            book.setTitle(newTitle);
                            book.setAuthor(newAuthor);
                            notifyItemChanged(bookList.indexOf(book));
                        });
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


}
