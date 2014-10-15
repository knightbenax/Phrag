package com.ephod.phrag;

public class Task {

	public String id;
    public String content;
    public String date;
    public String tag;
    public String finished;
    
    public Task(String id, String content, String date, String tag, String finished) {
        this.content = content;
        this.date = date;
        this.tag = tag;
        this.id = id;
        this.finished = finished;
    }
	
}
