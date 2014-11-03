package todo;

import java.util.ArrayList;
import java.util.List;

public class NoteList {
	private ArrayList<Note> list;
	
	public NoteList() {
		list = new ArrayList<Note>();
	}
	
	public void add(Note note) {
		int pos = 0;
		while (pos < list.size() && note.getPriority() > list.get(pos).getPriority()) {
			pos++;
		}
		list.add(pos, note);
	}
	
	public List<Note> getAllNotes() {
		return list;
	}
	
	public List<Note> getNotes(String task, String context, String project, Integer priority) {
		List<Note> resultado = new ArrayList<Note>();
		if (priority == null) {
			for (Note n : list) {
				if (n.getContext().contains(context) &&
						n.getProject().contains(project) &&
						n.getTask().contains(task)) {
					resultado.add(n);
				}
			}
		}
		else {
			int i = 0;
			while (i < list.size() && list.get(i).getPriority() < priority) {
				i++;
			}
			while (i < list.size() && list.get(i).getPriority() == priority) {
				resultado.add(list.get(i));
				i++;
			}
		}
		
		return resultado;
	}
	
	public int num() {
		return list.size();
	}
	
	public void deleteNote(int pos) {
		if (pos > 0 && pos <= list.size()) {
			list.remove(pos-1);
		}
	}

	public int deleteNote(String task) {
		int i = 0;
		int borradas = 0;
		while (i < list.size()) {
			if (list.get(i).getTask().equals(task)) {
				list.remove(i);
				borradas++;
			}
			else {
				i++;
			}
		}
		return borradas;
	}

}
