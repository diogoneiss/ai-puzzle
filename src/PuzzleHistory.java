import java.util.ArrayList;
import java.util.List;

public class PuzzleHistory {
    private List<PuzzleState> history = new ArrayList<>();

    public void store(PuzzleState state) {
        history.add(state);
    }

    public void printGrids(){
        for(int i=0;i<history.size(); i++){
            var current = history.get(i);
            var lastMovement = "None";
            var movements = current.getPreviousMovements();
            if(!movements.isEmpty()){
                lastMovement = movements.get(movements.size()-1).toString();
            }
            System.out.printf("Grid %d. Movement: %s\n", i, lastMovement );
            current.getGrid().display();

        }
    }
    public String formatMovements(){
        StringBuilder movementList = new StringBuilder();

        for(var movements : this.getLastState().getPreviousMovements()){
            movementList.append(movements).append(" ");
        }

        return movementList.toString();
    }

    public List<PuzzleState> getHistory() {
        return history;
    }

    public PuzzleState getLastState() {
        return history.get(history.size() - 1);
    }


}