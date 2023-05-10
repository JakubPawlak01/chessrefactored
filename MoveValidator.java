public class MoveValidator {
private CheckValidator CheckValid;
public MoveValidator(){
    CheckValid = new CheckValidator();
}

    public boolean isCheckAfterMove(ChessBoard chessboard, Square startSquare, Square endSquare, boolean isWhiteTurn) {
        ChessBoard chessboardCopy = new ChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboardCopy.board[i][j] = chessboard.board[i][j];
            }
        }
        chessboardCopy.whiteTurn = chessboard.whiteTurn;
        Piece startPiece = startSquare.getPiece();
        Piece endPiece = endSquare.getPiece();
        startSquare.setPiece(null);
        endSquare.setPiece(startPiece);
        if (CheckValid.isCheck(chessboardCopy, isWhiteTurn)) {
            startSquare.setPiece(startPiece);
            endSquare.setPiece(endPiece);
            return true;
        }
        startSquare.setPiece(startPiece);
        endSquare.setPiece(endPiece);
        return false;
    }
}
