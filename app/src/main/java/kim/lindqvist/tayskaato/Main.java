package kim.lindqvist.tayskaato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.content.Context;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startEasy(View view) {
        Intent intent = new Intent(Main.this, Board.class);
        intent.putExtra("difficulty","easy");
        startActivity(intent);
    }

    public void startMedium(View view) {
        Intent intent = new Intent(Main.this, Board.class);
        intent.putExtra("difficulty","medium");
        startActivity(intent);
    }

    public void startHard(View view) {
        Intent intent = new Intent(Main.this, Board.class);
        intent.putExtra("difficulty","hard");
        startActivity(intent);
    }
}
