    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package nqueensbfs;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.LinkedList;

    /**
     *
     * @author BenDiliberto
     */
    public class NQueensBFS {

        static int N;
        static LinkedList<int[][]> boards = new LinkedList();
        static int[][] solution = null;

        static void displayBoard(int[][] board){
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[i].length;j++){
                    //Prints out state that is passed
                    System.out.print("["+board[i][j]+"]");
                }
                System.out.println();
            }
        }

        static int checkSolution(int[][] board){
            int queenCount = 0;
            //Iterate over board to count the amount of queens in a state
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[i].length;j++){
                    if(board[i][j]==1){
                        queenCount++;
                    }
                }
            }
            return queenCount;
        }

        static boolean isValid(int[][] board, int row, int col){
            for(int i=0;i<board.length;i++){
                //Check column
                if(board[row][i]==1){
                    return false;
                }
                //Check row
                if(board[i][col]==1){
                    return false;
                }
            }
            int x=0,y=0;
            for(x=row,y=col;x>=0 && y>=0;x--,y--){
                //Check up-left diagonal
                if(board[x][y]==1){
                    return false;
                }
            }
            for(x=row,y=col;x>N && y>N;x++,y++){
                //Check down-right diagonal
                if(board[x][y]==1){
                    return false;
                }
            }
            for(x=row,y=col;x<N && y>=0;x++,y--){
                //Check down-left diagonal
                if(board[x][y]==1){
                    return false;
                }
            }
            for(x=row,y=col;x>=0 && y<N;x--,y++){
                //Check up-right diagonal
                if(board[x][y]==1){
                    return false;
                }
            }
            return true;
        }

        static void addQueen(int[][] board){
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[i].length;j++){
                    //Check if state valid
                    if(isValid(board,i,j)){
                        //Check if the current state has the correct queens for this step
                        if(checkSolution(board) == i){
                            int[][] temp = new int[N][N];
                            for(int x = 0; x < N; x++){
                                for(int xx = 0; xx < N; xx++){
                                    temp[x][xx] = board[x][xx];
                                }
                            }
                            temp[i][j] = 1;
                            //Add new child state to queue
                            boards.add(temp);
                        }
                    }
                }
            }
        }

        static void placeInitialQueens(){
            for(int i=0;i<N;i++){
                int[][] board = new int[N][N];
                board[0][i] = 1;
                //Create initial states
                boards.add(board);
            }
        }

        static void getInput() throws IOException{
            System.out.println("Please enter the amount of queens you wish to place between 3 and 11");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
            String text = in.readLine();
            N = Integer.parseInt(text);
        }

        public static void main(String[] args) throws IOException {
            boolean correct = false;
            do{
                correct = false;
                getInput();
                if(N>3 && N<11){
                    correct=true;
                    placeInitialQueens();
                    do{
                            checkSolution(boards.get(0));
                            if(checkSolution(boards.get(0)) == N){
                                //If the head of queue is solution then print it
                                displayBoard(boards.get(0));
                                System.exit(0);
                            }else{
                                //If "" "" is not solution then create children of state
                                addQueen(boards.get(0));
                                boards.remove(0);
                        }
                    }while(boards.size()>0);
                }
            }while(!correct);
        }
    }
