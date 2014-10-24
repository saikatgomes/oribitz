import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class AirportGraph {

	HashMap<String, Airport> airport_list = new HashMap<String, Airport>();

	AirportGraph() {
		//do nothing!
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
		int hrs=-1;// can use Integer.MAX_VALUE to denote infinity
		Airport a = airport_list.get(origin);
		if(a!=null){
			hrs=a.getHoursTo(destination);
		}				
		return hrs;
	}
	
	public int getShortestHours(String origin, String destination){
		int hrs=-1;// can use Integer.MAX_VALUE to denote infinity
		Airport a = airport_list.get(origin);
		if(a!=null){
			hrs=a.getHoursTo(destination);
		}		
		return hrs;
	}
	
	public String[] getAirportsNStopsAway(String origin, int nStops){
		String[] airpts = null;
		//do stuff!
		return airpts;
	}
	
	public String getLongestPath(String origin){
		String lgstPath=null;
		//do stuff!
		return lgstPath;
	}

}
