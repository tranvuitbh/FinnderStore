package com.example.vutran.finderawsome.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.vutran.finderawsome.Model.ModelStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VuTran on 6/21/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "faststore2";
    private static final String TABLE_NAME = "stores";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String SITE = "site";
    private static final String SAVED = "saved";
    private static final String IDUSER ="iduser";
    private static int VERSION = 1;
    private Context context;


    // Contructor để gọi sử dụng
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    // Truy vấn không trả về kết quả: CREATE, INSERT, UPDATE, DELETE
    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //  Truy vấn trả về kết quả: SELECT
    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    // Tạo ra bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " TEXT PRIMARY KEY, " +
                NAME + " TEXT, " +
                ADDRESS + " TEXT, " +
                SITE + " TEXT, " +
                SAVED + " TEXT, " +
                IDUSER +" TEXT)";
        db.execSQL(SQLQuery);
        Log.d("testsqlite", "Create table successfully");
    }

    // Xóa bảng trong database nếu đã tồn tại
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Thêm dữ liệu vào database
    public void addStore(ModelStore store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, store.getId());
        values.put(NAME, store.getName());
        values.put(ADDRESS, store.getAddress());
        values.put(SITE, store.getSite());
        values.put(SAVED, store.getSaved());
        values.put(IDUSER,store.getIdUser());

        db.insert(TABLE_NAME, null, values);
        Log.d("testsqlite", "Create store successfully");
        db.close();
    }

    // Đọc dữ liệu
    public List<DBModelStore> getAllStores(String idUser) {
        List<DBModelStore> listStores = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+SAVED+"=1 AND " +IDUSER+"='"+idUser+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DBModelStore store = new DBModelStore();
                store.setId(cursor.getString(0));
                store.setName(cursor.getString(1));
                store.setAddress(cursor.getString(2));
                store.setSite(cursor.getString(3));
                store.setSaved(cursor.getString(4));
                store.setIdUser(cursor.getString(5));

                listStores.add(store);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listStores;
    }
    // delete store
    public int deleteStore(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[]{String.valueOf(id)});
    }
    // UPDATE STORE CHANGE VALUE OF SAVED
    public void updateSaved(String id, String idUser){
        SQLiteDatabase db =  getWritableDatabase();
        String sqlupdate = "UPDATE "+TABLE_NAME+
                           " SET "+SAVED+"='1' " +
                           "WHERE "+ID+"='"+id+"' AND "+IDUSER+ " = '"+idUser+"'" ;
        db.execSQL(sqlupdate);
    }
}
