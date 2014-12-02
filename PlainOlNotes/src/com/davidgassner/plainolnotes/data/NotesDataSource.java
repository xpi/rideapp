package com.davidgassner.plainolnotes.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesDataSource {

	private static final String PREFKEY = "notes";
	private SharedPreferences notePrefs;
	//构造器，context对应的activity
	public NotesDataSource(Context context) {
		//
		notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
	}
	
	public List<NoteItem> findAll() {
		//从SharedPreferences中获得键值集合
		
		Map<String, ?> notesMap = notePrefs.getAll();
		
	
		SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());
		
		List<NoteItem> noteList = new ArrayList<NoteItem>();
		for (String key : keys) {
			NoteItem note = new NoteItem();
			note.setKey(key);
			note.setText((String) notesMap.get(key));
			noteList.add(note);
		}
		
		return noteList;
		
	}
	
	public boolean update(NoteItem note) {
		
		if (note.getText().length() > 0) {
			//创建编辑器
			SharedPreferences.Editor editor = notePrefs.edit();
			//填充编辑器
			editor.putString(note.getKey(), note.getText());
			//提交内容
			editor.commit();
			return true;
		}
		else {
			return remove(note);
		}
		
	}
	//删除数据
	public boolean remove(NoteItem note) {
		
		if (notePrefs.contains(note.getKey())) {
			SharedPreferences.Editor editor = notePrefs.edit();
			editor.remove(note.getKey());
			editor.commit();
		}
		
		return true;
	}
	
	
}
