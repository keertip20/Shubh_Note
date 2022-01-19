package in.xparticle.shubhnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;


public class DataBaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="Item.db";
    public final static String TABLE_NAME="Itemtable";
    public final static String COL_1="ID";
    public final static String COL_2="ITEM";
    public final static String COL_3="CHECKBOX";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," + "ITEM," + "CHECKBOX)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {   sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    /**
     * this method is to save data in database
     * */

    public boolean insertData(String item,String checkbox){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_2,item);
        contentValues.put(COL_3,checkbox);


        long result=db.insert(TABLE_NAME,null, contentValues);
        if (result==-1){
            return false;
        } else {
            return true;
        }
    }

    /**
     *  this method is to get all data from database
     * */
    public Cursor getAllData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return cursor;
    }
    /**
     * this function is to update data
     * */
    public boolean updateData(String id, String checkboxState){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_3,checkboxState);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;

    }
    /**
     * this function is to delete data
     * */
    public Integer deleteData(String id){
        SQLiteDatabase db= this.getWritableDatabase();

        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }

}