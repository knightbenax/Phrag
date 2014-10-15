package com.ephod.phrag;

import java.util.LinkedList;
import java.util.List;

import com.ephod.phrag.TagsDatabaseHelper.TagEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

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
	    TaskEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
	    TaskEntry.COLUMN_NAME_TAG + TEXT_TYPE + COMMA_SEP +
	    TaskEntry.COLUMN_FINISHED_VALUE + TEXT_TYPE +  " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;
	
	private static final String TAG_SQL_CREATE_ENTRIES =
		    "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
		    TagEntry.COLUMN_NAME_TAG_ID + " INTEGER PRIMARY KEY," +
		    TagEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    TagEntry.COLUMN_NAME_COLOR + TEXT_TYPE +
		    //TagEntry.COLUMN_NAME_TYPE + TEXT_TYPE +
		    
		    //... // Any other options for the CREATE command
		    " )";

		private static final String TAG_SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME;
	
	
	public TasksDatabaseHelper(Context context){
		m = context;
		mDbHelper = new TaskReaderDbHelper(m);
	}
	
	//public void PutStuffInTheDb(String id, String content, String date, String type){
	public void PutStuffInTheDb(String content, String date, String tag, String finished){
		//get the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		//create a map of values, where column names are the keys
		ContentValues values = new ContentValues();
		//values.put(TaskEntry.COLUMN_NAME_TASK_ID, id);
		values.put(TaskEntry.COLUMN_NAME_CONTENT, content);
		values.put(TaskEntry.COLUMN_NAME_DATE, date);
		values.put(TaskEntry.COLUMN_NAME_TAG, tag);
		values.put(TaskEntry.COLUMN_FINISHED_VALUE, finished);
		//values.put(TaskEntry.COLUMN_NAME_TYPE, type);
		
		//insert the new row, returning the primary key value of the new row
		newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);
		Log.d("Mushy",String.valueOf(newRowId));
		db.close();
	}
	
	public void getSingleTask(String id, String Task, String TaskId, String TaskDate, String Tag, String Finished){
		//int IdInt = Integer.parseInt(id);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] columns = {TaskEntry.COLUMN_NAME_TASK_ID, TaskEntry.COLUMN_NAME_CONTENT, TaskEntry.COLUMN_NAME_DATE, TaskEntry.COLUMN_NAME_TAG, TaskEntry.COLUMN_FINISHED_VALUE};
		String selection  = TaskEntry.COLUMN_NAME_TASK_ID + "=?";
		String[] selectionArgs = {id};
		Cursor cursor = db.query(TaskEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		TaskId = cursor.getString(0);
		Task = cursor.getString(1);
		TaskDate = cursor.getString(2);
		Tag = cursor.getString(3);
		Finished = cursor.getString(4);
	}
	
	public int updateSingleTask(String id, String content, String date, String type, String tag, String finished){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_CONTENT, content);
        values.put(TaskEntry.COLUMN_NAME_DATE, date);
        values.put(TaskEntry.COLUMN_NAME_TAG, tag);
        values.put(TaskEntry.COLUMN_FINISHED_VALUE, finished);
        
        // updating row
        return db.update(TaskEntry.TABLE_NAME, values, TaskEntry.COLUMN_NAME_TASK_ID + " = ?", new String[] {id});
	}

	public int updateSingleTaskFinished(String id, String finished){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_FINISHED_VALUE, finished);
        
        // updating row
        return db.update(TaskEntry.TABLE_NAME, values, TaskEntry.COLUMN_NAME_TASK_ID + " = ?", new String[] {id});
	}

	
	//update tags in the task table when you change them
	public int updateTaskTags(String previoustag, String tag){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_TAG, tag);
 
        // updating row
        return db.update(TaskEntry.TABLE_NAME, values, TaskEntry.COLUMN_NAME_TAG + " = ?", new String[] {previoustag});
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
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_FINISHED_VALUE = "finished";
        //public static final String COLUMN_NAME_TYPE = "type";
    }
	
	
	public List<Task> getAllTasks(){ //we would populate the List Array that holds the tasks and the List Array that holds the id from here
		List<Task> tasks = new LinkedList<Task>();
		String sortOrder = TaskEntry.COLUMN_NAME_TASK_ID + " DESC";
		String selectQuery = "SELECT * FROM " + TaskEntry.TABLE_NAME + " ORDER BY " + sortOrder;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//looping through all rows and adding to the list
		Task task = null;
		if(cursor.moveToFirst()){
			do{
				task = new Task(String.valueOf(cursor.getString(0)), String.valueOf(cursor.getString(1)), String.valueOf(cursor.getString(2)), String.valueOf(cursor.getString(3)), String.valueOf(cursor.getString(4)));
			//Log.d("Mushy ", String.valueOf(cursor.getString(0)));
				//Log.d("Mushy I", String.valueOf(cursor.getString(1)));
				//Log.d("Mushy II", String.valueOf(cursor.getString(2)));
				//Log.d("Mushy III", String.valueOf(cursor.getString(3)));
				//taskIds.add(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_TASK_ID))); //add the IDs of the task
				//tasks.add(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_CONTENT))); //add the content of the task
				//taskDates.add(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_DATE))); //add the content of the task
				//tags.add(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_TAG))); //add the content of the task
				tasks.add(task);
			} while (cursor.moveToNext());
		}
		return tasks;
	}
	
	
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
	
	
	public String getSingleTagColor(String Tag){
		//int IdInt = Integer.parseInt(id);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] columns = {TagEntry.COLUMN_NAME_TAG_ID, TagEntry.COLUMN_NAME_NAME, TagEntry.COLUMN_NAME_COLOR};
		String selection  = TagEntry.COLUMN_NAME_NAME + "=?";
		String[] selectionArgs = {Tag};
		Cursor cursor = db.query(TagEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		String TagColor = cursor.getString(2);
		return TagColor;
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
	
	public int updateSingleTag(String id, String name, String color){
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
	
	
	public List<Tag> getAllTags(){ //we would populate the List Array that holds the tasks and the List Array that holds the id from here
		List<Tag> tags = new LinkedList<Tag>();
		String sortOrder = TagEntry.COLUMN_NAME_TAG_ID + " DESC";
		String selectQuery = "SELECT * FROM " + TagEntry.TABLE_NAME + " ORDER BY " + sortOrder;
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//looping through all rows and adding to the list
		Tag tag = null;
		if(cursor.moveToFirst()){
			do{
				tag = new Tag(String.valueOf(cursor.getString(0)), String.valueOf(cursor.getString(1)), String.valueOf(cursor.getString(2)));
				tags.add(tag);
			} while (cursor.moveToNext());
		}
		
		return tags;
		
		
	}
	
	
	
	public class TaskReaderDbHelper extends SQLiteOpenHelper {
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "PhragReader";

	    public TaskReaderDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	        db.execSQL(TAG_SQL_CREATE_ENTRIES);
	    }
	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        db.execSQL(TAG_SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    
	    @Override
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
	
}





	



