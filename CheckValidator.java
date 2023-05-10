public class CheckValidator {
    private KingFinder FindKing;
    public CheckValidator(){
        FindKing = new KingFinder();
    }
    public boolean isCheck(ChessBoard chessboard, boolean isWhiteTurn) {
        Square kingSquare = FindKing.findKing(chessboard, isWhiteTurn);
        if (kingSquare == null) {
            System.out.println("Nie znaleziono Króla");
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square attackingSquare = chessboard.board[i][j];
                Piece attackingPiece = attackingSquare.getPiece();
                if (attackingPiece != null && attackingPiece.isWhite() != isWhiteTurn && attackingPiece.canMove(chessboard, attackingSquare, kingSquare)) {
                    return true;
                }
            }
        }
        return false;
    }
}
