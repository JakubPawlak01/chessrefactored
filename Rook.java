public class Rook extends Piece {
    private boolean firstMove;

    public Rook(boolean white, String name) {
        super(white, name);
        firstMove = true;
    }

    public void setFirstMove() {
        this.firstMove = false;
    }

    public boolean canMove(ChessBoard board, Square start, Square end) {
        if (!isValidPosition(end)) {
            return false;
        }

        if (isSamePosition(start, end)) {
            return false;
        }

        if (!isMoveStraight(start, end)) {
            return false;
        }

        if (!isPathClear(board, start, end)) {
            return false;
        }

        if (isCaptureMove(end) || isEmptySquare(end)) {
            return true;
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

    private boolean isPathClear(ChessBoard board, Square start, Square end) {
        int xDiff = Integer.compare(end.getX(), start.getX());
        int yDiff = Integer.compare(end.getY(), start.getY());

        int x = start.getX() + xDiff;
        int y = start.getY() + yDiff;

        while (x != end.getX() || y != end.getY()) {
            if (board.getSquare(x, y).getPiece() != null) {
                return false;
            }
            x += xDiff;
            y += yDiff;
        }

        return true;
    }

    private boolean isCaptureMove(Square square) {
        return square.getPiece() != null && square.getPiece().isWhite() != this.isWhite();
    }

    private boolean isEmptySquare(Square square) {
        return square.getPiece() == null;
    }
}
