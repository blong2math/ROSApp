package app.ros.com.rosapp.helper;

import java.util.ArrayList;
import java.util.Stack;

import app.ros.com.rosapp.helper.SubwayGraph;

public class PathFinder{
	
	// SubwayGraph
	private SubwayGraph sg;
	
	public PathFinder(){
		sg = new SubwayGraph();
	}
	
	public ArrayList<Stack<Integer>> findPath(int orientation, int destination){
		// Possible pathes
		ArrayList<Stack<Integer>> possiblePathes = new ArrayList<Stack<Integer>>();
		
		// Start position and finish position
		int start = SubwayGraph.id2Node(orientation);
		int finish = SubwayGraph.id2Node(destination);
		
		// Status storing
		int [] vertexStatus = new int[sg.getNumStations()];
		int [][] arcStatus = new int[sg.getNumStations()][sg.getNumStations()];
		
		// Route Searching
		Stack<Integer> stack = new Stack<Integer>();
		vertexStatus[start] = 1;
		stack.push(new Integer(start));
		while(!stack.isEmpty()){
			int elem = stack.peek();
			if(elem == finish){
				@SuppressWarnings("unchecked")
				Stack<Integer> s_temp = (Stack<Integer>) stack.clone();
				possiblePathes.add(s_temp);
				vertexStatus[elem] = 0;
				for(int row = 0; row < sg.getNumStations(); row++){
					for(int col = 0; col < sg.getNumStations(); col++){
						if(!stack.contains(new Integer(row)) && !stack.contains(new Integer(col))){
							arcStatus[row][col] = 0;
						}
					}
				}
				stack.pop();
			}else{
				int iter = 0;
				for(iter = 0; iter < sg.getNumStations(); iter++){
					if((vertexStatus[iter] == 0) && (arcStatus[elem][iter] == 0) && (sg.getWeight(elem, iter) != 0)){
						vertexStatus[iter] = 1;
						arcStatus[elem][iter] = 1;
						stack.push(new Integer(iter));
						break;
					}
				}
				if(iter == sg.getNumStations()){
					vertexStatus[elem] = 0;
					for(int row = 0; row < sg.getNumStations(); row++){
						for(int col = 0; col < sg.getNumStations(); col++){
							if(!stack.contains(new Integer(row)) && !stack.contains(new Integer(col))){
								arcStatus[row][col] = 0;
							}
						}
					}
					stack.pop();
				}
			}
		}
		return possiblePathes;
	}
}