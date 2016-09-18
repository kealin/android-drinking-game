package kim.lindqvist.tayskaato;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataHelper mDbHelper;

    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getTask(String difficulty, int cardValue)
    {
        try
        {
            String sql ="SELECT title\n" +
                    "\t\t\t,description\n" +
                    "\t\t\t,card_images.image\n" +
                    " FROM rules\n" +
                    "\n" +
                    " INNER JOIN difficulty\n" +
                    " ON difficulty.rule_id = rules._id and difficulty.difficulty = '" + difficulty + "'\n" +
                    " \n" +
                    " INNER JOIN cards\n" +
                    " ON cards.rule_id = rules._id and cards.value = " + cardValue +"\n" +
                    " \n" +
                    " INNER JOIN card_images\n" +
                    " ON cards.value = card_images.card_value\n" +
                    " \n" +
                    " ORDER BY RANDOM() LIMIT 1;";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}