public class Board {

    // The following four constants were defined in the starter code (kt54)
    private static final int  DEFAULT_SIZE = 8;
    private static final char FREE         = '.';
    private static final char WHITE_MAN    = '⛀';
    private static final char BLACK_MAN    = '⛂';

    // The following constants are needed for Part 4
    private static final char WHITE_KING   = '⛁';
    private static final char BLACK_KING   = '⛃';

    private int boardsize;
    private char[][] board;
    private char piece;
    
    // Default constructor was provided by the starter code. Extend as needed (kt54)

    public Board() {
        this.boardsize = DEFAULT_SIZE;

        board = new char[boardsize][boardsize];

        // Clear all playable fields

        for(int x=0; x<boardsize; x++)
            for(int y=0; y<boardsize; y++)
                board[x][y] = FREE;

        // Initialises the board
        for(int i = 0; i<boardsize; i+=2){
            board[i+1][0] = BLACK_MAN;
            board[i][1]   = BLACK_MAN;
            board[i+1][2] = BLACK_MAN;

            board[i][boardsize-3]  = WHITE_MAN;
            board[i+1][boardsize-2]= WHITE_MAN;
            board[i][boardsize-1]  = WHITE_MAN;
        }
    }

    // Prints the board. This method was provided with the starter code. Better not
    // modify to ensure

    // output consistent with the autochecker (kt54)

    public void printBoard() {
        // Print the letters at the top
        System.out.print(" ");
        for (int x = 0; x < boardsize; x++)
            System.out.print(" " + (char) (x + 'a'));
        System.out.println();

        for (int y = 0; y < boardsize; y++)
        {
            // Print the numbers on the left side
            System.out.print(y + 1);

            // Print the actual board fields
            for (int x = 0; x < boardsize; x++) {
                System.out.print(" ");
                char value = board[x][y];
                if (value == FREE) {
                    System.out.print(".");
                } else if (value == WHITE_MAN || value == BLACK_MAN ||
                        value == WHITE_KING || value == BLACK_KING) {
                    System.out.print(value);

                } else {
                    System.out.print("X");
                }
            }

            // Print the numbers on the right side
            System.out.println(" " + (y + 1));
        }

        // Print the letters at the bottom

        System.out.print(" ");
        for (int x = 0; x < boardsize; x++)
            System.out.print(" " + (char) (x + 'a'));
        System.out.print("\n\n");

    }

    public int getBoardSize() {
        return DEFAULT_SIZE;
    }

    public boolean isPieceThere(int col, int row, char piece) {
        if (board[col][row] == piece) {
            return true;
        }
        return false;
    }

    public boolean isMoveLegal(String pos1, String pos2, char piece) {
        int startColIndex = getColIndex(pos1);
        int startRowIndex = getRowIndex(pos1);
        
        int endColIndex = getColIndex(pos2);
        int endRowIndex = getRowIndex(pos2);
        
        
        if (isPieceThere(startColIndex, startRowIndex, piece) && isPieceThere(endColIndex, endRowIndex, FREE)
                && isMoveDiagonal(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)) {
            return true;
            
        }
        return false;
    }

