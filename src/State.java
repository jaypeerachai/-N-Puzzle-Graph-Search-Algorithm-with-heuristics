public abstract class State {
    private double estimatedCostToGoal = Double.POSITIVE_INFINITY;  // h(n)

    public double getEstimatedCostToGoal(){
        return this.estimatedCostToGoal;
    }

    public void setEstimatedCostToGoal(double h){
        this.estimatedCostToGoal = h;
    }

}