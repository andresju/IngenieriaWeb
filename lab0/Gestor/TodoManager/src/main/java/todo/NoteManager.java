package todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.Gson;

public class NoteManager {

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		Gson gson = new Gson();
		NoteList notes = null;
		String ruta = "";
		
		do {
			System.out.print("Escriba el nombre del fichero: ");
			ruta = scan.nextLine();
		} while (ruta.equals(""));
		File f = new File(ruta);
		if (f.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				notes = gson.fromJson(br, NoteList.class);
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			while (!f.isFile()) {
				System.out.print("El fichero especificado no existe. Desea crearlo? (S/n): ");
				String resp = scan.nextLine();
				if (resp.equals("") || resp.toLowerCase().equals("s")) {
					try {
						f.createNewFile();
					} catch (IOException e) {
						do {
							System.out.print("El fichero no ha podido crearse, escoja una nueva ruta: ");
							ruta = scan.nextLine();
						} while (!ruta.equals(""));
					}
				}
				else if (resp.toLowerCase().equals("n")) {
					System.exit(0);
				}
			}
		}
		
		if (notes == null) {notes = new NoteList(); }
		
		int opcion = 0;
		do {
			try {
				ayuda();
				opcion = scan.nextInt();
				switch (opcion) {
				case 1: anadir(notes); break;
				case 2: listar(notes); break;
				case 3: buscar(notes); break;
				case 4: eliminar(notes); break;
				}
			} catch (InputMismatchException e) {
				scan.nextLine();
			}
			
		} while (opcion != 5);
		
		scan.close();
		
		
		try {
			Writer writer = new FileWriter(f);
			gson.toJson(notes, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void anadir(NoteList notes) {
		try {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.print("Nombre de la tarea: ");
			String nombre = scan.nextLine();
			System.out.print("Nombre del contexto: ");
			String contexto = scan.nextLine();
			System.out.print("Nombre del proyecto: ");
			String proyecto = scan.nextLine();
			System.out.print("Prioridad: ");
			int prioridad = scan.nextInt();
			notes.add(new Note(nombre, contexto, proyecto, prioridad));
		}
		catch (InputMismatchException e) {
			System.out.println("Error en la entrada. Nota no anadida");
		}
	}
	
	private static void listar(NoteList notes) {
		System.out.println("Id\tNombre\tCtx\tProy\tPrio");

		int i = 1;
		for (Note n : notes.getAllNotes()) {
			System.out.println(i + "\t" + n.getTask() + "\t" + n.getContext() + "\t"
					+ n.getProject() + "\t" + n.getPriority());
			i++;
		}
	}
	
	private static void buscar(NoteList notes) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Nombre de la tarea: ");
		String nombre = scan.nextLine();
		System.out.print("Nombre del contexto: ");
		String contexto = scan.nextLine();
		System.out.print("Nombre del proyecto: ");
		String proyecto = scan.nextLine();
		System.out.print("Prioridad: ");
		String prio = scan.nextLine();
		Integer prioridad = null;
		if (!prio.equals("")) {
			prioridad = Integer.parseInt(prio);
		}
		
		System.out.println("Nombre\tCtx\tProy\tPrio");
		for (Note n : notes.getNotes(nombre, contexto, proyecto, prioridad)) {
			System.out.println(n.getTask() + "\t" + n.getContext() + "\t"
					+ n.getProject() + "\t" + n.getPriority());
		}
	}
	
	private static void eliminar(NoteList notes) {
		try {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.print("Id de la nota a eliminar: ");
			int id = scan.nextInt();
			notes.eliminaNota(id);
		} catch (InputMismatchException e) {
			System.out.println("Id incorrecto");
		}
		
	}
	
	private static void ayuda() {
		System.out.println();
		System.out.println("Opciones:");
		System.out.println("\t1. Anadir nota");
		System.out.println("\t2. Listar notas");
		System.out.println("\t3. Buscar nota");
		System.out.println("\t4. Eliminar nota");
		System.out.println("\t5. Salir");
		System.out.println();
		System.out.print("Opcion: ");
	}

}
