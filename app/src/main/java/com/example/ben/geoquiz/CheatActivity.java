package com.example.ben.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private Button mShowAnswerButton;
    private static final String EXTRA_ANSWER_IS_TRUE="com.example.ben.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.ben.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private boolean mIsCheater=false;
    private String KEY_ISCHEATER="isCheater";


    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i=new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To get saved data, after the activity has been destoryed
        if(savedInstanceState!=null){
            mIsCheater=savedInstanceState.getBoolean(KEY_ISCHEATER,false);
            setAnswerShownResult(mIsCheater);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mShowAnswerButton=(Button) findViewById(R.id.show_answer_button);

        mAnswerTextView=(TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                mIsCheater=true;
                setAnswerShownResult(mIsCheater);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ISCHEATER,mIsCheater);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);//RESULT_OK is from Activity.RESULT_OK
    }


    //This is just for QuizActivity to call
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

}
