import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @author 		Saikat Gomes 
 * Email: 		saikatgomes@gmail.com 
 * Description: Stores the list of flights as a graph structure
 * 				and associated functions wit it.
 */
public class AirportGraph {

	// Maps the airport names to Airport objects - faster lookup! 
	HashMap<String, Airport> airport_list = new HashMap<String, Airport>();

	AirportGraph() {
		// do nothing!
	}

	// Get a list of all the Aiport Names in the graph
	public String[] getAirportNames() {
		Set<String> key = airport_list.keySet();
		String[] aptNames = key.toArray(new String[key.size()]);
		return aptNames;
	}

	// Add a flight to the graph as a directed edge
	// origin --> destination : hrs
	public void addConnection(String origin, String destination, float hrs) {
		if (airport_list.containsKey(origin)) {
			// This airport is already present in the graph
			// So update it with a new connection
			Airport anAirport = airport_list.get(origin);
			anAirport.addConnection(destination, hrs);
		} else {
			// This airport does not exist in the graph yet
			// Add it to the graph
			Airport anAirport = new Airport(origin);
			anAirport.addConnection(destination, hrs);
			airport_list.put(origin, anAirport);
		}
	}

	// Given a starting airport and a number of flights, get a list all 
	// the airports (in alphabetical order) hat may be reached 
	// by taking exactly N flights.
	// Essentially doing a BFS without making a node as seen since we
	// allow cycles here ie A->B->A : 2 hops!
	public String getAirportsNStopsAway(String origin, int nStops) {
		List<String> airportNamesList = new ArrayList<String>();
		List<Airport> checkList = new ArrayList<Airport>();
		Airport oriAirport = airport_list.get(origin);
		
		if (oriAirport == null) {
			// this origin does not exist
			return "";
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
			//clear the checklist since we a going a level deeper.
			checkList.clear();
			airportNamesList.clear();
			
			// get all airports "hopCount" flights away
			
			for (int idx2 = 0; idx2 < setOfAirports.length; idx2++) {
				Airport anAirport = setOfAirports[idx2];
				String[] airportNeightbors = anAirport.getAllConnections();
				for (int idx3 = 0; idx3 < airportNeightbors.length; idx3++) {
					String nextAirport = airport_list
							.get(airportNeightbors[idx3]).name;
					if (airportNamesList.indexOf(nextAirport) < 0) {
						// add a new airport only if its not already present
						checkList
								.add(airport_list.get(airportNeightbors[idx3]));
						airportNamesList.add(nextAirport);
					}
				}
			}
		}
		// short the final airport list
		java.util.Collections.sort(airportNamesList);
		return listToString(airportNamesList);
	}

	// The longest-duration round trip flight path to/from an airport. 
	// This flight path does not visit any airport more than once, 
	// other than the starting/ending airport.
	public PathInfo getRoundTrip(String origin) {
		float hrs = Float.MIN_VALUE;// can use Integer.MAX_VALUE to denote
									// infinity
		List<String> path = new ArrayList<String>();
		PathInfo info = new PathInfo(origin, hrs);
		/*
		 * Could optimize the solution by using a greedy approach and marking
		 * nodes with no possible solution once we see one.
		 * but this is NP hard problem so we will have check all possible paths.
		 * 
		 * Comparator<NextAirportWithList> comparator = new
		 * MostHoursComparator(); PriorityQueue<NextAirportWithList> aptsList =
		 * new PriorityQueue<NextAirportWithList>( airport_list.size(),
		 * comparator);
		 */
		Queue<NextAirportWithList> aptsList = new LinkedList<NextAirportWithList>();

		if (!airport_list.containsKey(origin)) {
			// this origin does not exist
			return info;
		}
		addToQWithList(origin, origin, path, 0, aptsList);

		// Search all possible paths without an internal cycle.
		while (!aptsList.isEmpty()) {
			NextAirportWithList anAirport = aptsList.poll();
			String justSeen = anAirport.name;
			if (justSeen.compareTo(origin) == 0) {
				//float comparison! :(
				if (anAirport.hours > info.getHours()) {
					// only add a path as a potential solution if it is longer that 
					// a path already found.
					anAirport.path.add(justSeen);
					info.setInfo(listToString(anAirport.path), anAirport.hours);
				}
			} else {
				addToQWithList(origin, justSeen, anAirport.path,
						anAirport.hours, aptsList);
			}
		}
		return info;
	}

