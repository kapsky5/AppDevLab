package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameStatus = true;
    int[] status = {2,2,2,2,2,2,2,2,2};
    boolean gameDraw=false;

    public void onTouch(View view){
        ImageView counter = (ImageView) view;
        int index = Integer.parseInt(counter.getTag().toString());
        LinearLayout resultBox = (LinearLayout) findViewById(R.id.result);
        TextView restext = (TextView) findViewById(R.id.resultText);

        if(status[index]==2 && gameStatus){
            counter.setTranslationY(-1000f);
            if(activePlayer==0){
                counter.setImageResource(R.drawable.cross3);
                status[index]=activePlayer;
                activePlayer=1;

            }
            else{
                counter.setImageResource(R.drawable.zero);
                status[index]=activePlayer;
                activePlayer=0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            int[][] winPos = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
            for(int i=0; i<winPos.length; i++){
                if(status[winPos[i][0]] == status[winPos[i][1]] && status[winPos[i][0]]==status[winPos[i][2]] && status[winPos[i][0]]!=2){
                    gameStatus = false;
                    String ans = "Player " + status[winPos[i][0]] + " Has Won !!!" ;
                    restext.setText(ans);
                    resultBox.setVisibility(View.VISIBLE);
                }
            }
        }
        gameDraw=true;
        for(int i=0; i<9; i++){
            if(status[i]==2){
                gameDraw=false;
            }
        }
        if(gameDraw){
            gameStatus = false;
            String ans = "Game is a Draw !!!" ;
            restext.setText(ans);
            resultBox.setVisibility(View.VISIBLE);
        }
    }

    public void playAgain(View view){
        LinearLayout resultBox = (LinearLayout) findViewById(R.id.result);
        activePlayer=0;
        for(int i=0; i<9; i++){
            status[i]=2;
        }
        resultBox.setVisibility(View.INVISIBLE);
        gameStatus = true;
        GridLayout zerox = findViewById(R.id.grid);
        for(int i=0; i<3*zerox.getColumnCount(); i++){
            ((ImageView) zerox.getChildAt(i)).setImageResource(0);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
