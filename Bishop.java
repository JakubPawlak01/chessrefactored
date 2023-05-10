public class Bishop extends Piece {
    public Bishop(boolean isWhite, String name) {
        super(isWhite, name);
    }

    public boolean canMove(ChessBoard board, Square start, Square end) {
        if (!isWithinBoardRange(end.getX(), end.getY())) {
            return false;
        }

        if (start.getX() == end.getX() && start.getY() == end.getY()) {
            return false;
        }
        
        if (isSameColorPieceOnSquare(end)) {
            return false;
        }

        int deltaX = Math.abs(end.getX() - start.getX());
        int deltaY = Math.abs(end.getY() - start.getY());

        if (deltaX != deltaY) {
            return false;
        }

        int xDir = (end.getX() > start.getX()) ? 1 : -1;
        int yDir = (end.getY() > start.getY()) ? 1 : -1;

        for (int i = 1; i < deltaX; i++) {
            Square square = board.getSquare(start.getX() + i * xDir, start.getY() + i * yDir);
            if (square.getPiece() != null) {
                return false;
            }
        }

        return true;
    }

    private boolean isWithinBoardRange(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private boolean isSameColorPieceOnSquare(Square square) {
        Piece piece = square.getPiece();
        return piece != null && piece.isWhite() == this.isWhite();
    }
};    
