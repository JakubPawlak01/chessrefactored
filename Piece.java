public abstract class Piece {
    private boolean isFirstMove = true;
    private boolean isWhite = false;
    private String name;

    public Piece(boolean isWhite, String name) {
        this.isWhite = isWhite;
        this.name = name;
    }
    
    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove() {
        this.isFirstMove = false;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public String getPName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean canMove(ChessBoard board, Square start, Square end);
}
