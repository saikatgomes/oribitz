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

			List<String> airpts = airport_list.getAirportsNStopsAway("A", 2);
			List<String> airpts2 = airport_list.getAirportsNStopsAway("A", 3);
			
			
			airport_list.print();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
