Name:	Saikat R. Gomes<br>
Email:	saikatgomes@gmail.com<br>
		saikat@cs.wisc.edu<br>
Phone:	201-850-7165<br>
School:	University of Wisconsin, Madison<br>

Instructions to compile/run:<br>

	I have created a flights.jar file
	Option 1:
		execute a shell script to compile/run flights.sh
		from the command line execute
			./flights.sh <file1> <file2>
				where:
					file1 is the list of flights ~ flights.txt
					flie2 is a list of tasks ~ tasks.txt
	Option 2:
		Manually compile/run the flights.jar
		from the command line execute
			java -jar flights.jar <file1> <file2>
				where:
					file1 is the list of flights ~ flights.txt
					flie2 is a list of tasks ~ tasks.txt

Source Code:<br>
	src/<br>
		AirportGraph.java<br>
		Airport.java<br>
		flightTest.java<br>
		PathInfo.java<br>

Implementation details in brief:

The list of flight information is first convert into a directed, weighted graph; where:
	each node represents a city/airport
	each directed edge represents a flight from origin to destination
	weight of the edge denotes hours

I have assumed each flight hour to be float.

To find the Quickest flight path between two airports dijkstra's shortest path algorithm is implemented with the help of a priority queue. 

To list all the airports that can be reached by taking exactly a certain number of flights I have done an implemented of a breath-first-search keeping track of the number of hops (ie number of flights) traversed. 

To find the longest-duration round trip path/to and from an airport, all the possible acyclic path (other than the starting and ending airports) were searched to find the longest path. 
Finding the longest path in a graph (a Hamiltonian path in this case) is a NP hard problem. The search can be optimized by marking any airport not part of the feasible if no solutions passes though it. Also if space was not an issue we could start caching or pre-computing longest path from itself to all other nodes. Other than using a lot of space these caching will be invalidated if new airports are added or removed from the graph. Hence I went with a simple solution for this problem.

I have done some additional testing, these testings are turned off by default but can we turned on by setting DO_TESTS=true in flightsTest.java. The jar would need to be recreated.

A folder named "out" is created if not already present where solution.txt is created as an output.
