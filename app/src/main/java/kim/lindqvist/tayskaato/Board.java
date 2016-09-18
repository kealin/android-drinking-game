package kim.lindqvist.tayskaato;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.content.Intent;
import android.database.Cursor;

import java.util.Random;

public class Board extends AppCompatActivity {

    private String difficulty;
    private static Context mContext;
    private DataAdapter mDbHelper;

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mDbHelper = new DataAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        mContext = getApplicationContext();
        setContentView(R.layout.activity_game);

        // Get difficulty from intent
        Intent myIntent = getIntent();
        difficulty = myIntent.getStringExtra("difficulty");

        Card card = randomCard();

        // Set default image
        ImageView img = (ImageView) findViewById(R.id.board_image);
        img.setImageResource(R.drawable.icon);
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public Card randomCard() {
        Card card = new Card();
        Random r = new Random();
        int value = getRandomWithExclusion(r, 0, 13, 1);
        Cursor cursor = mDbHelper.getTask(difficulty, value);

        if (cursor.moveToFirst()){
            do{
                card.setImage(cursor.getString(cursor.getColumnIndex("image")));
                card.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                card.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return card;
    }
}
