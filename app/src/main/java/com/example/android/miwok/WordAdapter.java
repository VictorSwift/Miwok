package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VICTOR on 6/29/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    /*Resource ID for the backgrond color to this list of words*/
    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Adapters recycle views to AdapterViews.
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //get the word object located at this position in the list
        Word currentWord = getItem(position);

        // find the textView in the list_item.xml layout
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // set the text from the array to the view item
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // find the textView in the list_item.xml layout
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        if (currentWord.hasImage()){
            //Set the ImageView to the image resource specified in the current word
            imageView.setImageResource(currentWord.getImageResourceId());

            //make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            //Otherwise hide the image
            imageView.setVisibility(View.GONE);
        }

        //Set the theme color for the list Item
        View textContainer = listItemView.findViewById(R.id.text_container);
        //find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        //Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        //Return the whole list item layout (containing two TextViews)
        //return the ListView
        return listItemView;
    }
}
