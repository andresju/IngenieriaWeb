package bigws.todo;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import todo.Note;
import todo.NoteList;

@WebService
public class ToDoService {
	private NoteList notes;

	public ToDoService() {
		notes = new NoteList();
	}

	@WebMethod()
	public String addNote(@WebParam(name = "task") String task,
				@WebParam(name = "context") String context,
				@WebParam(name = "project") String project,
				@WebParam(name = "priority") int priority) {
		notes.add(new Note(task, context, project, priority));
		return "Nota añadida";
	}

	@WebMethod()
	public List<Note> listNotes() {
		return notes.getAllNotes();
	}
	
	@WebMethod()
	public String countNotes() {
		return "Hay " + notes.num() + " notas";
	}

	@WebMethod()
	public String deleteNote(@WebParam(name = "task") String task) {
		return "Se han borrado " + notes.deleteNote(task) + " notas";
	}
}
