package kim.lindqvist.tayskaato;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.content.Intent;
import android.database.Cursor;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;

public class Board extends AppCompatActivity {

    private float x1,x2,y1,y2;
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

        setContentView(R.layout.activity_game);
        TextView title = (TextView)findViewById(R.id.board_title);
        title.setText(card.getTitle());
        TextView description = (TextView)findViewById(R.id.board_description);
        description.setText(card.getDescription());

        // Set default image
        ImageView img = (ImageView) findViewById(R.id.board_image);
        int resID = getResources().getIdentifier(card.getImage() , "drawable", getPackageName());
        img.setImageResource(resID);
    }

    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                //if left to right sweep event on screen
                if (x1 < x2)
                {
                    Intent intent = new Intent(Board.this, Board.class);
                    intent.putExtra("difficulty","easy");
                    startActivity(intent);
                }
                break;
            }
        }
        return false;
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
