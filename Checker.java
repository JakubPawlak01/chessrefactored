public class Checker {
    private KingFinder kingFinder;
    private CheckValidator checkValidator;
    private CheckmateValidator checkmateValidator;
    private MoveValidator moveValidator;
    private PatValidator patValidator;

    public Checker(){
        kingFinder = new KingFinder();
        checkValidator = new CheckValidator();
        checkmateValidator = new CheckmateValidator();
        moveValidator = new MoveValidator();
        patValidator = new PatValidator();
    }

    public Square findKing(ChessBoard board, boolean isWhiteTurn){
        return kingFinder.findKing(board, isWhiteTurn);
    }

    public boolean isCheck(ChessBoard board, boolean isWhiteTurn){
        return checkValidator.isCheck(board, isWhiteTurn);
    }

    public boolean isCheckMate(ChessBoard board, boolean isWhiteTurn){
        return checkmateValidator.isCheckMate(board, isWhiteTurn);
    }

    public boolean isPat(ChessBoard board, boolean isWhiteTurn){
        return patValidator.isPat(board, isWhiteTurn);
    }

    public boolean isCheckAfterMove(ChessBoard board, Square startSquare, Square endSquare, boolean isWhiteTurn){
        return moveValidator.isCheckAfterMove(board, startSquare, endSquare, isWhiteTurn);
    }
}
