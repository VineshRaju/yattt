package xyz.vinesh.yattt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TicTakToeBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = new TicTakToeBoard(this);
        setContentView(board);
    }
}
