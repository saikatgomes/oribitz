import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class AirportGraph {

	HashMap<String, Airport> airport_list = new HashMap<String, Airport>();

	AirportGraph() {

	}

	public void addConnection(String origin, String destination, int hrs) {
		if (airport_list.containsKey(origin)) {
			Airport a = airport_list.get(origin);
			a.addConnection(destination, hrs);
		} else {
			Airport a = new Airport(origin);
			a.addConnection(destination, hrs);
			airport_list.put(origin, a);
		}
	}

	public void print() {
		Iterator<Entry<String, Airport>> iterator = airport_list.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Airport> entry = iterator.next();
			Airport a = (Airport) entry.getValue();
			a.print();
		}
	}
	
	public int getDirectHours(String origin, String destination){
		int hrs=-1;
		
		return hrs;
	}

}
