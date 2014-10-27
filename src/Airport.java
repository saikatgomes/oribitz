import java.util.ArrayList;
import java.util.List;

/**
 * @author 		Saikat Gomes 
 * Email: 		saikatgomes@gmail.com 
 * Description: Stores an instance of an airport/city
 * 				each airport stores:
 * 					name
 * 					list of connection airport and time
 */
public class Airport {

	public List<String> connections = new ArrayList<String>();
	public List<Float> hours = new ArrayList<Float>();
	public String name;
	public int connectionCount = 0;

	Airport(String myName) {
		name = myName;
	}

	public void addConnection(String aName, float hrs) {
		connections.add(aName);
		hours.add(hrs);
		connectionCount++;
	}

	public void removeConnection(String aName) {
		connections.remove(aName);
	}

	public void updateConnection(String aName, float hrs) {
		// could be implemented more efficiently!
		removeConnection(aName);
		addConnection(aName, hrs);
	}

	// Hours to a neighboring airport
	public float getHoursTo(String aName) {
		int idx = connections.indexOf(aName);
		float hrs = -1f;// can use Integer.MAX_VALUE to denote infinity
		if (idx != -1) {
			hrs = hours.get(idx);
		}
		return hrs;
	}

	// Names of airport with flight out of here
	public String[] getAllConnections() {
		String[] connectionList = new String[connections.size()];
		for (int idx = 0; idx < connectionList.length; idx++) {
			connectionList[idx] = connections.get(idx);
		}
		return connectionList;
	}

	// Time take to reach neighboring airports
	public float[] getAllHours() {
		float[] hoursList = new float[hours.size()];
		for (int idx = 0; idx < hoursList.length; idx++) {
			hoursList[idx] = hours.get(idx);
		}
		return hoursList;
	}

	// Display this airport and destinations with hours
	public void print() {
		System.out.println("\tOrigin: " + name);
		System.out.println("\t\tDestination: ");
		for (int i = 0; i < connectionCount; i++) {
			System.out.println("\t\t--> " + connections.get(i) + " : "
					+ hours.get(i));
		}
	}

}
