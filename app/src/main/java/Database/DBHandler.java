package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import Model.RBooks;
import Model.Users;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "bookworm.db";

    public DBHandler(Context context){
        super(context, DB_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_bookinfo = "CREATE TABLE " + BookWormMaster.Book.TABLE_NAME + " (" +
                BookWormMaster.Book._ID + " INTEGER PRIMARY KEY, " +
                BookWormMaster.Book.COLUMN_NAME_TITLE + " TEXT, " +
                BookWormMaster.Book.COLUMN_NAME_AUTHOR + " TEXT, " +
                //price
                BookWormMaster.Book.COLUMN_NAME_PRICE + " TEXT, " +
                //pages
                BookWormMaster.Book.COLUMN_NAME_PAGES + " TEXT, " +
                //review
                BookWormMaster.Book.COLUMN_NAME_REVIEW + " TEXT);";

        db.execSQL(create_table_bookinfo);

        //osanda's create table method
        String create_table_category = "CREATE TABLE " + BookWormMaster.Category.TABLE_NAME_CAT + " ("+
                BookWormMaster.Category._ID + " INTEGER PRIMARY KEY, " +
                BookWormMaster.Category.COLUMN_NAME_CATNAME + " TEXT);";

        db.execSQL(create_table_category);
        //end osanda's methods

        String create_table_Read = "CREATE TABLE " + BookWormMaster.ReadBook.TABLE_RBOOK + " ("+
                BookWormMaster.ReadBook._ID + " INTEGER PRIMARY KEY, " +
                BookWormMaster.ReadBook.COLUMN_RNAME + " TEXT, " +
                BookWormMaster.ReadBook.COLUMN_RAUTHOR + " TEXT, " +
                BookWormMaster.ReadBook.COLUMN_RFROM + " TEXT, " +
                BookWormMaster.ReadBook.COLUMN_RTILL + " TEXT, " +
                BookWormMaster.ReadBook.COLUMN_RGENRE + " TEXT);";

        db.execSQL(create_table_Read);
        Log.i( "DB" , create_table_Read );

        String create_table_wishList = "CREATE TABLE " + BookWormMaster.AddWishList.TABLE_WList + " ("+
                BookWormMaster.AddWishList._ID + "INTEGER PRIMARY KEY, " +
                BookWormMaster.AddWishList.COLUMN_Title + " TEXT, " +
                BookWormMaster.AddWishList.COLUMN_Author + " TEXT " +
                BookWormMaster.AddWishList.COLUMN_Price + " TEXT ); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXiSTS " + BookWormMaster.ReadBook.TABLE_RBOOK);
       // onCreate(db);
    }

    public boolean addBook(String title, String author, String price, String pages, String review){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BookWormMaster.Book.COLUMN_NAME_TITLE, title);
        contentValues.put(BookWormMaster.Book.COLUMN_NAME_AUTHOR, author);
        contentValues.put(BookWormMaster.Book.COLUMN_NAME_PRICE, price);
        contentValues.put(BookWormMaster.Book.COLUMN_NAME_PAGES, pages);
        contentValues.put(BookWormMaster.Book.COLUMN_NAME_REVIEW, review);

        long result = db.insert(BookWormMaster.Book.TABLE_NAME, null, contentValues);

        if(result > 0){
            return true;
        }else{
            return false;
        }

    }


    //==================================OSANDA's display catogory method | Do not edit this=============================
    public ArrayList<Users> readAllInfor() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {BookWormMaster.Category._ID,BookWormMaster.Category.COLUMN_NAME_CATNAME};

        String sortOrder = BookWormMaster.Category.COLUMN_NAME_CATNAME;

        Cursor values = db.query(BookWormMaster.Category.TABLE_NAME_CAT,projection,null,null,null,null,sortOrder);

        ArrayList<Users> users = new ArrayList<Users>();

        while (values.moveToNext()){
            String userName = values.getString(values.getColumnIndexOrThrow(BookWormMaster.Category.COLUMN_NAME_CATNAME));
            users.add( new Users(userName) );

        }
        return users;
    }

    public void deleteuser(String username){
        SQLiteDatabase db = getReadableDatabase();
        String Selection = BookWormMaster.Category.COLUMN_NAME_CATNAME + " LIKE ?";
        String[] SelectionArgs = { username };

        db.delete(BookWormMaster.Category.TABLE_NAME_CAT , Selection , SelectionArgs );
    }

    public void deleteRead(int id){
        SQLiteDatabase db = getReadableDatabase();

        String Selection = BookWormMaster.ReadBook._ID + " = ?";
        String[] SelectionArgs = { String.valueOf(id) };

        db.delete(BookWormMaster.ReadBook.TABLE_RBOOK , Selection ,SelectionArgs );
        Log.i( "DB", "Delete : " + id );
    }

    public void userUpdate(String username ){
        SQLiteDatabase db  = getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BookWormMaster.Category.COLUMN_NAME_CATNAME , username );

        String Selection = BookWormMaster.Category.COLUMN_NAME_CATNAME + " Like ? ";
        Log.i("DB" , Selection  );
        String[] SelectionArgs = { username };

        db.update(BookWormMaster.Category.TABLE_NAME_CAT ,contentValues , Selection , SelectionArgs);

    }
    //=========================================OSANDA's display methos is over==============================



    public boolean addCategory(String name){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BookWormMaster.Category.COLUMN_NAME_CATNAME, name);

        long result = db.insert(BookWormMaster.Category.TABLE_NAME_CAT, null, contentValues);

        if(result > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addReadB(String name,String author,String dfrom,String dtill,String genre){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BookWormMaster.ReadBook.COLUMN_RNAME,name);
        contentValues.put(BookWormMaster.ReadBook.COLUMN_RAUTHOR,author);
        contentValues.put(BookWormMaster.ReadBook.COLUMN_RFROM,dfrom);
        contentValues.put(BookWormMaster.ReadBook.COLUMN_RTILL,dtill);
        contentValues.put(BookWormMaster.ReadBook.COLUMN_RGENRE,genre);

        long result = db.insert(BookWormMaster.ReadBook.TABLE_RBOOK,null,contentValues);

        if (result > 0){
            return true;
        }else {
            return false;
        }

    }
    //=============================kavindi=====================================

    public boolean AddWishList(String Title, String Author, double Price){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BookWormMaster.AddWishList.COLUMN_Title,Title);
        contentValues.put(BookWormMaster.AddWishList.COLUMN_Author,Author);
        contentValues.put(BookWormMaster.AddWishList.COLUMN_Price, Price);

        long result = db.insert(BookWormMaster.AddWishList.TABLE_WList,null,contentValues);

        if(result > 0){
            return true;
        }
        else {
            return false;
        }


    }

    //Adi---------------------------------------------------------------------------------------------
    public ArrayList<RBooks> readAllRbooks() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {BookWormMaster.ReadBook.COLUMN_RNAME , BookWormMaster.ReadBook._ID };

        String sortOrder = BookWormMaster.ReadBook.COLUMN_RNAME;

        Cursor values = db.query(BookWormMaster.ReadBook.TABLE_RBOOK ,projection,null,null,null,null,sortOrder);

        ArrayList<RBooks> books = new ArrayList<RBooks>();

        while (values.moveToNext()){
            RBooks book = new RBooks();
            String bookName = values.getString( values.getColumnIndexOrThrow( BookWormMaster.ReadBook.COLUMN_RNAME ));
            book.setName( bookName);
            book.setID( values.getInt( values.getColumnIndexOrThrow(BookWormMaster.ReadBook._ID) )  );
            books.add( book );


        }
        return books;
    }



}
