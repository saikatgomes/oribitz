import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class flightTest {

	final static boolean DO_TESTS = true;

	/**
	 * Saikat Gomes
	 * 
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
			if (DO_TESTS) {
				String[] aptName = airport_list.getAirportNames();
				List<String> airpts = new ArrayList<String>();
				PathInfo ans = new PathInfo();

				for (int idx1 = 0; idx1 < aptName.length; idx1++) {
					String start = aptName[idx1];
					for (int idx2 = 1; idx2 < 5; idx2++) {
						airpts = airport_list.getAirportsNStopsAway(start, idx2);
						System.out.println(aptName[idx1] + ":" + idx2 + " -> "
								+ airpts);
					}
				}
				for (int idx1 = 0; idx1 < aptName.length; idx1++) {
					for (int idx2 = 0; idx2 < aptName.length; idx2++) {
						String start = aptName[idx1];
						String destination = aptName[idx2];
						ans = airport_list.getShortestHours(start, destination);
						System.out.println(start + "-->" + destination + " : "
								+ ans.getPath() + " @ " + ans.getHours());
					}
				}
				for (int idx = 0; idx < aptName.length; idx++) {
					String start = aptName[idx];
					ans = airport_list.getRoundTrip(start);
					System.out.println(start + " : " + ans.getPath() + " @ "
							+ ans.getHours());
				}
				airport_list.print();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
