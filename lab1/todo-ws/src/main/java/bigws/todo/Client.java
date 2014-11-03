package bigws.todo;

// import javax.xml.ws.BindingProvider;

public class Client {
 	
	public static void main(String[] args) {
		ToDoServiceService twss = new ToDoServiceService();
		ToDoService tws = twss.getToDoServicePort();

//		// If there is a TCP/IP Monitor 8090 -> 8080
//		String endpointURL = "http://localhost:8080/hellows/HelloWorld";
//		BindingProvider bp = (BindingProvider)hws;
//		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

		
		System.out.println(tws.countNotes());
	}

}
