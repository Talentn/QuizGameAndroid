package com.example.questionnaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz_Page extends AppCompatActivity {
    TextView time, correct, wrong;
    TextView question, a,b,c,d;
    Button next,finish;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://questionnaire-f7c30-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference databaseReference = database.getReference().child("Questions"); // reach the child of the db
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference databaseReferenceSecond = database.getReference();
    String quizQuestion;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String quizCorrectAnswer;
    int questionCount;
    int questionNumber = 1;
    String userAnswer;
    int userCorrect = 0 ;
    int userWrong = 0;
    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 15000;
    Boolean timerContinue;
    long leftTime = TOTAL_TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        time = findViewById(R.id.textView);
        correct = findViewById(R.id.textView5);
        wrong = findViewById(R.id.textView7);
        question = findViewById(R.id.textView8);
        a = findViewById(R.id.textView9);
        b = findViewById(R.id.textView10);
        c = findViewById(R.id.textView11);
        d = findViewById(R.id.textView12);
        next = findViewById(R.id.button3);
        finish = findViewById(R.id.button4);
        game();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                game();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendScore();
                Intent i = new Intent(Quiz_Page.this, Score_Page.class);
                startActivity(i);
                finish();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pauseTimer();

                userAnswer = "a";
                if (quizCorrectAnswer.equals(userAnswer)){
                    a.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+ userCorrect);
                }else{
                    a.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "b";
                if (quizCorrectAnswer.equals(userAnswer)){
                    b.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+ userCorrect);
                }else{
                    b.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "c";
                if (quizCorrectAnswer.equals(userAnswer)){
                    c.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+ userCorrect);
                }else{
                    c.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "d";
                if (quizCorrectAnswer.equals(userAnswer)){
                    d.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+ userCorrect);
                }else{
                    d.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });
    }

    public void game(){

        startTimer();

        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                questionCount = (int) dataSnapshot.getChildrenCount();

                quizQuestion = dataSnapshot.child(String.valueOf(questionNumber)).child("q")
                        .getValue().toString();
                quizAnswerA = dataSnapshot.child(String.valueOf(questionNumber)).child("a")
                        .getValue().toString();
                quizAnswerB = dataSnapshot.child(String.valueOf(questionNumber)).child("b")
                        .getValue().toString();
                quizAnswerC = dataSnapshot.child(String.valueOf(questionNumber)).child("c")
                        .getValue().toString();
                quizAnswerD = dataSnapshot.child(String.valueOf(questionNumber)).child("d")
                        .getValue().toString();
                quizCorrectAnswer = dataSnapshot.child(String.valueOf(questionNumber)).child("answer")
                        .getValue().toString();

                question.setText(quizQuestion);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);
                
                if(questionNumber < questionCount){
                    questionNumber++;
                }
                else {
                    Toast.makeText(Quiz_Page.this, "Vous avez répondu à toutes les questions.",
                             Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Quiz_Page.this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // method to make correct answer green alala
    public void findAnswer()
    {
        if (quizCorrectAnswer.equals("a")){
            a.setBackgroundColor(Color.GREEN);
        } else if (quizCorrectAnswer.equals("b")) {
            b.setBackgroundColor(Color.GREEN);
        } else if (quizCorrectAnswer.equals("c")) {
            c.setBackgroundColor(Color.GREEN);
        } else {
            d.setBackgroundColor(Color.GREEN);
        }
    }
    public void startTimer(){
        countDownTimer = new CountDownTimer(leftTime,1000) {
            @Override
            public void onTick(long l) {
                leftTime = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerContinue = false ;
                pauseTimer();
                question.setText("Désolé, le temps est écoulé.");

            }
        }.start();

        timerContinue = true;
    }

    public void resetTimer(){
        leftTime = TOTAL_TIME;
        updateCountDownText();
    }
    public void updateCountDownText(){
        int second = (int)( leftTime / 1000)%60;
        time.setText(""+second);
    }
    public void pauseTimer(){
        countDownTimer.cancel();
        timerContinue = false;
    }
    
    public void sendScore(){
        String userUID = user.getUid();
        databaseReferenceSecond.child("scores").child(userUID)
                .child("correct").setValue(userCorrect)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Quiz_Page.this, "Score envoyeé avec succée", Toast.LENGTH_SHORT).show();
                            }
                        });
        databaseReferenceSecond.child("scores").child(userUID)
                .child("wrong").setValue(userWrong);
        
    }
}