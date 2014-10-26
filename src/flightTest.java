import java.io.BufferedReader;
import java.io.FileReader;
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
			/*
			 * System.out.println(airport_list.getDirectHours("QQ", "K"));
			 * System.out.println(airport_list.getDirectHours("O", "K"));
			 * System.out.println(airport_list.getDirectHours("K", "O"));
			 * System.out.println(airport_list.getDirectHours("K", "H"));
			 * System.out.println(airport_list.getDirectHours("K", "J"));
			 * System.out.println(airport_list.getDirectHours("Y", "B"));
			 * System.out.println(airport_list.getDirectHours("Y", "X"));
			 */

			/*
			 * List<String> airpts0 = airport_list.getAirportsNStopsAway("Y",
			 * 0); List<String> airpts1 =
			 * airport_list.getAirportsNStopsAway("Y", 1); List<String> airpts2
			 * = airport_list.getAirportsNStopsAway("Y", 2); List<String>
			 * airpts3 = airport_list.getAirportsNStopsAway("Y", 3);
			 * List<String> airpts4 = airport_list.getAirportsNStopsAway("Y",
			 * 4); List<String> airpts5 =
			 * airport_list.getAirportsNStopsAway("Y", 5); List<String> airpts6
			 * = airport_list.getAirportsNStopsAway("Y", 6); List<String>
			 * airpts7 = airport_list.getAirportsNStopsAway("Y", 7);
			 * List<String> airpts8 = airport_list.getAirportsNStopsAway("Y",
			 * 8); List<String> airpts9 =
			 * airport_list.getAirportsNStopsAway("Y", 9); List<String> airpts10
			 * = airport_list.getAirportsNStopsAway("Y", 10); List<String>
			 * airpts11 = airport_list.getAirportsNStopsAway("Y", 11);
			 * 
			 * List<String> airpts12 = airport_list.getAirportsNStopsAway("YY",
			 * 11);
			 * 
			 * System.out.println("Y:0 -> " + airpts0);
			 * System.out.println("Y:1 -> " + airpts1);
			 * System.out.println("Y:2 -> " + airpts2);
			 * System.out.println("Y:3 -> " + airpts3);
			 * System.out.println("Y:4 -> " + airpts4);
			 * System.out.println("Y:5 -> " + airpts5);
			 * System.out.println("Y:6 -> " + airpts6);
			 * System.out.println("Y:7 -> " + airpts7);
			 * System.out.println("Y:8 -> " + airpts8);
			 * System.out.println("Y:9 -> " + airpts9);
			 * System.out.println("Y:10 -> " + airpts10);
			 * System.out.println("Y:11 -> " + airpts11);
			 */

			int ans = airport_list.getShortestHours("A", "A");
			ans = airport_list.getShortestHours("A", "B");
			ans = airport_list.getShortestHours("A", "C");
			ans = airport_list.getShortestHours("A", "D");
			ans = airport_list.getShortestHours("A", "E");
			ans = airport_list.getShortestHours("A", "F");
			ans = airport_list.getShortestHours("A", "G");
			ans = airport_list.getShortestHours("A", "H");
			ans = airport_list.getShortestHours("A", "I");
			ans = airport_list.getShortestHours("A", "J");
			ans = airport_list.getShortestHours("A", "K");
			ans = airport_list.getShortestHours("A", "L");
			ans = airport_list.getShortestHours("A", "M");
			ans = airport_list.getShortestHours("A", "N");
			ans = airport_list.getShortestHours("A", "O");
			ans = airport_list.getShortestHours("A", "P");
			ans = airport_list.getShortestHours("A", "Q");
			ans = airport_list.getShortestHours("A", "R");
			ans = airport_list.getShortestHours("A", "S");
			ans = airport_list.getShortestHours("A", "T");
			ans = airport_list.getShortestHours("A", "U");
			ans = airport_list.getShortestHours("A", "V");
			ans = airport_list.getShortestHours("A", "W");
			ans = airport_list.getShortestHours("A", "X");
			ans = airport_list.getShortestHours("A", "Y");
			ans = airport_list.getShortestHours("A", "Z");

			airport_list.print();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