	// helper function for getRoundTrip
	// add an airport to a search path while remembering the path traversed so far
	public void addToQWithList(String origin, String aptName,
			List<String> aPath, float hrsTillNow,
			Queue<NextAirportWithList> aptsList) {

		Airport anAirport = airport_list.get(aptName);
		String[] connections = anAirport.getAllConnections();
		float[] hours = anAirport.getAllHours();

		for (int idx = 0; idx < anAirport.connectionCount; idx++) {
			String nxtAptName = connections[idx];
			if (!aPath.contains(nxtAptName)
					|| origin.compareTo(nxtAptName) == 0) {
				List<String> aPath2 = new ArrayList<String>(aPath);
				aPath2.add(aptName);
				NextAirportWithList nxt = new NextAirportWithList(
						connections[idx], hours[idx] + hrsTillNow, aPath2);
				aptsList.add(nxt);
			}
		}
	}

	// Given a starting airport and an ending airport, 
	// lists the quickest flight path between them
	// This is an implementation of dijkstra's shortest path algorithm
	// using a priority queue
	public PathInfo getShortestHours(String origin, String destination) {
		float hrs = 0f;// can use Integer.MAX_VALUE to denote infinity
		String path = origin;
		// store path and hours
		PathInfo info = new PathInfo(path, hrs);

		if (!airport_list.containsKey(origin)
				|| !airport_list.containsKey(destination)) {
			// Either origin or destination DNE
			info.setHours(Float.MIN_VALUE);
			return info;
		} else if (origin.compareTo(destination) == 0) {
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
				info.setInfo(anAirport.path + justSeen, anAirport.hours);
			} else {
				addToQ(justSeen, anAirport.path, anAirport.hours, alreadySeen,
						aptsList, comparator);
			}
		}
		return info;
	}

	// helper function for getShortestHours
	// Updates the priority queue and a list of seen airports
	public void addToQ(String aptName, String aPath, float hrsTillNow,
			List<String> alreadySeen, PriorityQueue<NextAirport> aptsList,
			Comparator<NextAirport> comparator) {
		alreadySeen.add(aptName);
		Airport anAirport = airport_list.get(aptName);
		String[] connections = anAirport.getAllConnections();
		float[] hours = anAirport.getAllHours();
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

	// Store info about all neighboring airports
	// but store the path traversed as a string 
	// since no operation is done on path.
	public class NextAirport {
		String name;
		String path = "";
		float hours = -1f;

		NextAirport() {
			// do nothing!
		}
		NextAirport(String aName, float hrs, String aPath) {
			name = aName;
			hours = hrs;
			path = aPath;
		}
	}

	// Store info about all neighboring airports
	// but store path traversed so far as a list
	// since we need to do a look up for already traversed airports
	public class NextAirportWithList extends NextAirport {

		List<String> path = new ArrayList<String>();
		
		NextAirportWithList(String aName, float hrs, List<String> aPath) {
			name = aName;
			hours = hrs;
			path = aPath;
		}

	}

	// Custom comparator to insert elements in the priority queue
	// with the LEAST hours traversed so far getting highest priority
	public class LeastHoursComparator implements Comparator<NextAirport> {
		@Override
		public int compare(NextAirport apt1, NextAirport apt2) {
			if (apt1.hours < apt2.hours) {
				return -1;
			}
			if (apt1.hours > apt2.hours) {
				return 1;
			}
			return 0;
		}
	}

	// Custom comparator to insert elements in the priority queue
	// with the MOST hours traversed so far getting highest priority
	// Not really used here, but could be used as a greedy Heuristic for optimatization.
	public class MostHoursComparator implements Comparator<NextAirportWithList> {
		@Override
		public int compare(NextAirportWithList apt1, NextAirportWithList apt2) {
			if (apt1.hours > apt2.hours) {
				return -1;
			}
			if (apt1.hours < apt2.hours) {
				return 1;
			}
			return 0;
		}
	}
	
	// Converts a List<String> to a String for Display and Output
	public String listToString(List<String> aList){
		String outString="";
		for(int i=0;i<aList.size();i++){
			outString+=aList.get(i);
			if(i<aList.size()-1){
				outString+=",";
			}
		}		
		return outString;
	}
	
	// Displays the entire grap.
	public void print() {
		Iterator<Entry<String, Airport>> iterator = airport_list.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Airport> entry = iterator.next();
			Airport aAirport = (Airport) entry.getValue();
			aAirport.print();
		}
	}
}
