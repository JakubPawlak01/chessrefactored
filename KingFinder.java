public class KingFinder {
    public Square findKing(ChessBoard chessboard, boolean isWhiteTurn) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = chessboard.board[i][j];
                Piece piece = square.getPiece();
                if (piece instanceof King && piece.isWhite() == isWhiteTurn) {
                    return square;
                }
            }
        }
        return null;
    }
}
