/**
 * Created by 
 * Peerachai Banyongrakkul Sec.1 5988070
 * Assignment 3
 * Graph-Search algorithm with heuristics (N-Puzzle)
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class NPuzzle {

    public static class Pair<L, R> {
        public L l;
        public R r;
        public Pair(L l, R r){
            this.l = l;
            this.r = r;
        }
    }

    public static int parity(NPuzzleState state) {
        int inversions = 0;
        ArrayList<Integer> nums = new ArrayList<>();
        int[][] board = state.getBoard();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                nums.add(board[i][j]);
            }
        }
        Integer[] copy = new Integer[nums.size()];
        nums.toArray(copy);
        for (int i = 0; i < copy.length; i++){
            for (int j = i + 1; j < copy.length; j++){
                if (copy[i] != 0 && copy[j] != 0 && copy[i]> copy[j]){
                    inversions++;
                }
            }
        }
        //System.out.println(inversions);
        return inversions % 2;
    }

    public static boolean isSolvable(NPuzzleState initState, NPuzzleState goalState) {
    	//System.out.println(parity(initState) + " " + parity(goalState));
        return parity(initState) == parity(goalState);
    }

    public static enum Action{ 
        // Define possible actions for the N-Puzzle search problem
        // [start:1]
    	UP,DOWN,LEFT,RIGHT
        // [end:1]
    }

    public static NPuzzleState successor(NPuzzleState state, Action action) {
        int[][] board = state.getBoard();
        int s = board.length;
        int r = state.getR();
        int c = state.getC();
        // Implement a successor function
        // Your code should return a new state if the action is valid
        // otherwise return null
        // [start:2]
        int[][] newBoard = state.copyBoard();
        Boolean isInvalid = false;
        NPuzzleState newState = null;
        if(action.compareTo(Action.UP) == 0)
        {
        	if( r > 0 )
        	{
        		int tempBoard = newBoard[r][c];
        		newBoard[r][c] = newBoard[r - 1][c];
        		newBoard[r - 1][c] = tempBoard;
        		newState = new NPuzzleState(newBoard);
        	}
        	else
        	{
        		isInvalid = true;
        	}
        }
        else if(action.compareTo(Action.DOWN) == 0)
        {
        	if( r < (s-1) )
        	{
        		int tempBoard = newBoard[r][c];
        		newBoard[r][c] = newBoard[r + 1][c];
        		newBoard[r + 1][c] = tempBoard;
        		newState = new NPuzzleState(newBoard);
        	}
        	else
        	{
        		isInvalid = true;
        	}
        }
        else if(action.compareTo(Action.LEFT) == 0)
        {
        	if( c > 0 )
        	{
        		int tempBoard = newBoard[r][c];
        		newBoard[r][c] = newBoard[r][c - 1];
        		newBoard[r][c - 1] = tempBoard;
        		newState = new NPuzzleState(newBoard);
        	}
        	else
        	{
        		isInvalid = true;
        	}
        }
        else if(action.compareTo(Action.RIGHT) == 0)
        {
        	if( c < (s-1) )
        	{
        		int tempBoard = newBoard[r][c];
        		newBoard[r][c] = newBoard[r][c + 1];
        		newBoard[r][c + 1] = tempBoard;
        		newState = new NPuzzleState(newBoard);
        	}
        	else
        	{
        		isInvalid = true;
        	}
        }
        //System.out.println(newState.toString()+"\n");
        // [end:2]
        if(isInvalid == true)
        {
        	return null;  // <- action is invalid
        }
        else
        {
        	return newState;
        }
    }

    public static ArrayList<TreeNode<NPuzzleState>> expandSuccessors(TreeNode<NPuzzleState> node) {
        ArrayList<TreeNode<NPuzzleState>> successors = new ArrayList<>();
        NPuzzleState state = node.getState();
        // Define a successor function for the N-Puzzle search problem
        // Your code should add all child nodes to "successors"
        // Hint: use successor(.,.) function above
        // [start:3]

        TreeNode<NPuzzleState> newTreePuzzle;
        if(successor(state,Action.UP) != null)
        {
        	newTreePuzzle = new TreeNode<NPuzzleState>(successor(state,Action.UP), node, Action.UP, 1);
        	successors.add(newTreePuzzle);
        }
        if(successor(state,Action.DOWN) != null)
        {
        	newTreePuzzle = new TreeNode<NPuzzleState>(successor(state,Action.DOWN), node, Action.DOWN, 1);
        	successors.add(newTreePuzzle);
        }
        if(successor(state,Action.LEFT) != null)
        {
        	newTreePuzzle = new TreeNode<NPuzzleState>(successor(state,Action.LEFT), node, Action.LEFT, 1);
        	successors.add(newTreePuzzle);
        }
        if(successor(state,Action.RIGHT) != null)
        {
        	newTreePuzzle = new TreeNode<NPuzzleState>(successor(state,Action.RIGHT), node, Action.RIGHT, 1);
        	successors.add(newTreePuzzle);
        }
        // [end:3]
        return successors;
    }

    public static boolean isGoal(NPuzzleState state, NPuzzleState goalState) {
        boolean desiredState = true;
        // Implement a goal test function
        // Your code should change desiredState to false if the state is not a goal 
        // [start:4]
        if(!state.toString().equals(goalState.toString()))
        {
        	desiredState = false;
        }
        // [end:4]
        return desiredState;
    }

    public static Pair<ArrayList<Action>, Integer> solve(
            NPuzzleState initState, NPuzzleState goalState, 
            Queue<TreeNode<NPuzzleState>> frontier, boolean checkClosedSet, int limit) {
        HashSet<String> closed = new HashSet<>();
        ArrayList<Action> solution = new ArrayList<>();
        int numSteps = 0;
        // Implement Graph Search algorithm
        // Your algorithm should add action to 'solution' and
        // for every node you remove from the frontier add 1 to 'numSteps'
        // [start:5]
        ArrayList<TreeNode<NPuzzleState>> listSuccess = expandSuccessors(new TreeNode<NPuzzleState>(initState));
		frontier.add(new TreeNode<NPuzzleState>(initState));
		numSteps++;
		while(!frontier.isEmpty() && limit>=numSteps)
		{
			TreeNode<NPuzzleState> node = frontier.remove();
			numSteps++;
			if(isGoal(node.getState(),goalState))
			{
				while(node.getParent()!=null) 
				{
					solution.add(0, (Action)node.getAction());
					node = node.getParent();
				}
        		break;
			}
			if(!closed.contains(node.getState().toString()))
			{
				closed.add(node.getState().toString());
				listSuccess = expandSuccessors(node);
				for(TreeNode<NPuzzleState> childNode: listSuccess) 
				{
    				frontier.add(childNode);
    			}
			}
		}
		
        // [end:5]
        return new Pair<ArrayList<Action>, Integer>(solution, numSteps);
    }

    public static class HeuristicComparator implements Comparator<TreeNode<NPuzzleState>> {

        private NPuzzleState goalState;
        private int heuristicNum;
        private boolean isAStar;
        private HashMap<Integer, Pair<Integer, Integer>> goalStateMap = null;

        public HeuristicComparator(NPuzzleState goalState, int heuristicNum, boolean isAStar) {
            this.goalState = goalState;
            this.heuristicNum = heuristicNum;
            this.isAStar = isAStar;
        }

        public int compare(TreeNode<NPuzzleState> n1, TreeNode<NPuzzleState> n2) {
            Double s1V = 0.0;
            Double s2V = 0.0;
            if (this.heuristicNum == 1) {
                s1V = h1(n1.getState());
                s2V = h1(n2.getState());
            } else {
                s1V = h2(n1.getState());
                s2V = h2(n2.getState());
            }
            if (this.isAStar) {  // AStar h(n) + g(n)
                s1V += n1.getPathCost();
                s2V += n2.getPathCost();
            }
            return s1V.compareTo(s2V);
        }

        public double h1(NPuzzleState s) {
            double h = 0.0;
            int[][] board = s.getBoard();
            int[][] goalBoard = goalState.getBoard();
            // Implement misplaced tiles heuristic
            // Your code should update 'h'
            // [start:6]
            // including 0
            for(int i = 0 ; i < board.length ; i++)
            {
            	for(int j = 0 ; j < board.length ; j++)
            	{
            		if(board[i][j] != goalBoard[i][j])
            		{
            			h++;
            		}
            	}
            }
            // [end:6]
            //System.out.println(h);
            s.setEstimatedCostToGoal(h);
            return h;
        }

        public double h2(NPuzzleState s) {
            double h = 0.0;
            int[][] board = s.getBoard();
            int[][] goalBoard = goalState.getBoard();
            // Implement number-of-blocks-away heuristic
            // Your code should update 'value'
            // [start:7]
            // including 0
            int temp;
            for(int i = 0 ; i < board.length ; i++)
            {
	            here:
            	for(int j = 0 ; j < board.length ; j++)
            	{
            		if(board[i][j] != goalBoard[i][j])
            		{
            			temp = board[i][j];
            			for(int k = 0 ; k < board.length ; k++)
            			{
            				for(int l = 0 ; l < board.length ; l++)
            				{
            					if(temp == goalBoard[k][l])
            					{
            						h += Math.abs(i-k) + Math.abs(j-l);
            						continue here;
            					}
            				}
            			}
            		}
            	}
            }
            // [end:7]
            s.setEstimatedCostToGoal(h);
            return h;
        }

    }

    public static void testRun(
            NPuzzleState initState, NPuzzleState goalState, 
            Queue<TreeNode<NPuzzleState>> frontier) {
        if (NPuzzle.isSolvable(initState, goalState)) {
            Pair<ArrayList<Action>, Integer> solution = solve(
                initState, goalState, frontier, true, 500000);
            System.out.println(initState);
            NPuzzleState curState = initState;
            for (Action action : solution.l) {
                curState = successor(curState, action);
                System.out.print("Action: ");
                System.out.println(action.toString());
                System.out.println(curState);
            }
            System.out.print("Number of steps in the solution: ");
            System.out.println(solution.l.size());
            System.out.print("Number of nodes expanded: ");
            System.out.println(solution.r);
        } else{
            System.out.println("Not solvable!");
        }
    }
    
    public static void experiment(
            NPuzzleState goalState, Queue<TreeNode<NPuzzleState>> frontier) {
    	// Add
    	TreeMap<Integer,Pair<ArrayList<Integer>,ArrayList<Integer>>> experiment = new TreeMap<>();
    	
        for (int i = 0; i < 1000; i++){
            NPuzzleState initState = new NPuzzleState(8);  // random
            if (!NPuzzle.isSolvable(initState, goalState)) {
                i--;
                continue;
            }
            // Experiment to evaluate a search setting
            // [start:8]
            
            if( i == 0)
            {
            	System.out.printf("\n%12s%12s%12s%12s%12s\n","length,","step_h1,","step_h2,","num_h1,","num_h2");
            }
            Pair<ArrayList<Integer>,ArrayList<Integer>> pairTemp = null;
            ArrayList<Integer> numNodeEx1 = new ArrayList<Integer>();
            ArrayList<Integer> numNodeEx2 = new ArrayList<Integer>();
			Queue<TreeNode<NPuzzleState>> frontierH1 = new PriorityQueue<>( new HeuristicComparator(goalState, 1, true));
			Queue<TreeNode<NPuzzleState>> frontierH2 = new PriorityQueue<>( new HeuristicComparator(goalState, 2, true));
			Pair<ArrayList<Action>, Integer> solutionH1 = solve(initState, goalState, frontierH1, true, 500000);
			Pair<ArrayList<Action>, Integer> solutionH2 = solve(initState, goalState, frontierH2, true, 500000);
			
			if(experiment.isEmpty())
			{
				numNodeEx1.add(solutionH1.r);
				pairTemp = new Pair<ArrayList<Integer>,ArrayList<Integer>>(numNodeEx1, new ArrayList<Integer>());
				experiment.put(solutionH1.l.size(), pairTemp);
			}
			else
			{
				if(!experiment.containsKey(solutionH1.l.size()))
				{
					numNodeEx1.add(solutionH1.r);
					pairTemp = new Pair<ArrayList<Integer>,ArrayList<Integer>>(numNodeEx1, new ArrayList<Integer>());
					experiment.put(solutionH1.l.size(), pairTemp);
				}
				else
				{
					experiment.get(solutionH1.l.size()).l.add(solutionH1.r);
				}
			}
			
			if(!experiment.containsKey(solutionH2.l.size()))
			{
				numNodeEx2.add(solutionH2.r);
				pairTemp = new Pair<ArrayList<Integer>,ArrayList<Integer>>(new ArrayList<Integer>(), numNodeEx2);
				experiment.put(solutionH2.l.size(), pairTemp);
			}
			else
			{
				experiment.get(solutionH2.l.size()).r.add(solutionH2.r);
			}
        }
        
        for(Entry<Integer, Pair<ArrayList<Integer>,ArrayList<Integer>>> entry : experiment.entrySet()) 
        {
            double sum1 = 0.00;
            double avg1 = 0.00;
            double sum2 = 0.00;
            double avg2 = 0.00;
			Integer key = entry.getKey();
			if(!entry.getValue().l.isEmpty() && !entry.getValue().r.isEmpty())
			{
				for(int i = 0 ; i < entry.getValue().l.size() ; i++)
				{
					sum1 += entry.getValue().l.get(i);
				}
				avg1 = sum1/entry.getValue().l.size();
				for(int i = 0 ; i < entry.getValue().r.size() ; i++)
				{
					sum2 += entry.getValue().r.get(i);
				}
				avg2 = sum2/entry.getValue().r.size();
				System.out.printf("\n%12d,%12.2f,%12.2f,%12d,%12d",key,avg1,avg2,entry.getValue().l.size(),entry.getValue().r.size());
			}
			else if (!entry.getValue().l.isEmpty())
			{
				for(int i = 0 ; i < entry.getValue().l.size() ; i++)
				{
					sum1 += entry.getValue().l.get(i);
				}
				avg1 = sum1/entry.getValue().l.size();
				System.out.printf("\n%12d,%12.2f,%12.2f,%12d,%12d",key,avg1,0.00,entry.getValue().l.size(),0);
			}
			else if (!entry.getValue().r.isEmpty())
			{
				for(int i = 0 ; i < entry.getValue().r.size() ; i++)
				{
					sum2 += entry.getValue().r.get(i);
				}
				avg2 = sum2/entry.getValue().r.size();
				System.out.printf("\n%12d,%12.2f,%12.2f,%12d,%12d",key,0.00,avg2,0,entry.getValue().r.size());
			}
        }
    }

    public static void main(String[] args) {
        NPuzzleState.studentID = 5988070;

        int[][] goalBoard = {{0, 1, 2},{3, 4, 5},{6, 7, 8}};
        NPuzzleState goalState = new NPuzzleState(goalBoard);

        /*
         *  Select an implementation of a frontier from the code below
         */
        
        // Stack
        // Queue<TreeNode<NPuzzleState>> frontier = Collections.asLifoQueue(
        //     new LinkedList<TreeNode<NPuzzleState>>());

        // Queue
        Queue<TreeNode<NPuzzleState>> frontier = new LinkedList<TreeNode<NPuzzleState>>();
        
        // Priority Queue: A* with h1  
        // Queue<TreeNode<NPuzzleState>> frontier = new PriorityQueue<>(
        //     new HeuristicComparator(goalState, 1, true));
        
         //Priority Queue: A* with h2
         //Queue<TreeNode<NPuzzleState>> frontier = new PriorityQueue<>(new HeuristicComparator(goalState, 2, true));
        
        //int[][] easy = {{0, 1, 2},{3, 4, 5},{6, 7, 8}};
//        int[][] easy = {{1, 4, 2},{3, 0, 5},{6, 7, 8}};
//        NPuzzleState initState = new NPuzzleState(easy);

         //int[][] hard = {{7, 2, 4}, {5, 0, 6}, {8, 3, 1}};
         //NPuzzleState initState = new NPuzzleState(hard);

         //testRun(initState, goalState, frontier);
        experiment(goalState, frontier);

    }
}

