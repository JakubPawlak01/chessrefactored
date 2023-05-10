public class PatValidator {
    private CheckValidator CheckValid;
    private MoveValidator ValidMove;

    public PatValidator(){
        CheckValid = new CheckValidator();
        ValidMove = new MoveValidator();
    }

    public boolean isPat(ChessBoard chessboard, boolean isWhiteTurn) {
        if (CheckValid.isCheck(chessboard, isWhiteTurn)) {
            return false;
        }

        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                Square startSquare = chessboard.board[startRow][startCol];
                Piece startPiece = startSquare.getPiece();
                if (startPiece != null && startPiece.isWhite() == isWhiteTurn) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            Square endSquare = chessboard.board[endRow][endCol];
                            if (startSquare != null && startPiece.canMove(chessboard, startSquare, endSquare)) {
                                if (!ValidMove.isCheckAfterMove(chessboard, startSquare, endSquare, isWhiteTurn)) {
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
}
