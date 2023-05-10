import java.util.Scanner;

public class ChessBoard {
    Square[][] board;
    Piece[] whiteCapture;
    Piece[] blackCapture;
    public boolean whiteTurn = true;
    public Checker check;
    Scanner scanner;
    int w = 0;
    int b = 0;
    Square[] lastMove;

    public ChessBoard(){
        scanner = new Scanner(System.in);
        check = new Checker();
        lastMove = new Square[2];
        whiteCapture = new Piece[7];
        blackCapture = new Piece[7];
        initializeCapturePieces();
        initializeBoard();
    }

    public void initializeCapturePieces(){
        for(int i=0; i < 7; i++){
            whiteCapture[i] = null;
            blackCapture[i] = null;
        }
    }

    private void initializeBoard(){
        board = new Square[8][8];

        board[0][0] = new Square(0, 0, new Rook(false,"\u001B[31mR\u001B[0m"));
        board[0][1] = new Square(0, 1, new Knight(false,"\u001B[31mN\u001B[0m"));
        board[0][2] = new Square(0, 2, new Bishop(false,"\u001B[31mB\u001B[0m"));
        board[0][3] = new Square(0, 3, new Queen(false,"\u001B[31mQ\u001B[0m"));
        board[0][4] = new Square(0, 4, new King(false,"\u001B[31mK\u001B[0m"));
        board[0][5] = new Square(0, 5, new Bishop(false,"\u001B[31mB\u001B[0m"));
        board[0][6] = new Square(0, 6, new Knight(false,"\u001B[31mN\u001B[0m"));
        board[0][7] = new Square(0, 7, new Rook(false,"\u001B[31mR\u001B[0m"));

        board[1][0] = new Square(1, 0, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][1] = new Square(1, 1, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][2] = new Square(1, 2, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][3] = new Square(1, 3, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][4] = new Square(1, 4, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][5] = new Square(1, 5, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][6] = new Square(1, 6, new Pawn(false,"\u001B[31mP\u001B[0m"));
        board[1][7] = new Square(1, 7, new Pawn(false,"\u001B[31mP\u001B[0m"));

        board[6][0] = new Square(6, 0, new Pawn(true,"P"));
        board[6][1] = new Square(6, 1, new Pawn(true,"P"));
        board[6][2] = new Square(6, 2, new Pawn(true,"P"));
        board[6][3] = new Square(6, 3, new Pawn(true,"P"));
        board[6][4] = new Square(6, 4, new Pawn(true,"P"));
        board[6][5] = new Square(6, 5, new Pawn(true,"P"));
        board[6][6] = new Square(6, 6, new Pawn(true,"P"));
        board[6][7] = new Square(6, 7, new Pawn(true,"P"));

        board[7][0] = new Square(7, 0, new Rook(true,"R"));
        board[7][1] = new Square(7, 1, new Knight(true,"N"));
        board[7][2] = new Square(7, 2, new Bishop(true,"B"));
        board[7][3] = new Square(7, 3, new Queen(true,"Q"));
        board[7][4] = new Square(7, 4, new King(true,"K"));
        board[7][5] = new Square(7, 5, new Bishop(true,"B"));
        board[7][6] = new Square(7, 6, new Knight(true,"N"));
        board[7][7] = new Square(7, 7, new Rook(true,"R"));

        for(int i = 2; i < 6; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j, null);
            }
        } 
    }

    public void printboard() {
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i +"| ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j].getName() + " ");
            }
            System.out.println();
        }
        System.out.println("  ++++++++++++++++");
        System.out.println("   A B C D E F G H");
    }

    public void printwhiteCapture(){
        for (int i = 0; i < 7; i++) {
            if(whiteCapture[i] != null){
                System.out.println(i+1+" "+whiteCapture[i].getPName());
            }
        }
    }
    
    public void printblackCapture(){
        for (int i = 0; i < 7; i++) {
            if(blackCapture[i] != null){
                System.out.println(i+1+" "+blackCapture[i].getPName());
            }
        }
    }  

    public Square getSquare(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null; // Return null if x or y is out of range
        }
        return board[x][y];
    }

    public Piece getPiece(Square square) {
        int row = square.getX();
        int col = square.getY();
        return board[row][col].getPiece();
    }

    public Square getLastMoveStart(){
        return lastMove[1];
    }

    public Square getLastMoveEnd(){
        return lastMove[1];
    }

    public boolean setMove(String start, String end) {
        Square str = this.getSquareFromNotation(start);
        Square en = this.getSquareFromNotation(end);
    
        if (str == null || en == null) {
            return false;
        }
    
        Piece piece = str.getPiece();
        if (piece instanceof King) {
            King king = (King) piece;
            if (king.castling(this, str, en)) {
                updateMoveData(str, en);
                return true;
            }
        }
    
        if (piece == null) {
            System.out.println("No piece on starting square!");
            return false;
        }
    
        if (str.getX() > 7 || str.getY() > 7 || en.getX() > 7 || en.getY() > 7) {
            System.out.println("Invalid move!");
            return false;
        }
    
        if (!piece.canMove(this, str, en)) {
            System.out.println("Invalid move!");
            return false;
        }
    
        if (check.isCheckAfterMove(this, str, en, whiteTurn)) {
            System.out.println("Invalid move!");
            return false;
        }
    
        Piece capturedPiece = en.getPiece();
        if (capturedPiece != null) {
            if (!(capturedPiece instanceof Pawn)) {
                if (capturedPiece.isWhite()) {
                    whiteCapture[w++] = capturedPiece;
                } else {
                    blackCapture[b++] = capturedPiece;
                }
            }
        }
    
        en.setPiece(piece);
        str.setPiece(null);
        en.getPiece().setFirstMove();
        promotion(en);
        updateMoveData(str, en);
        return true;
    }

    public boolean setMove(Square start, Square end) {
        Square str = start;
        Square en = end;
    
        if (str == null || en == null) {
            return false;
        }
    
        Piece piece = str.getPiece();
        if (piece instanceof King) {
            King king = (King) piece;
            if (king.castling(this, str, en)) {
                updateMoveData(str, en);
                return true;
            }
        }
    
        if (piece == null) {
            System.out.println("No piece on starting square!");
            return false;
        }
    
        if (str.getX() > 7 || str.getY() > 7 || en.getX() > 7 || en.getY() > 7) {
            System.out.println("Invalid move!");
            return false;
        }
    
        if (!piece.canMove(this, str, en)) {
            System.out.println("Invalid move!");
            return false;
        }
    
        if (check.isCheckAfterMove(this, str, en, whiteTurn)) {
            System.out.println("Invalid move!");
            return false;
        }
    
        Piece capturedPiece = en.getPiece();
        if (capturedPiece != null) {
            if (!(capturedPiece instanceof Pawn)) {
                if (capturedPiece.isWhite()) {
                    whiteCapture[w++] = capturedPiece;
                } else {
                    blackCapture[b++] = capturedPiece;
                }
            }
        }
    
        en.setPiece(piece);
        str.setPiece(null);
        en.getPiece().setFirstMove();
        promotion(en);
        updateMoveData(str, en);
        return true;
    }

    private void updateMoveData(Square start, Square end) {
        whiteTurn = !whiteTurn;
        lastMove[0] = start;
        lastMove[1] = end;
    }

    public Square getSquareFromNotation(String not){
        char first = not.charAt(0);
        char second = not.charAt(1);

        int one = 0;
        int two = 0;
        switch(first){
            case 'a':
                one = 0; break;
            case 'b':
                one = 1; break;
            case 'c':
                one = 2; break;
            case 'd':
                one = 3; break;
            case 'e':
                one = 4; break;
            case 'f':
                one = 5; break;
            case 'g':
                one = 6; break;
            case 'h':
                one = 7; break;
            default:
                System.out.println("Zła literka"); break;
        }
        switch(second){
            case '1':
                two = 7; break;
            case '2':
                two = 6; break;
            case '3':
                two = 5; break;
            case '4':
                two = 4; break;
            case '5':
                two = 3; break;
            case '6':
                two = 2; break;
            case '7':
                two = 1; break;
            case '8':
                two = 0; break;
            default:
                System.out.println("Zła cyferka"); break;
        }
        return this.board[two][one];
    }
    
    public void promotion(Square end) {
        Piece piece = end.getPiece();
        boolean isWhite = piece.isWhite();
        int x = end.getX();
    
        if (piece instanceof Pawn && ((isWhite && x == 0) || (!isWhite && x == 7))) {
            if (isWhite) {
                printwhiteCapture();
            } else {
                printblackCapture();
            }
    
            System.out.println("Podaj na jaką figurę chcesz zamienić piona: ");
            int choice = scanner.nextInt();
    
            Piece[] captureArray = isWhite ? whiteCapture : blackCapture;
    
            if (captureArray[choice - 1] != null) {
                end.setPiece(captureArray[choice - 1]);
                captureArray[choice - 1] = null;
                sortTable(captureArray);
    
                if (isWhite) {
                    w--;
                } else {
                    b--;
                }
            }
        }
    }
    
    public void sortTable(Piece[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                for (int j = i + 1; j < table.length; j++) {
                    if (table[j] != null) {
                        table[i] = table[j];
                        table[j] = null;
                        break;
                    }
                }
            }
        }
    }
};