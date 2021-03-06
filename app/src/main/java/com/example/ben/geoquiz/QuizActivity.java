package com.example.ben.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX="index";
    private static final String KEY_ISCHEATER="isCheater";
    //REQUEST_CODE_CHEAT does not do much here, it is used when activity starts more than one type
    // of child activity and needs to know who is reporting
    private static final int REQUEST_CODE_CHEAT=0;



    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;

    private Button mCheatButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex=0;
    private boolean mIsCheater;

    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };



    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answer =mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId=0;
        if(mIsCheater){
            messageResId=R.string.judgment_toast;
        }
        else {
            if (userPressedTrue == answer) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){ super.onStart(); Log.d(TAG,"onStart() called"); }

    @Override public void onPause() { super.onPause(); Log.d(TAG, "onPause() called"); }

    @Override public void onResume() { super.onResume(); Log.d(TAG, "onResume() called"); }

    @Override public void onStop() { super.onStop(); Log.d(TAG, "onStop() called"); }

    @Override public void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy() called"); }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex+1)% mQuestionBank.length;
                mIsCheater=false;
               updateQuestion();
            }
        });

        /*
        mPrevButton = (Button) findViewById(R.id.prev_button);
        if (mPrevButton!=null) {
            mPrevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            });
        }*/


        mCheatButton= (Button) findViewById(R.id.cheat_button);
        if (mCheatButton!=null){
            mCheatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

                    //Let newIntent generate the Intent
                    Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                    //Start cheat activity without getting any result from it
                    //startActivity(i);
                    //Start cheat activity getting result from it
                    startActivityForResult(i,REQUEST_CODE_CHEAT);
                }
            });
        }
        //To get saved data, after the activity has been destroyed
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater=savedInstanceState.getBoolean(KEY_ISCHEATER,false);
        }


        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_ISCHEATER,mIsCheater);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        /*if(id==R.id.action_settings){
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

}
