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
			
			//TEST CASES
			System.out.println(airport_list.getDirectHours("QQ", "K"));
			System.out.println(airport_list.getDirectHours("O", "K"));
			System.out.println(airport_list.getDirectHours("K", "O"));
			System.out.println(airport_list.getDirectHours("K", "H"));
			System.out.println(airport_list.getDirectHours("K", "J"));
			System.out.println(airport_list.getDirectHours("A", "B"));
			System.out.println(airport_list.getDirectHours("A", "X"));

			List<String> airpts0 = airport_list.getAirportsNStopsAway("A", 0);
			List<String> airpts1 = airport_list.getAirportsNStopsAway("A", 1);
			List<String> airpts2 = airport_list.getAirportsNStopsAway("A", 2);
			List<String> airpts3 = airport_list.getAirportsNStopsAway("A", 3);
			List<String> airpts4 = airport_list.getAirportsNStopsAway("A", 4);
			List<String> airpts5 = airport_list.getAirportsNStopsAway("A", 5);
			List<String> airpts6 = airport_list.getAirportsNStopsAway("A", 6);
			List<String> airpts7 = airport_list.getAirportsNStopsAway("A", 7);
			List<String> airpts8 = airport_list.getAirportsNStopsAway("A", 8);
			List<String> airpts9 = airport_list.getAirportsNStopsAway("A", 9);
			List<String> airpts10 = airport_list.getAirportsNStopsAway("A", 10);
			List<String> airpts11 = airport_list.getAirportsNStopsAway("A", 11);

			System.out.println("A:0 -> "+airpts0);
			System.out.println("A:1 -> "+airpts1);
			System.out.println("A:2 -> "+airpts2);
			System.out.println("A:3 -> "+airpts3);
			System.out.println("A:4 -> "+airpts4);
			System.out.println("A:5 -> "+airpts5);
			System.out.println("A:6 -> "+airpts6);
			System.out.println("A:7 -> "+airpts7);
			System.out.println("A:8 -> "+airpts8);
			System.out.println("A:9 -> "+airpts9);
			System.out.println("A:10 -> "+airpts10);
			System.out.println("A:11 -> "+airpts11);
			
			airport_list.print();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
