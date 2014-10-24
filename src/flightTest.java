import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

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

		HashMap<String, String> airports = new HashMap<String, String>();

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
				String ap1=data[0];
				String ap2=data[1];
				String hrs=data[2];
				if(airports.containsKey(ap1)){
					airports.put(ap1, airports.get(ap1)+"|"+ap2);
				}else{
					airports.put(ap1, ap2);
				}
				System.out.println(data[0] + " --> " + data[1] + " = "
						+ data[2]);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println(airports.entrySet());

	}

}
