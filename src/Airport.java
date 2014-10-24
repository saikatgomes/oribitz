import java.util.ArrayList;
import java.util.List;

public class Airport {
	public List<String> connections = new ArrayList<String>();
	public List<Integer> hours = new ArrayList<Integer>();
	public String name;
	public int connectionCount = 0;

	Airport(String myName) {
		name = myName;
	}

	public void addConnection(String aName, int hrs) {
		connections.add(aName);
		hours.add(hrs);
		connectionCount++;
	}

	public void removeConnection(String aName) {
		connections.remove(aName);
	}

	public void updateConnection(String aName, int hrs) {
		// could be implemented more efficiently!
		removeConnection(aName);
		addConnection(aName, hrs);
	}

	public int getHoursTo(String aName) {
		int idx = connections.indexOf(aName);
		int hrs = Integer.MAX_VALUE;
		if (idx != -1) {
			hrs = hours.get(idx);
		}
		return hrs;
	}

	public void print() {
		System.out.println(name);
		for (int i = 0; i < connectionCount; i++) {
			System.out.println("\n--> " + connections.get(i) + " : "
					+ hours.get(i));
		}
	}

}
