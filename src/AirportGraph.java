import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class AirportGraph {

	HashMap<String, Airport> airport_list = new HashMap<String, Airport>();

	AirportGraph() {
		// do nothing!
	}

	public void addConnection(String origin, String destination, int hrs) {
		if (airport_list.containsKey(origin)) {
			Airport anAirport = airport_list.get(origin);
			anAirport.addConnection(destination, hrs);
		} else {
			Airport anAirport = new Airport(origin);
			anAirport.addConnection(destination, hrs);
			airport_list.put(origin, anAirport);
		}
	}

	public int getDirectHours(String origin, String destination) {
		int hrs = -1;// can use Integer.MAX_VALUE to denote infinity
		Airport anAirport = airport_list.get(origin);
		if (anAirport != null) {
			hrs = anAirport.getHoursTo(destination);
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
			Airport[] setOfAirports = new Airport[checkList.size()];
			for (int idx1 = 0; idx1 < setOfAirports.length; idx1++) {
				setOfAirports[idx1] = checkList.get(idx1);
			}
			checkList.clear();
			airportNamesList.clear();
			for (int idx2 = 0; idx2 < setOfAirports.length; idx2++) {
				Airport anAirport = setOfAirports[idx2];
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

	public PathInfo getShortestHours(String origin, String destination) {
		int hrs = 0;// can use Integer.MAX_VALUE to denote infinity
		String path=origin;
		
		PathInfo info = new PathInfo(path,hrs);
		
		if (!airport_list.containsKey(origin)
				|| !airport_list.containsKey(destination)) {
			//Either origin or destination DNE
			info.setHours(-1);
			return info;
		}else if(origin.compareTo(destination)==0) {
			return info;
		}
		Comparator<NextAirport> comparator = new LeastHoursComparator();
		PriorityQueue<NextAirport> aptsList = new PriorityQueue<NextAirport>(
				airport_list.size(), comparator);
		List<String> alreadySeen = new ArrayList<String>();
		String justSeen = origin;

		addToQ(origin, "", 0, alreadySeen, aptsList, comparator);
		while (justSeen.compareTo(destination) != 0) {
			NextAirport anAirport = aptsList.poll();
			justSeen = anAirport.name;
			if (justSeen.compareTo(destination) == 0) {
				/*System.out.println(anAirport.name + "--" + anAirport.hours + "--" + anAirport.path
						+ justSeen);*/
				info.setInfo(anAirport.path+ justSeen, anAirport.hours);
			}
			addToQ(justSeen, anAirport.path, anAirport.hours, alreadySeen, aptsList,
					comparator);
		}
		return info;
	}

	public void addToQ(String aptName, String aPath, int hrsTillNow,
			List<String> alreadySeen, PriorityQueue<NextAirport> aptsList,
			Comparator<NextAirport> comparator) {
		alreadySeen.add(aptName);
		Airport anAirport = airport_list.get(aptName);
		String[] connections = anAirport.getAllConnections();
		int[] hours = anAirport.getAllHours();
		aPath += aptName + ",";
		for (int i = 0; i < anAirport.connectionCount; i++) {
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