    public boolean isMoveDiagonal(int startColIndex, int startRowIndex, int endColIndex, int endRowIndex, char piece) {
        if (startColIndex == 0) {
            if (endColIndex == startColIndex + 1 && endRowIndex == startRowIndex - 1 && (piece == WHITE_MAN|| piece == WHITE_KING|| piece == BLACK_KING)||
                endColIndex == startColIndex + 1 && endRowIndex == startRowIndex + 1 && (piece == BLACK_MAN|| piece == BLACK_KING|| piece == WHITE_KING)) {
                    
                return true;
            }
            else if(checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
                    return true;
                }
            }
        else if (startColIndex == DEFAULT_SIZE - 1) {
            if (endColIndex == startColIndex - 1 && endRowIndex == startRowIndex - 1 && (piece == WHITE_MAN|| piece == WHITE_KING|| piece == BLACK_KING) ||
                endColIndex == startColIndex - 1 && endRowIndex == startRowIndex + 1 && (piece == BLACK_MAN|| piece == WHITE_KING|| piece == BLACK_KING)) {
                return true;
            }
            else if(checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
                return true;
            }
        }
        else if (startRowIndex == 0) {
            if (endRowIndex == startRowIndex + 1 && endColIndex == startColIndex + 1 && (piece == BLACK_MAN|| piece == WHITE_KING|| piece == BLACK_KING) ||
                endRowIndex == startRowIndex + 1 && endColIndex == startColIndex - 1 && (piece == BLACK_MAN|| piece == WHITE_KING|| piece == BLACK_KING)) {
                return true;
            }
            else if(checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
                return true;
            }
        }
        else if (startRowIndex == DEFAULT_SIZE - 1) {
            if (endRowIndex == startRowIndex - 1 && endColIndex == startColIndex + 1 && (piece == WHITE_MAN|| piece == WHITE_KING|| piece == BLACK_KING) ||
                endRowIndex == startRowIndex - 1 && endColIndex == startColIndex - 1 && (piece == WHITE_MAN|| piece == WHITE_KING|| piece == BLACK_KING)) {
                return true;
            }
            else if(checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
                return true;
            }
        }
        else if (endColIndex == startColIndex + 1 && endRowIndex == startRowIndex + 1 && (piece == BLACK_MAN|| piece == WHITE_KING|| piece == BLACK_KING) ||
                 endColIndex == startColIndex - 1 && endRowIndex == startRowIndex + 1 && (piece == BLACK_MAN|| piece == WHITE_KING|| piece == BLACK_KING)) {
            return true;
        }
        else if (endColIndex == startColIndex + 1 && endRowIndex == startRowIndex - 1 && (piece == WHITE_MAN|| piece == WHITE_KING|| piece == BLACK_KING) ||
                endColIndex == startColIndex - 1 && endRowIndex == startRowIndex - 1 &&  (piece == WHITE_MAN|| piece == WHITE_KING|| piece == BLACK_KING)) {
            return true;
        }
        else if (checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)) {
            return true;
        }
        return false;
    }

    public boolean makeMove(String pos1, String pos2, char piece) {

        int startColIndex = getColIndex(pos1);
        int startRowIndex = getRowIndex(pos1);
        
        int endColIndex = getColIndex(pos2);
        int endRowIndex = getRowIndex(pos2);

        if (isMoveLegal(pos1, pos2, piece)
            && isMoveDiagonal(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)) {
            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, piece);
            return true;
        }

        else if (isMoveLegal(pos1, pos2, piece)
            && checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)) {
            return true;
        }
        
        return false;
    }

    public char getFree() {
        return FREE;
    }


    public char getWhiteMan() {
        return WHITE_MAN;
    }

    public char getBlackMan() {
        return BLACK_MAN;
    }

    public boolean checkCanTake(int startColIndex, int startRowIndex, int endColIndex,
                                                        int endRowIndex, char piece) {
                                                            
        if (endColIndex == startColIndex + 2 && endRowIndex == startRowIndex + 2 ||
            endColIndex == startColIndex - 2 && endRowIndex == startRowIndex + 2 ||
            endColIndex == startColIndex + 2 && endRowIndex == startRowIndex - 2 ||
            endColIndex == startColIndex - 2 && endRowIndex == startRowIndex - 2) {

            //checks black can take white and white king can take black going backwards
            if (endColIndex == startColIndex + 2 && endRowIndex == startRowIndex + 2 && (piece == BLACK_MAN || piece == BLACK_KING || piece == WHITE_KING)){
                if(piece == BLACK_MAN || piece == BLACK_KING){
                    if(board[startColIndex + 1][startRowIndex + 1] == WHITE_MAN || board[startColIndex + 1][startRowIndex + 1] == WHITE_KING) {

                        board[startColIndex + 1][startRowIndex + 1] = FREE;

                        if(piece == BLACK_MAN){
                            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, BLACK_MAN);
                            return true;
                        }
                        else{
                            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, BLACK_KING);
                            return true;
                        }
                    }
                }
                else{ //i.e. a white king
                    if(board[startColIndex + 1][startRowIndex + 1] == BLACK_MAN || board[startColIndex + 1][startRowIndex + 1] == BLACK_KING){
                        board[startColIndex + 1][startRowIndex + 1] = FREE;
                        movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, WHITE_KING);
                        return true;
                    }
                }
            }
            

            else if (endColIndex == startColIndex - 2 && endRowIndex == startRowIndex + 2 && (piece == BLACK_MAN || piece == BLACK_KING || piece == WHITE_KING)){
                if(piece == BLACK_MAN || piece == BLACK_KING){
                    if(board[startColIndex - 1][startRowIndex + 1] == WHITE_MAN ||board[startColIndex - 1][startRowIndex + 1] == WHITE_KING) {

                    board[startColIndex - 1][startRowIndex + 1] = FREE;
                        if(piece == BLACK_MAN){
                            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, BLACK_MAN);
                            return true;
                        }
                        else{
                            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, BLACK_KING);
                            return true;
                        }
                    }
                }
                else{
                    if(board[startColIndex - 1][startRowIndex + 1] == BLACK_MAN ||board[startColIndex - 1][startRowIndex + 1] == BLACK_KING){
                    board[startColIndex - 1][startRowIndex + 1] = FREE;
                    movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, WHITE_KING );
                    return true;
                    }
                }
            }
            //checks white can take black and black kings can take white going backwards
            else if (endColIndex == startColIndex + 2 && endRowIndex == startRowIndex - 2 && (piece == WHITE_MAN || piece == WHITE_KING || piece == BLACK_KING)){
                
                if(piece == WHITE_MAN || piece == WHITE_KING){
                        if(board[startColIndex + 1][startRowIndex - 1] == BLACK_MAN || board[startColIndex + 1][startRowIndex - 1] == BLACK_KING) {

                        board[startColIndex + 1][startRowIndex - 1] = FREE;

                        if(piece == WHITE_MAN){
                            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, WHITE_MAN);
                            return true;
                        }
                        else{
                            movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, WHITE_KING);
                            return true;
                        }
                    }
                }
                else{ //i.e. a black king
                    if(board[startColIndex + 1][startRowIndex - 1] == WHITE_MAN || board[startColIndex + 1][startRowIndex - 1] == WHITE_KING){
                        board[startColIndex + 1][startRowIndex - 1] = FREE;
                        movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, BLACK_KING);
                        return true;
                    }
                } 
            }

            else if (endColIndex == startColIndex - 2 && endRowIndex == startRowIndex - 2 && (piece == WHITE_MAN || piece == WHITE_KING || piece == BLACK_KING)){
                
                if(piece == WHITE_MAN || piece == WHITE_KING){
                    if(board[startColIndex - 1][startRowIndex - 1] == BLACK_MAN || board[startColIndex - 1][startRowIndex - 1] == BLACK_KING) {

                    board[startColIndex - 1][startRowIndex - 1] = FREE;

                    if(piece == WHITE_MAN){
                        movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, WHITE_MAN);
                        return true;
                    }
                    else{
                        movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, WHITE_KING);
                        return true;
                        }
                    }
                }
                else{
                    if(board[startColIndex - 1][startRowIndex - 1] == WHITE_MAN ||board[startColIndex -1][startRowIndex -1] == WHITE_KING){
                       board[startColIndex - 1][startRowIndex - 1] = FREE;
                       movePiece(startColIndex, startRowIndex, endColIndex, endRowIndex, BLACK_KING);
                       return true;
                    }
                }
            }
        }return false;
    }
    
    public void movePiece(int startColIndex, int startRowIndex, int endColIndex, int endRowIndex, char piece){
        board[startColIndex][startRowIndex] = FREE;
        board[endColIndex][endRowIndex] = piece;
    }

    public int getColIndex(String pos){
        char col = pos.charAt(0);
        int startColIndex = (int) col - 97;// turns ascii number into equivalent index in array
        return startColIndex;
    }
    public int getRowIndex(String pos){
        char row = pos.charAt(1);
        int startRowIndex = Character.getNumericValue(row) - 1; //to get array index
        return startRowIndex;
    }

    public boolean checkGameOver(char piece){
        for(int row=0; row < getBoardSize(); row ++)
            for(int col=0;col < getBoardSize(); col ++){
                if(board[row][col] == piece){
                    return false;
                }
            }
        return true;
    }

    public char getWhiteKing() {
        return WHITE_KING;
    }

    public char getBlackKing() {
        return BLACK_KING;
    }

    public char becomeKing(int[] index){
        if(board[index[0]][index[1]] == BLACK_MAN){
            return piece = board[index[0]][index[1]] = BLACK_KING;
        }
        else if(board[index[0]][index[1]] == WHITE_MAN){
            return piece = board[index[0]][index[1]] = WHITE_KING;
        } return 'n';
    }
    
    //returns the coordinates of any men that reach the other side of the board

    public int[] checkForKing(){
        int[] index = new int[2];
        index[0] = -1;
        index[1] = -1;
        for(int col = 0; col < DEFAULT_SIZE - 1; col++){
            if(board[col][DEFAULT_SIZE - 1] == BLACK_MAN){
                index[0] = col;
                index[1] = DEFAULT_SIZE - 1;
            }
        }
        for(int col = 0; col < DEFAULT_SIZE - 1; col++){
            if(board[col][0] == WHITE_MAN){
                index[0] = col;
                index[1] = 0;
                
            }
        } return index;        
    }

    public boolean isPieceBlack(int col, int row){
        if(board[col][row] == BLACK_MAN || board[col][row] == BLACK_KING){
            return true;
        }return false;
    }

    public boolean isPieceWhite(int col, int row){
        if(board[col][row] == WHITE_MAN || board[col][row] == WHITE_KING){
            return true;
        }return false;
    }
    public boolean isKing(int col, int row){
        if(board[col][row] == WHITE_KING || board[col][row] == BLACK_KING){
            return true;
        }return false;
    }

    public int[] mustBlackTake(){
        int[] whereMustTake = new int[4];
        whereMustTake[0] = -1;
        whereMustTake[1] = -1;
        whereMustTake[2] = -1;
        whereMustTake[3] = -1;
        for(int col=0; col < DEFAULT_SIZE; col++){
            for(int row = 0; row < DEFAULT_SIZE; row ++){

                    if((board[col][row]== BLACK_MAN || board[col][row] == BLACK_KING)){
                        if(col < DEFAULT_SIZE -2 & row < DEFAULT_SIZE -2){
                            if((board[col + 1][row + 1] == WHITE_MAN || board[col + 1][row + 1] == WHITE_KING) &&
                            board[col + 2][row +2] == FREE){   
                                whereMustTake[0] = col ;
                                whereMustTake[1] = row;
                                whereMustTake[2] = col + 2;
                                whereMustTake[3] = row + 2;
                                return whereMustTake;
                                
                            }
                            else{
                                if(col > 1 & row < DEFAULT_SIZE -2){
                                    if((board[col - 1][row + 1] == WHITE_MAN || board[col - 1][row + 1] == WHITE_KING) &&
                                    board[col-2][row+2] == FREE){
                                        whereMustTake[0] = col ;
                                        whereMustTake[1] = row;
                                        whereMustTake[2] = col - 2;
                                        whereMustTake[3] = row + 2;
                                        return whereMustTake;      
                                    }
                                }
                            }
                        }
                    }  
            
                if(board[col][row] == BLACK_KING){ //extra moves for black king
                        if(row > 1 && col < DEFAULT_SIZE -2){
                            if((board[col + 1][row - 1] == WHITE_MAN || board[col + 1][row - 1] == WHITE_KING) &&
                                board[col + 2][row -2] == FREE){   
                                whereMustTake[0] = col ;
                                whereMustTake[1] = row;
                                whereMustTake[2] = col + 2;
                                whereMustTake[3] = row - 2;
                                return whereMustTake;
                            }
                        }
                        else{
                            if(col > 1 & row > 1){
                                if((board[col - 1][row - 1] == WHITE_MAN || board[col - 1][row - 1] == WHITE_KING) &&
                                    board[col-2][row-2] == FREE){
                                        whereMustTake[0] = col ;
                                        whereMustTake[1] = row;
                                        whereMustTake[2] = col -2;
                                        whereMustTake[3] = row -2;
                                        return whereMustTake;
                                }
                            }
                        }    
                    } 
                }    
            }return whereMustTake;       
        }
        
        public boolean isBlackCapturePossible(int startCol, int startRow){
            for(int col=startCol -2; col < startCol+3; col++){
                for(int row = 0; row < DEFAULT_SIZE; row ++){
    
                        if((board[startCol][startRow]== BLACK_MAN || board[startCol][startRow] == BLACK_KING)){
                            if(startCol < DEFAULT_SIZE -2 & startRow < DEFAULT_SIZE -2){
                                if((board[startCol + 1][startRow + 1] == WHITE_MAN || board[startCol + 1][startRow + 1] == WHITE_KING) &&
                                board[startCol + 2][startRow +2] == FREE){   
                                    return true;
                                    
                                }
                                else{
                                    if(startCol > 1 & startRow < DEFAULT_SIZE -2){
                                        if((board[startCol - 1][startRow + 1] == WHITE_MAN || board[startCol - 1][startRow + 1] == WHITE_KING) &&
                                        board[startCol-2][startRow+2] == FREE){
                                            return true;    
                                        }
                                    }
                                }
                            }
                        }  
                
                    if(board[startCol][startRow] == BLACK_KING){ //extra moves for black king
                            if(startRow > 1 && startCol < DEFAULT_SIZE -2){
                                if((board[startCol + 1][startRow - 1] == WHITE_MAN || board[startCol + 1][startRow - 1] == WHITE_KING) &&
                                    board[startCol + 2][startRow -2] == FREE){   
                                    return true;
                                }
                            }
                            else{
                                if(startCol > 1 & startRow > 1){
                                    if((board[startCol - 1][startRow - 1] == WHITE_MAN || board[startCol - 1][startRow - 1] == WHITE_KING) &&
                                        board[startCol-2][startRow-2] == FREE){
                                            return true;
                                    }
                                }
                            }    
                        } 
                    }    
                }return false;       
            }
        
        
    public int[] mustWhiteTake(){
        int[] whereMustTake = new int[4];
        whereMustTake[0] = -1;
        whereMustTake[1] = -1;
        whereMustTake[2] = -1;
        whereMustTake[3] = -1;
        for(int col=0; col < DEFAULT_SIZE; col++){
            for(int row = 0; row < DEFAULT_SIZE; row ++){

                if((board[col][row]== WHITE_MAN || board[col][row] == WHITE_KING)){
                    if(col < DEFAULT_SIZE -2 && row > 1){
                        if((board[col + 1][row - 1] == BLACK_MAN || board[col + 1][row - 1] == BLACK_KING) &&
                        board[col + 2][row -2] == FREE){   
                            whereMustTake[0] = col ;
                            whereMustTake[1] = row;
                            whereMustTake[2] = col + 2;
                            whereMustTake[3] = row - 2;
                            return whereMustTake;
                        }
                        else{
                            if(col > 1 & row > 1){
                                if((board[col - 1][row - 1] == BLACK_MAN || board[col - 1][row - 1] == BLACK_KING) &&
                                board[col-2][row-2] == FREE){
                                    whereMustTake[0] = col ;
                                    whereMustTake[1] = row;
                                    whereMustTake[2] = col -2;
                                    whereMustTake[3] = row -2;
                                    return whereMustTake;
                                }
                            }
                        }
                    } 
                }
                else if(board[col][row] == WHITE_KING){ // extra check for a white king
                        if(col < DEFAULT_SIZE -2 & row < DEFAULT_SIZE -2){
                            if((board[col + 1][row + 1] == BLACK_MAN || board[col + 1][row + 1] == BLACK_KING) &&
                            board[col + 2][row +2] == FREE){   
                                whereMustTake[0] = col ;
                                whereMustTake[1] = row;
                                whereMustTake[2] = col + 2;
                                whereMustTake[3] = row + 2;
                                return whereMustTake;
                            }
                        }
                        else{
                            if(col > 1 && row > 1){
                                if((board[col - 1][row + 1] == BLACK_MAN || board[col - 1][row + 1] == BLACK_KING) &&
                                board[col-2][row+2] == FREE){
                                    whereMustTake[0] = col ;
                                    whereMustTake[1] = row;
                                    whereMustTake[2] = col - 2;
                                    whereMustTake[3] = row + 2;
                                    return whereMustTake;
                                }
                            }
                        }
                    }
                }
                    
        } return whereMustTake;
    }

    public boolean isWhiteCapturePossible(int startCol, int startRow){
        
        for(int col=startCol-2; col < startCol+3; col++){
            for(int row = startRow-2; row < startRow+3; row ++){

                if((board[startCol][startRow]== WHITE_MAN || board[startCol][startRow] == WHITE_KING)){
                    if(startCol < DEFAULT_SIZE -2 && startRow > 1){
                        if((board[startCol + 1][startRow - 1] == BLACK_MAN || board[startCol + 1][startRow - 1] == BLACK_KING) &&
                        board[startCol + 2][startRow -2] == FREE){   
                            return true;
                        }
                        else{
                            if(startCol > 1 & startRow > 1){
                                if((board[startCol - 1][startRow - 1] == BLACK_MAN || board[startCol - 1][startRow - 1] == BLACK_KING) &&
                                board[startCol-2][startRow-2] == FREE){
                                    return true;
                                }
                            }
                        }
                    } 
                }
                else if(board[startCol][startRow] == WHITE_KING){ // extra check for a white king
                        if(startCol < DEFAULT_SIZE -2 & startRow < DEFAULT_SIZE -2){
                            if((board[startCol + 1][startRow + 1] == BLACK_MAN || board[startCol + 1][startRow + 1] == BLACK_KING) &&
                            board[startCol + 2][startRow +2] == FREE){   
                                return true;
                            }
                        }
                        else{
                            if(startCol > 1 && startRow > 1){
                                if((board[startCol - 1][startRow + 1] == BLACK_MAN || board[startCol - 1][startRow + 1] == BLACK_KING) &&
                                board[startCol-2][startRow+2] == FREE){
                                    return true;
                                }
                            }
                        }
                    }
                }
                    
        } return false;
    }

    public boolean isMidPointFree(String pos1, String pos2){
        int col1 = getColIndex(pos1);
        int row1 = getRowIndex(pos1);
        int col2 = getColIndex(pos2);
        int row2 = getRowIndex(pos2);

        int midCol = (col1 + col2)/2;
        int midRow = (row1 + row2)/2;

        if(col2 == col1 +2 || col2 == col1-2){
        
        if(board[midCol][midRow] == FREE){
            return true;
        }
    }
        return false;

    }

    
}