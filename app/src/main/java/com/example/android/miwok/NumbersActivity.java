package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    /*to handle playback of all sound files*/
    private MediaPlayer mMediaPlayer;

    /*Handles audio focus when playing a sound file*/
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback and reset the player to start and the beginning of the file
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // This means we have regained audio focus and can Resume playback
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //This means we have lost audio focus so,
                        //stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    /*This listener gets triggered when the MediaPlayer has completed playing the audio file*/
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and Setup AudioManager to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create Array List of words. ArrayList does not need a predetermined size like in the case of arrays
        final ArrayList<Word> words = new ArrayList<Word>();

       // words.add("one");
        /*creating an instance of the Word Class in Word.java*/
        Word w = new Word("one","ken", R.drawable.number_two, R.raw.number_one);
        /*add the word to the array list*/
        words.add(w);

        words.add(new Word ("two","iba", R.drawable.number_two, R.raw.number_two)); /*shortened way of creating an object of the class and passing values*/
        words.add(new Word ("three","ita", R.drawable.number_three, R.raw.number_three));
        words.add(new Word ("four","inang", R.drawable.number_four, R.raw.number_four));
        words.add(new Word ("five","ition", R.drawable.number_five, R.raw.number_five));
        words.add(new Word ("six","iteken", R.drawable.number_six, R.raw.number_six));
        words.add(new Word ("seven","itiaiba", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word ("eight","itiaita", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word ("nine","anangken", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word ("ten","du'op", R.drawable.number_ten, R.raw.number_ten));

        //Array Adapter knows how to create layouts for each item in the List using the
        //simple_list_item_1.xml layout resource defined in the android framework
        //this list item  layout contains a single TextView which the adapter will set to display a
        //single word
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        //There should be a ListView with the View ID called List, which is declaredin
        //word_list.mxl layout file
        ListView listView = (ListView) findViewById(R.id.List);

        //make the ListView use the ArrayAdapter we created above, so that the ListView will display
        //list items for each word in the list of words. Do this by calling the setAdapter method on
        //the ListView object and pass in 1 argument, which is the ArrayAdapter with the variable name itemsAdapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Word word = words.get(position); //return position of a single word variable

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.==Music file type
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    //Release the media player if it currently exist because we are about to play a different sound file
                    releaseMediaPlayer();

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();

                    //Setup a listener on the media player,so that we can stop and release the MediaPlayer once the
                    // sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        //when the activity is stopped, release the media player resources because we won't be playing any more sounds
        super.onStop();
        releaseMediaPlayer();
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Abandon audio focus when playback is complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
