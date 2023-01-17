package com.example.productivityapp_ver12;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "listdb_ver2";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "liste";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DESCRIPTION_COL = "description";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addnewlist(String listName, String listDescription) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, listName);
        values.put(DESCRIPTION_COL, listDescription);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<lista> readListe() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<lista> listeArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                listeArrayList.add(new lista(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return listeArrayList;
    }

    public void updateCourse(String originallistid, String listName, String listDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, listName);
        values.put(DESCRIPTION_COL, listDescription);


        db.update(TABLE_NAME, values, "id=?", new String[]{originallistid});
        db.close();
    }

    public void deleteCourse(String listaid) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id=?", new String[]{listaid});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        //db.delete(TABLE_NAME,null,null);
        db.close();

    }
}
