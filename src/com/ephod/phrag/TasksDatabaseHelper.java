package com.ephod.phrag;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class TasksDatabaseHelper {
	Context m = null;
	TaskReaderDbHelper mDbHelper;
	long newRowId;
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
	    TaskEntry.COLUMN_NAME_TASK_ID + " INTEGER PRIMARY KEY," +
	    TaskEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
	    TaskEntry.COLUMN_NAME_DATE + TEXT_TYPE +
	    //TaskEntry.COLUMN_NAME_TYPE + TEXT_TYPE +
	    
	    //... // Any other options for the CREATE command
	    " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;
	
	
	public TasksDatabaseHelper(Context context){
		m = context;
		mDbHelper = new TaskReaderDbHelper(m);
	}
	
	//public void PutStuffInTheDb(String id, String content, String date, String type){
	public void PutStuffInTheDb(String content, String date){
		//get the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		//create a map of values, where column names are the keys
		ContentValues values = new ContentValues();
		//values.put(TaskEntry.COLUMN_NAME_TASK_ID, id);
		values.put(TaskEntry.COLUMN_NAME_CONTENT, content);
		values.put(TaskEntry.COLUMN_NAME_DATE, date);
		//values.put(TaskEntry.COLUMN_NAME_TYPE, type);
		
		//insert the new row, returning the primary key value of the new row
		newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);
		db.close();
	}
	
	public void getSingleTask(String id, String Task, String TaskId){
		//int IdInt = Integer.parseInt(id);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] columns = {TaskEntry.COLUMN_NAME_TASK_ID, TaskEntry.COLUMN_NAME_CONTENT, TaskEntry.COLUMN_NAME_DATE};
		String selection  = TaskEntry.COLUMN_NAME_TASK_ID + "=?";
		String[] selectionArgs = {id};
		Cursor cursor = db.query(TaskEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		TaskId = cursor.getString(0);
		Task = cursor.getString(1);
	}
	
	public int updateSingleTask(String id, String content, String date, String type){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_CONTENT, content);
        values.put(TaskEntry.COLUMN_NAME_DATE, date);
 
        // updating row
        return db.update(TaskEntry.TABLE_NAME, values, TaskEntry.COLUMN_NAME_TASK_ID + " = ?", new String[] {id});
	}
	
	
	public void deleteTask(String id){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(TaskEntry.TABLE_NAME, TaskEntry.COLUMN_NAME_TASK_ID + " = ?", new String[] {id});
		db.close();
	}
	
	public int getTaskCount(){
		String selectQuery = "SELECT * FROM " + TaskEntry.TABLE_NAME;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		return cursor.getCount();
	}
	
	
	public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "Tasks";
        public static final String COLUMN_NAME_TASK_ID = "id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";
        //public static final String COLUMN_NAME_TYPE = "type";
    }
	
	
	public void getAllTasks(List<String> tasks, List<String> taskIds){ //we would populate the List Array that holds the tasks and the List Array that holds the id from here
		String sortOrder = TaskEntry.COLUMN_NAME_TASK_ID + " DESC";
		String selectQuery = "SELECT * FROM " + TaskEntry.TABLE_NAME + " ORDER BY " + sortOrder;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//looping through all rows and adding to the list
		if(cursor.moveToFirst()){
			do{
				taskIds.add(cursor.getString(0)); //add the IDs of the task
				tasks.add(cursor.getString(1)); //add the content of the task
			} while (cursor.moveToNext());
		}
	}
	
	
	
	public class TaskReaderDbHelper extends SQLiteOpenHelper {
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "PhragReader.db";

	    public TaskReaderDbHelper(Context context) {
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





	



