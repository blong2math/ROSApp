package Path;

import Jama.Matrix;

public class SubwayGraph{
	
	private static int [] numStationsLines = {24, 25, 39, 7, 24, 33};
	private static int numStations = 152;
	Matrix subwayGraph;
	
	public SubwayGraph(){
		subwayGraph = new Matrix(numStations, numStations);
		// The following 5 stations will be changed in the future);
		subwayGraph.set(0, 1, 0); subwayGraph.set(16, 18, 4); subwayGraph.set(22, 23, 0);
		subwayGraph.set(1, 124, 0); subwayGraph.set(48, 49, 0);
		
	    // 0-23: Chaotianmen-Daxuecheng...
	    subwayGraph.set(1, 2, 2); subwayGraph.set(2, 3, 2); subwayGraph.set(3, 4, 2); 
	    subwayGraph.set(4, 5, 3); subwayGraph.set(5, 6, 2); subwayGraph.set(6, 7, 3); 
	    subwayGraph.set(7, 8, 2); subwayGraph.set(8, 9, 2); subwayGraph.set(9, 10, 3);
	    subwayGraph.set(10, 11, 2); subwayGraph.set(11, 12, 1); subwayGraph.set(12, 13, 2);
	    subwayGraph.set(13, 14, 3); subwayGraph.set(14, 15, 2); subwayGraph.set(15, 16, 2);
	    subwayGraph.set(18, 19, 6); subwayGraph.set(19, 20, 2); subwayGraph.set(20, 21, 4); 
	    subwayGraph.set(21, 22, 2);
	    
	    // 24-48: Jiaochangkou-Yudong
	    subwayGraph.set(24, 25, 1); subwayGraph.set(25, 26, 2); subwayGraph.set(26, 27, 2); 
	    subwayGraph.set(27, 28, 1); subwayGraph.set(28, 29, 3); subwayGraph.set(29, 30, 2); 
	    subwayGraph.set(30, 31, 1); subwayGraph.set(31, 32, 3); subwayGraph.set(32, 33, 2); 
	    subwayGraph.set(33, 34, 2); subwayGraph.set(34, 35, 3); subwayGraph.set(35, 36, 2);
	    subwayGraph.set(36, 37, 2); subwayGraph.set(37, 38, 2); subwayGraph.set(38, 39, 2); 
	    subwayGraph.set(39, 40, 2); subwayGraph.set(40, 41, 2);
	    
	    // 49-87: Yudong-Jiangbei Airport
	    subwayGraph.set(49, 50, 2); subwayGraph.set(50, 51, 3); subwayGraph.set(51, 52, 3); 
	    subwayGraph.set(52, 53, 3); subwayGraph.set(53, 54, 3); subwayGraph.set(54, 55, 3); 
	    subwayGraph.set(55, 56, 2); subwayGraph.set(56, 57, 2); subwayGraph.set(57, 58, 3); 
	    subwayGraph.set(58, 59, 2); subwayGraph.set(59, 60, 2); subwayGraph.set(60, 61, 3);
	    subwayGraph.set(61, 62, 2); subwayGraph.set(62, 63, 3); subwayGraph.set(63, 64, 2); 
	    subwayGraph.set(64, 65, 2); subwayGraph.set(65, 66, 3); subwayGraph.set(66, 67, 2); 
	    subwayGraph.set(67, 68, 2); subwayGraph.set(68, 69, 3); subwayGraph.set(69, 70, 3); 
	    subwayGraph.set(70, 71, 2); subwayGraph.set(71, 72, 3); subwayGraph.set(72, 73, 2);
	    subwayGraph.set(73, 74, 2); subwayGraph.set(74, 75, 2); subwayGraph.set(75, 76, 2); 
	    subwayGraph.set(76, 77, 3); subwayGraph.set(77, 78, 2); subwayGraph.set(78, 79, 3); 
	    subwayGraph.set(79, 80, 3); subwayGraph.set(80, 81, 2); subwayGraph.set(81, 82, 3); 
	    subwayGraph.set(82, 83, 3); subwayGraph.set(83, 84, 3); subwayGraph.set(84, 85, 3);
	    subwayGraph.set(85, 86, 4); subwayGraph.set(86, 87, 2);

	    // 119-149: Chayuan-Beibei-Yuelai
	    subwayGraph.set(127, 128, 4); subwayGraph.set(128, 129, 2); subwayGraph.set(129, 130, 3); 
	    subwayGraph.set(130, 131, 2); subwayGraph.set(131, 132, 3); subwayGraph.set(132, 133, 3); 
	    subwayGraph.set(133, 134, 3); subwayGraph.set(134, 135, 4); subwayGraph.set(135, 136, 2); 
	    subwayGraph.set(136, 138, 5); subwayGraph.set(138, 139, 2); subwayGraph.set(139, 141, 7); 
	    subwayGraph.set(141, 143, 9); subwayGraph.set(143, 144, 2); subwayGraph.set(144, 146, 5); 
	    subwayGraph.set(138, 150, 11); subwayGraph.set(150, 151, 3);

	    // Interchange Station
	    subwayGraph.set(2, 24, 5); 		// Jiaochangkou
	    subwayGraph.set(8, 32, 5); 		// Daping
	    subwayGraph.set(4, 66, 5); 		// Lianglukou
	    subwayGraph.set(29, 67, 5); 	// Niujiaotuo
	    subwayGraph.set(70, 130, 5); 	// Hongqihegou
	    
	    // Calculate reverse path
	    double[][] reversePath = subwayGraph.transpose().getArray();
	    double[][] path = subwayGraph.getArray();
	    for(int row = 0; row < numStations; row++){
	    	for(int col = 0; col < numStations; col++){
	    		path[row][col] = reversePath[row][col] + path[row][col];
	    	}
	    }
	    subwayGraph = Matrix.constructWithCopy(path);
	}
	
	public int getNumStations(){
		return numStations;
	}
	
	public int getWeight(int start, int finish){
		return (int) subwayGraph.get(start, finish);
	}
	
	// Subway id number is transformed to node number in graph);
	public static int id2Node(int id){
		int stationNumber = id % 100;
		int lineNumber = (id - stationNumber) / 100;
		if(lineNumber == 56){
			stationNumber += 27;
			lineNumber = 6;
		}
		int node = 0;
		for(int iter = 0; iter < lineNumber-1; iter++){
			node += numStationsLines[iter];
		}
		return node + stationNumber - 1;
	}
	
	// Node number in graph is transformed to subway id number);
	public static int node2Id(int node){
		int temp = node + 1;
		int line = 0;
		while(temp > numStationsLines[line]){
			temp -= numStationsLines[line];
			line++;
		}
		return (line + 1) * 100 + temp;
	}
}