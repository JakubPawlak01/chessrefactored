import java.util.Scanner;

public class Pawn extends Piece {
    Scanner scanner;

    public Pawn(boolean white, String name) {
        super(white, name);
        scanner = new Scanner(System.in);
    }

    public boolean canMove(ChessBoard board, Square start, Square end) {
        if (!isValidRange(end.getX(), end.getY())) {
            return false;
        }

        if (start.equals(end)) {
            return false;
        }

        int diffX = Math.abs(start.getX() - end.getX());
        int diffY = Math.abs(start.getY() - end.getY());

        if (isValidEnPassantMove(start, end, diffX, diffY, board)) {
            return true;
        }

        if (!isValidNormalMove(start, end, diffX, diffY, board)) {
            return false;
        }

        return true;
    }

    private boolean isValidRange(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean isValidEnPassantMove(Square start, Square end, int diffX, int diffY, ChessBoard board) {
        if (diffX == 1 && diffY == 1 && start.getPiece() instanceof Pawn) {
            if ((start.getPiece().isWhite() && start.getX() == 3) || (!start.getPiece().isWhite() && start.getX() == 4)) {
                if (end.getPiece() == null && isEnPassantPossible(start, end, board)) {
                    executeEnPassantCapture(start, board);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isEnPassantPossible(Square start, Square end, ChessBoard board) {
        Square lastMoveStart = board.lastMove[0];
        Square lastMoveEnd = board.lastMove[1];

        int diffX = Math.abs(lastMoveStart.getX() - lastMoveEnd.getX());
        int diffY = Math.abs(lastMoveStart.getY() - lastMoveEnd.getY());

        return diffY == 0 && diffX == 2 && isEnPassantCapturePiece(start, end, lastMoveEnd, board);
    }

    private boolean isEnPassantCapturePiece(Square start, Square end, Square lastMoveEnd, ChessBoard board) {
        int enPassantCaptureX = lastMoveEnd.getX();
        int enPassantCaptureY = lastMoveEnd.getY();
        Piece enPassantCapturePiece = board.board[enPassantCaptureX][enPassantCaptureY].getPiece();

        if (start.getPiece().isWhite() == true && enPassantCapturePiece instanceof Pawn && !enPassantCapturePiece.isWhite()) {
            board.board[enPassantCaptureX][enPassantCaptureY].setPiece(null);
            return true;
        } else if (start.getPiece().isWhite() != true && enPassantCapturePiece instanceof Pawn && enPassantCapturePiece.isWhite()) {
            board.board[enPassantCaptureX][enPassantCaptureY].setPiece(null);
            return true;
        }

        return false;
    }

    private void executeEnPassantCapture(Square start, ChessBoard board) {
        int enPassantCaptureX = board.lastMove[1].getX();
        int enPassantCaptureY = board.lastMove[1].getY();
        board.board[enPassantCaptureX][enPassantCaptureY].setPiece(null);
    }

    private boolean isValidNormalMove(Square start, Square end, int diffX, int diffY, ChessBoard board) {
        if (diffX > 2 || diffY > 1) {
            return false;
        }

        if ((start.getPiece().isWhite() && end.getX() > start.getX()) || (!start.getPiece().isWhite() && end.getX() < start.getX())) {
            return false;
        }

        if ((diffX == 1 && diffY == 1 && end.getPiece() == null) || (diffY >= 1 && diffX == 0)) {
            return false;
        }

        if (isValidTwoSquareMove(start, end, diffX)) {
            int xDir = (end.getX() > start.getX()) ? 1 : -1;
            Square diffSquare = board.board[start.getX() + xDir][start.getY()];
            if (diffSquare.getPiece() == null && end.getPiece() == null) {
                return true;
            }
        }

        if (diffX == 1 && end.getPiece() == null) {
            return true;
        }

        if (diffX == 1 && diffY == 1 && end.getPiece() != null && start.getPiece().isWhite() != end.getPiece().isWhite()) {
            return true;
        }

        return false;
    }

    private boolean isValidTwoSquareMove(Square start, Square end, int diffX) {
        return diffX == 2 && (start.getX() == 6 || start.getX() == 1);
    }
}

