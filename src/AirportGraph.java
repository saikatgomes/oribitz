import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
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

	public int getDirectHours(String origin, String destination) {
		int hrs = -1;// can use Integer.MAX_VALUE to denote infinity
		Airport a = airport_list.get(origin);
		if (a != null) {
			hrs = a.getHoursTo(destination);
		}
		return hrs;
	}

	public List<String> getAirportsNStopsAway(String origin, int nStops) {
		List<String> airportNamesList = new ArrayList<String>();
		List<Airport> checkList = new ArrayList<Airport>();
		Airport oriAirport = airport_list.get(origin);
		if (oriAirport == null) {
			// this origin does not exist
			return airportNamesList;
		}
		checkList.add(oriAirport);
		airportNamesList.add(origin);
		int hopCount = 0;
		while (hopCount < nStops) {
			hopCount++;
			Airport[] s = new Airport[checkList.size()];
			for (int idx1 = 0; idx1 < s.length; idx1++) {
				s[idx1] = checkList.get(idx1);
			}
			checkList.clear();
			airportNamesList.clear();
			for (int idx2 = 0; idx2 < s.length; idx2++) {
				Airport anAirport = s[idx2];
				String[] airportNeightbors = anAirport.getAllConnections();
				for (int idx3 = 0; idx3 < airportNeightbors.length; idx3++) {
					String nextAirport = airport_list
							.get(airportNeightbors[idx3]).name;
					if (airportNamesList.indexOf(nextAirport) < 0) {
						checkList
								.add(airport_list.get(airportNeightbors[idx3]));
						airportNamesList.add(nextAirport);
					}
				}
			}
		}
		java.util.Collections.sort(airportNamesList);
		return airportNamesList;
	}

	public String getLongestPath(String origin) {
		String lgstPath = null;
		// do stuff!
		return lgstPath;
	}

	public int getShortestHours(String origin, String destination) {
		int hrs = -1;// can use Integer.MAX_VALUE to denote infinity
		if (!airport_list.containsKey(origin)
				|| !airport_list.containsKey(destination)) {
			//Either origin or destination DNE
			return -1;
		}
		Comparator<NextAirport> comparator = new LeastHoursComparator();
		PriorityQueue<NextAirport> aptsList = new PriorityQueue<NextAirport>(
				airport_list.size(), comparator);
		List<String> alreadySeen = new ArrayList<String>();
		String justSeen = origin;

		addToQ(origin, "", 0, alreadySeen, aptsList, comparator);
		while (justSeen.compareTo(destination) != 0) {
			NextAirport aa = aptsList.poll();
			justSeen = aa.name;
			if (justSeen.compareTo(destination) == 0) {
				System.out.println(aa.name + "--" + aa.hours + "--" + aa.path
						+ justSeen);
			}
			addToQ(justSeen, aa.path, aa.hours, alreadySeen, aptsList,
					comparator);
		}
		return hrs;
	}

	public void addToQ(String aptName, String aPath, int hrsTillNow,
			List<String> alreadySeen, PriorityQueue<NextAirport> aptsList,
			Comparator<NextAirport> comparator) {
		alreadySeen.add(aptName);
		Airport a = airport_list.get(aptName);
		String[] connections = a.getAllConnections();
		int[] hours = a.getAllHours();
		aPath += aptName + ",";
		for (int i = 0; i < a.connectionCount; i++) {
			String nxtAptName = connections[i];
			if (!alreadySeen.contains(nxtAptName)) {
				NextAirport nxt = new NextAirport(connections[i], hours[i]
						+ hrsTillNow, aPath);
				aptsList.add(nxt);
			}
		}
	}

	public class NextAirport {
		String name;
		String path = "";
		int hours = -1;

		NextAirport(String aName, int hrs, String aPath) {
			name = aName;
			hours = hrs;
			path = aPath;
		}
	}

	public class LeastHoursComparator implements Comparator<NextAirport> {
		@Override
		public int compare(NextAirport x, NextAirport y) {
			if (x.hours < y.hours) {
				return -1;
			}
			if (x.hours > y.hours) {
				return 1;
			}
			return 0;
		}
	}

	public class MostHoursComparator implements Comparator<NextAirport> {
		@Override
		public int compare(NextAirport x, NextAirport y) {
			if (x.hours > y.hours) {
				return -1;
			}
			if (x.hours < y.hours) {
				return 1;
			}
			return 0;
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
}
