package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.controller.PrefsManager;
import com.example.trivia.data.AnswerListResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView, scoreCard;
    private TextView questionCounterTextView;
    private Button trueButton, falseButton;
    private ImageButton nextButton, prevButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
    CardView cardView;
    PrefsManager prefs;
    private int scoreCounter = 0;

    private int totalScore = 0;
    String APP_ID = "App_id";


    private Score score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        manageView();

        questionList = new QuestionBank().getQuestions(new AnswerListResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {


                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextView.setText(currentQuestionIndex + 1 + " / " + questionArrayList.size());
                totalScore = prefs.getInt(APP_ID);

                scoreCard.setText("Your Score is " + scoreCounter + " High Score " + totalScore);


                Log.d("Inside", "onCreate" + questionArrayList);


            }
        });


    }

    private void manageView() {
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
    }

    private void initView() {

        questionTextView = findViewById(R.id.question_textview);
        questionCounterTextView = findViewById(R.id.textview_counter);
        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_flase);
        nextButton = findViewById(R.id.imageButton_next);
        prevButton = findViewById(R.id.imageButton_prev);
        cardView = findViewById(R.id.cardView);
        prefs = new PrefsManager(getApplicationContext());
        scoreCard = findViewById(R.id.score_card);
        score = new Score();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_prev:


                if (currentQuestionIndex == 0) {
                    Toast.makeText(getApplicationContext(), "bsdk wanha kanha ja rha hai", Toast.LENGTH_SHORT).show();
                }

                if (currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();

                }


                break;

            case R.id.imageButton_next:

                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();

                break;

            case R.id.button_true:
                checkAnswer(true);


                break;

            case R.id.button_flase:
                checkAnswer(false);


                break;
        }
    }

    private void checkAnswer(boolean b) {
        boolean answerTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId = 0;
        if (b == answerTrue) {
            fadeView();
            toastMessageId = R.string.correct_Answer;
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            addPoint();
            updateQuestion();


        } else {
            shakeAnimation();
            toastMessageId = R.string.wrong_Answer;
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            subTractPoint();
            updateQuestion();


        }




       // prefs.putInt(APP_ID, totalScore);
        Toast.makeText(MainActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();


    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(currentQuestionIndex + 1 + " / " + questionList.size());




    }


    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_animation);

        cardView.setAnimation(shake);


        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);


            }
        });
    }

    private void fadeView() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(150);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);


            }
        });


    }


    private void addPoint() {
        scoreCounter += 10;
        score.setScore(scoreCounter);
        scoreCard.setText("Your Score is " +score.getScore()  + " High Score " + totalScore);


    }

    private void subTractPoint() {
        scoreCounter -= 10;

        score.setScore(scoreCounter);
        scoreCard.setText("Your Score is " +score.getScore()  + " High Score " + totalScore);


//        if (scoreCounter > 0) {
//
//
//        }else {
//            scoreCounter = 0;
//            score.setScore(scoreCounter);
//
//        }
    }


    @Override
    protected void onPause() {
        prefs.saveHigh(score.getScore());
        super.onPause();
    }
}





