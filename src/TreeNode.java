public class TreeNode<T extends State> {
    private TreeNode<T> parent;
    private T state;
    private int depth;
    private Object action;  // an action from parent to this node
    private double pathCost;  // g(n)
    
    // root node
    public TreeNode(T state) {
        this.parent = null;
        this.depth = 0;
        this.pathCost = 0;
        this.state = state;
    }
    
    public TreeNode(T state, TreeNode<T> parent, Object action, double stepCost) {
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
        this.pathCost = parent.getPathCost() + stepCost;
        this.state = state;
        this.action = action;
    }

    public T getState(){ return this.state; }
    public int getDepth(){ return this.depth; }
    public double getPathCost(){ return this.pathCost; }
    public TreeNode<T> getParent(){ return this.parent; }
    public Object getAction(){ return this.action; }
    public double getEstimatedCostToGoal(){
        return this.state.getEstimatedCostToGoal();  // heuristic depends on the state
    }
}