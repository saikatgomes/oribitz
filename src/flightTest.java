import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class flightTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("ERROR: (usage) flightTest "
					+ "<flights-file> <task-file>");
			System.exit(-1);
		}

		AirportGraph airport_list = new AirportGraph();
		BufferedReader in;

		try {
			in = new BufferedReader(new FileReader(args[0]));
			int lineCount = 0;
			while (in.ready()) {
				String aLine = in.readLine();
				lineCount++;
				String[] data = aLine.split(",");
				if (data.length != 3) {
					System.out.println("Flights file (" + args[0]
							+ ") should be formatted as "
							+ "<airport1>,<airport2>,<time> [line:" + lineCount
							+ "]");
					System.exit(-1);
				}
				airport_list.addConnection(data[0], data[1],
						Integer.valueOf(data[2]));
			}

			in.close();

			// TEST CASES

			String[] aptName = airport_list.getAirportNames();
			List<String> airpts = new ArrayList<String>();
			PathInfo ans = new PathInfo();

			for (int i = 0; i < aptName.length; i++) {
				String s = aptName[i];
				for (int j = 1; j < 5; j++) {
					airpts = airport_list.getAirportsNStopsAway(s, j);
					System.out.println(aptName[i] + ":" + j + " -> " + airpts);
				}
			}

			for (int i = 0; i < aptName.length; i++) {
				for (int j = 0; j < aptName.length; j++) {
					String s = aptName[i];
					String d = aptName[j];
					ans = airport_list.getShortestHours(s, d);
					System.out.println(s + "-->" + d + " : " + ans.getPath()
							+ " @ " + ans.getHours());
				}
			}

			for (int i = 0; i < aptName.length; i++) {
				String s = aptName[i];
				ans = airport_list.getRoundTrip(s);
				System.out.println(s + " : " + ans.getPath() + " @ "
						+ ans.getHours());
			}

			airport_list.print();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
