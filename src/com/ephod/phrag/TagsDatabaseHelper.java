package com.ephod.phrag;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class TagsDatabaseHelper {
	Context m = null;
	TagReaderDbHelper mDbHelper;
	long newRowId;
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
	    TagEntry.COLUMN_NAME_TAG_ID + " INTEGER PRIMARY KEY," +
	    TagEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
	    TagEntry.COLUMN_NAME_COLOR + TEXT_TYPE +
	    //TagEntry.COLUMN_NAME_TYPE + TEXT_TYPE +
	    
	    //... // Any other options for the CREATE command
	    " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME;
	
	
	public TagsDatabaseHelper(Context context){
		m = context;
		mDbHelper = new TagReaderDbHelper(m);
	}
	
	//public void PutStuffInTheDb(String id, String content, String date, String type){
	public void PutTagInTheDb(String name, String color){
		//get the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		//create a map of values, where column names are the keys
		ContentValues values = new ContentValues();
		//values.put(TagEntry.COLUMN_NAME_TASK_ID, id);
		values.put(TagEntry.COLUMN_NAME_NAME, name);
		values.put(TagEntry.COLUMN_NAME_COLOR, color);
		//values.put(TagEntry.COLUMN_NAME_TYPE, type);
		
		//insert the new row, returning the primary key value of the new row
		newRowId = db.insert(TagEntry.TABLE_NAME, null, values);
		
		db.close();
	}
	
	public void getSingleTag(String Tag, String TagId, String TagColor){
		//int IdInt = Integer.parseInt(id);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] columns = {TagEntry.COLUMN_NAME_TAG_ID, TagEntry.COLUMN_NAME_NAME, TagEntry.COLUMN_NAME_COLOR};
		String selection  = TagEntry.COLUMN_NAME_NAME + "=?";
		String[] selectionArgs = {Tag};
		Cursor cursor = db.query(TagEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		TagId = cursor.getString(0);
		//Tag = cursor.getString(1);
		TagColor = cursor.getString(2);
	}
	
	public void getSingleTagById(String id, String Tag, String TagId, String TagColor){
		//int IdInt = Integer.parseInt(id);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] columns = {TagEntry.COLUMN_NAME_TAG_ID, TagEntry.COLUMN_NAME_NAME, TagEntry.COLUMN_NAME_COLOR};
		String selection  = TagEntry.COLUMN_NAME_TAG_ID + "=?";
		String[] selectionArgs = {id};
		Cursor cursor = db.query(TagEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		TagId = cursor.getString(0);
		Tag = cursor.getString(1);
		TagColor = cursor.getString(2);
	}
	
	public int updateSingleTag(String id, String name, String color, String type){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(TagEntry.COLUMN_NAME_NAME, name);
        values.put(TagEntry.COLUMN_NAME_COLOR, color);
 
        // updating row
        return db.update(TagEntry.TABLE_NAME, values, TagEntry.COLUMN_NAME_TAG_ID + " = ?", new String[] {id});
	}
	
	
	public void deleteTag(String id){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(TagEntry.TABLE_NAME, TagEntry.COLUMN_NAME_TAG_ID + " = ?", new String[] {id});
		db.close();
	}
	
	public int getTagCount(){
		String selectQuery = "SELECT * FROM " + TagEntry.TABLE_NAME;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		return cursor.getCount();
	}
	
	
	public static abstract class TagEntry implements BaseColumns {
        public static final String TABLE_NAME = "Tags";
        public static final String COLUMN_NAME_TAG_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_COLOR = "color";
        //public static final String COLUMN_NAME_TYPE = "type";
    }
	
	
	public void getAllTags(List<String> tags, List<String> tagIds, List<String> tagColors){ //we would populate the List Array that holds the tasks and the List Array that holds the id from here
		String sortOrder = TagEntry.COLUMN_NAME_TAG_ID + " DESC";
		String selectQuery = "SELECT * FROM " + TagEntry.TABLE_NAME + " ORDER BY " + sortOrder;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//looping through all rows and adding to the list
		if(cursor.moveToFirst()){
			do{
				tagIds.add(cursor.getString(0)); //add the IDs of the task
				tags.add(cursor.getString(1)); //add the content of the task
				tagColors.add(cursor.getString(2)); //add the content of the task
			} while (cursor.moveToNext());
		}
	}
	
	
	
	public class TagReaderDbHelper extends SQLiteOpenHelper {
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 3;
	    public static final String DATABASE_NAME = "PhragReader.db";

	    public TagReaderDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
	
}





	



