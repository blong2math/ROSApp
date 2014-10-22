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

    public ArrayList<Integer> dijkstra(int orientation, int destination){

        int start = SubwayGraph.id2Node(orientation);
        int finish = SubwayGraph.id2Node(destination);
        ArrayList<Integer>[] pathes = new ArrayList[152];
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(SubwayGraph.node2Id(start));
        pathes[start] = path;
        int[] isSearch = new int[152];
        int[] distance = new int[152];
        isSearch[start] = 1;

        do{
            int shortest = Integer.MAX_VALUE;
            int root = -1;
            int next = -1;
            for(int i = 0; i < sg.getNumStations(); i++){
                if(isSearch[i] == 1){
                    for(int j = 0; j < sg.getNumStations(); j++){
                        if(j != i && isSearch[j] == 0 && sg.getWeight(i, j) != 0){
                            if(sg.getWeight(i, j) + distance[i] < shortest){
                                next = j;
                                root = i;
                                shortest = sg.getWeight(i, j) + distance[i];
                            }
                        }
                    }
                }
            }
            if(next == -1) break;
            ArrayList<Integer> rootPath = (ArrayList<Integer>) pathes[root].clone();
            rootPath.add(SubwayGraph.node2Id(next));
            pathes[next] = rootPath;
            distance[next] = sg.getWeight(root, next) + distance[root];
            isSearch[next] = 1;
            if(next == finish) break;
        }while(true);

        return pathes[finish];
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
                break;
                /*
				vertexStatus[elem] = 0;
				for(int row = 0; row < sg.getNumStations(); row++){
					for(int col = 0; col < sg.getNumStations(); col++){
						if(!stack.contains(new Integer(row)) && !stack.contains(new Integer(col))){
							arcStatus[row][col] = 0;
						}
					}
				}
				stack.pop();*/
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