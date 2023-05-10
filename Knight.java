public class Knight extends Piece {
    public Knight(boolean white, String name) {
        super(white, name);
    }

    public boolean canMove(ChessBoard board, Square start, Square end) {
        if (!isValidPosition(end)) {
            return false;
        }

        if (isSamePosition(start, end)) {
            return false;
        }

        int dx = Math.abs(end.getX() - start.getX());
        int dy = Math.abs(end.getY() - start.getY());

        if ((dx == 1 && dy == 2) || (dx == 2 && dy == 1)) {
            if (isCaptureMove(end) || isEmptySquare(end)) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidPosition(Square square) {
        int x = square.getX();
        int y = square.getY();
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private boolean isSamePosition(Square start, Square end) {
        return start.getX() == end.getX() && start.getY() == end.getY();
    }

    private boolean isCaptureMove(Square square) {
        return square.getPiece() != null && square.getPiece().isWhite() != this.isWhite();
    }

    private boolean isEmptySquare(Square square) {
        return square.getPiece() == null;
    }
}
