import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NPuzzleState extends State {
    public static long studentID = 5988070;
    private static Random random = new Random(studentID);
    int[][] board;
    int r = -1, c = -1;  // blank (0) position

    public NPuzzleState(int N){
        int side = (int) Math.sqrt(N + 1);
        this.board = new int[side][side];
        List<Integer> nums = IntStream.rangeClosed(0, N).boxed().collect(Collectors.toList());
        Collections.shuffle(nums, random);
        for(int i = 0; i < side; i++) {
            for(int j = 0; j < side; j++) {
                this.board[i][j] = nums.get(i*side + j);
                if(this.board[i][j] == 0) {
                    this.r = i;
                    this.c = j;
                }
            }
        }
    }

    public NPuzzleState(int[][] board){
        this.board = board;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(this.board[i][j] == 0) {
                	//System.out.println(i + " " + j);
                    this.r = i;
                    this.c = j;
                    break;
                }
            }
            if (this.r!=-1 && this.c!=-1) {
                break;
            }
        }
    }

    public int getR(){ return r; }
    public int getC(){ return c; }
    public int[][] getBoard() { return board; }

    public int[][] copyBoard() {
        int s = this.board.length;
        int[][] newBoard = new int[s][s];
        for(int i = 0; i < s; i++){
            for(int j = 0; j < s; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    public String toString(){
        int digits = (int) Math.log10(Math.pow(board.length, 2)) + 1;
        String format = "%0" + digits + "d";
        StringBuilder display = new StringBuilder();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                display.append(String.format(format, board[i][j]));
                display.append(' ');
            }
            display.append('\n');
        }
        return display.substring(0, display.length()-1);
    }
}