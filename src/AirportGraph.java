import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

public class AirportGraph {

	HashMap<String, Airport> airport_list = new HashMap<String, Airport>();

	AirportGraph() {
		// do nothing!
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

	public int getDirectHours(String origin, String destination) {
		int hrs = -1;// can use Integer.MAX_VALUE to denote infinity
		Airport a = airport_list.get(origin);
		if (a != null) {
			hrs = a.getHoursTo(destination);
		}
		return hrs;
	}

	public int getShortestHours(String origin, String destination) {
		int hrs = -1;// can use Integer.MAX_VALUE to denote infinity
		Airport a = airport_list.get(origin);
		if (a != null) {
			hrs = a.getHoursTo(destination);
		}
		return hrs;
	}

	public List<String> getAirportsNStopsAway(String origin, int nStops) {
		List<String> airpts = new ArrayList<String>();
		List<Airport> checkList = new ArrayList<Airport>();
		checkList.add(airport_list.get(origin));
		airpts.add(origin);
		int hopCount = 0;
		while (hopCount < nStops) {
			hopCount++;
			Airport[] s= new Airport[checkList.size()];
			for(int k=0;k<s.length;k++){
				s[k]=checkList.get(k);
			}
			checkList.clear();
			airpts.clear();
			for (int i = 0; i < s.length; i++) {
				Airport ap= s[i];
				String[] s2 = ap.getAllConnections();
				for (int j = 0; j < s2.length; j++) {
					String nextAirport = airport_list.get(s2[j]).name;
					if(airpts.indexOf(nextAirport)<0){
						checkList.add(airport_list.get(s2[j]));
						airpts.add(nextAirport);
					}
				}
			}
		}
		return airpts;
	}

	public String getLongestPath(String origin) {
		String lgstPath = null;
		// do stuff!
		return lgstPath;
	}

}
