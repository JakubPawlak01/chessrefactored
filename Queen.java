public class Queen extends Piece {
    public Queen(boolean white, String name) {
        super(white, name);
    }

    public boolean canMove(ChessBoard board, Square start, Square end) {
        if (!isValidPosition(end)) {
            return false;
        }

        if (isSamePosition(start, end)) {
            return false;
        }

        if (!isMoveStraight(start, end) && !isMoveDiagonal(start, end)) {
            return false;
        }

        if (isCaptureMove(end) || isEmptySquare(end)) {
            return isPathClear(board, start, end);
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

    private boolean isMoveStraight(Square start, Square end) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    private boolean isMoveDiagonal(Square start, Square end) {
        int dx = Math.abs(end.getX() - start.getX());
        int dy = Math.abs(end.getY() - start.getY());
        return dx == dy;
    }

    private boolean isCaptureMove(Square square) {
        return square.getPiece() != null && square.getPiece().isWhite() != this.isWhite();
    }

    private boolean isEmptySquare(Square square) {
        return square.getPiece() == null;
    }

    private boolean isPathClear(ChessBoard board, Square start, Square end) {
        int dx = Integer.compare(end.getX(), start.getX());
        int dy = Integer.compare(end.getY(), start.getY());
        int x = start.getX() + dx;
        int y = start.getY() + dy;
        while (x != end.getX() || y != end.getY()) {
            if (board.getSquare(x, y).getPiece() != null) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }
}
