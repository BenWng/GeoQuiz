package com.example.ben.geoquiz;

/**
 * Created by Ben_Big on 8/30/16.
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){
        mTextResId=textResId;
        mAnswerTrue=answerTrue;
    }

    public void setTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public int getTextResId() {
        return mTextResId;
    }


    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
}
