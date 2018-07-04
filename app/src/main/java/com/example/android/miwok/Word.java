package com.example.android.miwok;

/**
 * Created by VICTOR on 6/26/2018.
 * Represents a vocabulary word that the user wants to learn
 * It contains a default translation (English) and a miwok translation for that word
 */

public class Word {
    /*default translation for the word the little "m" in front is just a naming convention for
    * private variables*/
    private String mDefaultTranslation;

    /*miwok translation for the word*/
    private String mMiwokTranslation;

    /*Image Resoucrce ID dfor the word*/
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /*this is a constant variable declaration and that is why it is in all caps*/
    private static final int NO_IMAGE_PROVIDED = -1;

    /*Audio Resource Id for the word*/
    private int mAudioResourceId;

    /* First Constructor
    * @param defaultTranslation is the default language Example englis
    * @param miwokTranslation is the miwok language
    * @param audioResourceId is the resource Id for the audio file*/
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /*Second Constructor
    * @param defaultTranslation is the default language Example englis
    * @param miwokTranslation is the miwok language
    * @ param imageResourceId is the drawable resource ID for the image assigned*/
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /*
    * Get default translation of the word
    */
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    /*
    * Get miwok translation of the word
    */
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    /*
    * return the image resource ID of the word
    */
    public int getImageResourceId(){
        return mImageResourceId;
    }

    /*
    * returns whether or not, there is an image for this word
    */
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
    /*
     * returns the audio resource ID
     */
    public int getmAudioResourceId(){
        return mAudioResourceId;
    }
}
