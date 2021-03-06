package bigws.todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/notes", "/anadir", "/eliminar", "/listar" })
public class SOAPClient extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String func = req.getServletPath();
		if (func.equals("/listar")) {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>Todas las tareas</title></head>"
					+ "<body><h1>Todas las tareas</h1>"
					+ "<table border=\"1\"><colgroup><col span=\"1\" style=\"background-color:blue\">"
					+ "</colgroup>"
					+ "<tr><th>ID</th>"
					+ "<th>Tarea</th>"
					+ "<th>Contexto</th>"
					+ "<th>Proyecto</th>"
					+ "<th>Prioridad</th></tr>"
					+ todasNotas()
					+ "</table><br/><a href=\"javascript:history.back()\">Atras</a>"
					+ "</body></html>");
		}
		else if (func.equals("/anadir")) {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			String task = req.getParameter("task");
			String context = req.getParameter("context");
			String project = req.getParameter("project");
			String priority = req.getParameter("priority");
			out.println("<html><head><title>Tareas</title></head>"
					+ "<body><h1>Tareas</h1>"
					+ anadirNota(task, context, project, priority)
					+ "<br/><br/><a href=\"javascript:history.back()\">Atras</a> "
					+ "<a href=\"/\">Inicio</a>"
					+ "</body></html>");
		} else if (func.equals("/eliminar")) {
			String task = req.getParameter("task");
			PrintWriter out = resp.getWriter();
			if (req.getParameter("confirm").equals("no")) {
				int cuenta = cuentaNotas(task);
				if (cuenta > 0) {
					out.println("<html><head>"
						+ "<title>Confimación eliminar</title></head>"
						+ "<body><h1>Atención!</h1>"
						+ "Se van a eliminar " + cuentaNotas(task) + " notas"
						+ "<br/>¿Está seguro?<br/>"
						+ "<br/><br/><a href=\"javascript:history.back()\">No </a> "
						+ "<form id=\"eliminar\" method=\"get\" action=\"eliminar\">"
						+ "<input type=\"hidden\" name=\"task\" value=\"" + task + "\"/>"
						+ "<input type=\"hidden\" name=\"confirm\" value=\"yes\"/>"
						+ "<a href=\"javascript:document.getElementById('eliminar').submit();\">Si</a>"
						+ "</form>"
						+ "</body></html>");
				}
				else {
					out.println("<html><head><title>Tareas</title></head>"
						+ "<body><h1>No se han encontrado notas</h1>"
						+ "<br/><br/><a href=\"javascript:history.back()\">Atras</a> "
						+ "<a href=\"/\">Inicio</a>"
						+ "</body></html>");
				}
			} else if (req.getParameter("confirm").equals("yes")) {
				out.println("<html><head><title>Tareas</title></head>"
					+ "<body><h1>Tareas</h1>"
					+ eliminaNotas(task)
					+ "<br/><br/><a href=\"eliminar.html\">Eliminar otra nota</a> "
					+ "<a href=\"/\">Inicio</a>"
					+ "</body></html>");
			}
		}
	}

	private int cuentaNotas(String task) {
		ToDoServiceService twss = new ToDoServiceService();
		ToDoService tws = twss.getToDoServicePort();
		return tws.fetchNotes(task);
	}

	private String anadirNota(String task, String context, String project, String priority) {
		ToDoServiceService twss = new ToDoServiceService();
		ToDoService tws = twss.getToDoServicePort();
		try {
			int prio = Integer.parseInt(priority);
			return tws.addNote(task, context, project, prio);
		} catch (NumberFormatException e) {
			return "Prioridad debe ser un número";
		}
	}

	private String eliminaNotas(String task) {
		ToDoServiceService twss = new ToDoServiceService();
		ToDoService tws = twss.getToDoServicePort();
		return tws.deleteNote(task);
	}

	private String todasNotas() {
		ToDoServiceService twss = new ToDoServiceService();
		ToDoService tws = twss.getToDoServicePort();
		String res = "";
		List<Note> notes = null;
		
		notes = tws.listNotes();
		
		int i = 1;
		for (Note n : notes) {
			res = res + "<tr><td>" + i + "</td>"
					+ "<td>" + n.getTask() + "</td>"
					+ "<td>" + n.getContext() + "</td>"
					+ "<td>" + n.getProject() + "</td>"
					+ "<td>" + n.getPriority() + "</td></tr>";
			i++;
		}
		return res;
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
