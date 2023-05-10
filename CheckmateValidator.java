public class CheckmateValidator {
    private KingFinder FindKing;
    private CheckValidator CheckValid;
    private MoveValidator ValidMove;
    
    public CheckmateValidator(){
        FindKing = new KingFinder();
        CheckValid = new CheckValidator();
        ValidMove = new MoveValidator();
    }

    public boolean isCheckMate(ChessBoard chessBoard, boolean isWhiteTurn) {
        Square kingSquare = FindKing.findKing(chessBoard, isWhiteTurn);

        if (kingSquare == null || !CheckValid.isCheck(chessBoard, isWhiteTurn)) {
            return false;
        }

        int kingX = kingSquare.getX();
        int kingY = kingSquare.getY();
        for (int row = kingX - 1; row <= kingX + 1; row++) {
            for (int col = kingY - 1; col <= kingY + 1; col++) {
                if (isValidCoordinate(row, col)) {
                    Square endSquare = chessBoard.board[row][col];
                    Piece endPiece = endSquare.getPiece();
                    if (endPiece == null || endPiece.isWhite() != isWhiteTurn) {
                        if (!ValidMove.isCheckAfterMove(chessBoard, kingSquare, endSquare, isWhiteTurn)) {
                            return false;
                        }
                    }
                }
            }
        }

        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                Square startSquare = chessBoard.board[startRow][startCol];
                Piece startPiece = startSquare.getPiece();
                if (startPiece != null && startPiece.isWhite() == isWhiteTurn) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            Square endSquare = chessBoard.board[endRow][endCol];
                            if (startSquare != null && startPiece.canMove(chessBoard, startSquare, endSquare)) {
                                if (!ValidMove.isCheckAfterMove(chessBoard, startSquare, endSquare, isWhiteTurn)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
