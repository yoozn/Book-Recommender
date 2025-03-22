package com.example.project_ui_implementation.model;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_ui_implementation.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Books> bookList;

    //Adding a new attribute so that there is a way to check which Layout is needed.
    private boolean isWideBook;


    /**
    public BookAdapter(List<Books> bookList) {
        this.bookList = bookList;
    }
     **/
    public void setBooks (List<Books> books) {
        this.bookList = books;
        notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookAuthor;
        ImageView bookCover;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookCover = itemView.findViewById(R.id.bookCover);
        }
    }

}
