import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class flightTest {

	// Flag to do additional testing
	final static boolean DO_TESTS = true;

	/**
	 * @author Saikat Gomes 
	 * Email: saikatgomes@gmail.com 
	 * Description: This is the main execution point to 
	 * 				read the input files and create the
	 *         		output file in the required format.
	 * 
	 * @param args[0] flights file ex: flights.txt
	 * @param args[1] task file ex: tasks.txt
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException,
			IOException {
		if (args.length != 2) {
			// Usage error.
			System.out.println("ERROR: (usage) flightTest "
					+ "<flights-file> <task-file>");
			System.exit(-1);
		}

		// create a graph from the input information
		AirportGraph airport_list = new AirportGraph();
		BufferedReader in;

		try {
			// read flight list file
			in = new BufferedReader(new FileReader(args[0]));
			int lineCount = 0;
			while (in.ready()) {
				String aLine = in.readLine();
				lineCount++;
				String[] data = aLine.split(",");
				if (data.length != 3) {
					// input file not according to expected format
					System.out.println("Flights file (" + args[0]
							+ ") should be formatted as "
							+ "<airport1>,<airport2>,<time> [line:" + lineCount
							+ "]");
					System.exit(-1);
				}
				// add the flight to the graph
				airport_list.addConnection(data[0], data[1],
						Float.valueOf(data[2]));
			}
			in.close();

			// ASSUMPTION: Tasks file is formatted as expected.
			in = new BufferedReader(new FileReader(args[1]));

			File outDir = new File("out");

			if (!outDir.exists()) {
				if (outDir.mkdir()) {
					System.out.println("out directory is created!");
				} else {
					System.exit(-1);
					System.out.println("Failed to create out directory!");
				}
			}
			PrintWriter writer = new PrintWriter("out/solutions.txt");

			// Quickest path between 2 cities/airports
			String aLine = in.readLine();
			String[] data = aLine.split(",");
			PathInfo pathInformation = airport_list.getShortestHours(data[0],
					data[1]);
			// uncomment to print to screen
			// System.out.println(pathInformation.getPath());
			writer.println(pathInformation.getPath());

			// All airports that can be reached with exactly N flights
			aLine = in.readLine();
			data = aLine.split(",");
			String listOfAirports = airport_list.getAirportsNStopsAway(data[0],
					Integer.valueOf(data[1]));
			// uncomment to print to screen
			// System.out.println(listOfAirports.toString());
			writer.println(listOfAirports.toString());

			// Longest duration round-trip from an airport
			aLine = in.readLine();
			pathInformation = airport_list.getRoundTrip(aLine);
			// uncomment to print to screen
			// System.out.println(pathInformation.getPath());
			writer.println(pathInformation.getPath());

			in.close();
			writer.close();

			// TEST CASES
			// These are for additional testing
			// Set the "DO_TESTS" flag to true to do these additional tests
			if (DO_TESTS) {
				String[] aptName = airport_list.getAirportNames();
				String airpts = "";
				PathInfo ans = new PathInfo();

				// Quickest path between 2 cities/airports - all pairs!
				for (int idx1 = 0; idx1 < aptName.length; idx1++) {
					for (int idx2 = 0; idx2 < aptName.length; idx2++) {
						String start = aptName[idx1];
						String destination = aptName[idx2];
						ans = airport_list.getShortestHours(start, destination);
						System.out.println(start + "-->" + destination + " : "
								+ ans.getPath() + " @ " + ans.getHours());
					}
				}

				// All airports that can be reached with exactly N flights - all
				// airports! N={1,2,3,4,5}
				for (int idx1 = 0; idx1 < aptName.length; idx1++) {
					String start = aptName[idx1];
					for (int idx2 = 1; idx2 < 5; idx2++) {
						airpts = airport_list
								.getAirportsNStopsAway(start, idx2);
						System.out.println(aptName[idx1] + ":" + idx2 + " -> "
								+ airpts);
					}
				}

				// Longest duration round-trip from all airports
				for (int idx = 0; idx < aptName.length; idx++) {
					String start = aptName[idx];
					ans = airport_list.getRoundTrip(start);
					System.out.println(start + " : " + ans.getPath() + " @ "
							+ ans.getHours());
				}
				airport_list.print();
			}

		} catch (FileNotFoundException e) {
			// Required input files (ex: flights.txt, tasks.txt) were not found
			e.printStackTrace();
			System.out.println("ERROR: File Not Found!");
			System.exit(-1);
		}
	}
}
