public class King extends Piece{
    Checker checker;
    public King(boolean white, String name){
        super(white, name);
        checker = new Checker();
    }

    public boolean canMove(ChessBoard board, Square start, Square end) {
        if (end.getX() < 0 || end.getX() > 7 || end.getY() < 0 || end.getY() > 7) {
            return false;
        }

        if (start.getX() == end.getX() && start.getY() == end.getY()) {
            return false;
        }

        if(!castling(board, start, end)){
            return false;
        }

        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        int xDiff = Math.abs(end.getX() - start.getX());
        int yDiff = Math.abs(end.getY() - start.getY());
        if (xDiff > 1 || yDiff > 1) {
            return false;
        }

        

        return true;
    }

    public boolean castling(ChessBoard board, Square start, Square end) {
        Piece piece = start.getPiece();
        int startY = start.getY();
        int endY = end.getY();
    
        if (piece instanceof King && piece.isWhite() && (endY == 2 || endY == 6)) {
            if (piece.isFirstMove() && board.board[7][0].getPiece().isFirstMove()) {
                Square mid = board.board[7][endY == 2 ? 3 : 5];
                Square endNext = board.board[7][endY == 2 ? 1 : 6];
                if (mid.getPiece() == null && !checker.isCheckAfterMove(board, start, mid, piece.isWhite())
                        && end.getPiece() == null && !checker.isCheckAfterMove(board, start, end, piece.isWhite())
                        && endNext.getPiece() == null) {
                    board.board[7][endY == 2 ? 2 : 6].setPiece(board.board[7][4].getPiece());
                    board.board[7][endY == 2 ? 3 : 5].setPiece(board.board[7][endY == 2 ? 0 : 7].getPiece());
                    board.board[7][endY == 2 ? 0 : 7].setPiece(null);
                    board.board[7][4].setPiece(null);
                    board.board[7][endY == 2 ? 2 : 6].getPiece().setFirstMove();
                    board.board[7][endY == 2 ? 3 : 5].getPiece().setFirstMove();
                    return true;
                }
            }
        } else if (piece instanceof King && !piece.isWhite() && (endY == 2 || endY == 6)) {
            if (piece.isFirstMove() && board.board[0][0].getPiece().isFirstMove()) {
                Square mid = board.board[0][endY == 2 ? 3 : 5];
                Square endNext = board.board[0][endY == 2 ? 1 : 6];
                if (mid.getPiece() == null && !checker.isCheckAfterMove(board, start, mid, piece.isWhite())
                        && end.getPiece() == null && !checker.isCheckAfterMove(board, start, end, piece.isWhite())
                        && endNext.getPiece() == null) {
                    board.board[0][endY == 2 ? 2 : 6].setPiece(board.board[0][4].getPiece());
                    board.board[0][endY == 2 ? 3 : 5].setPiece(board.board[0][endY == 2 ? 0 : 7].getPiece());
                    board.board[0][endY == 2 ? 0 : 7].setPiece(null);
                    board.board[0][4].setPiece(null);
                    board.board[0][endY == 2 ? 2 : 6].getPiece().setFirstMove();
                    board.board[0][endY == 2 ? 3 : 5].getPiece().setFirstMove();
                    return true;
                }
            }
        }
    
        return false;
    }
    
    
    
    

}
