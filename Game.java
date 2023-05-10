import java.util.Scanner;

public class Game {
    private ChessBoard board;
    private Checker check;
    private Scanner scanner;
    private boolean whiteTurn;

    public Game() {
        this.board = new ChessBoard();
        this.check = new Checker();
        this.scanner = new Scanner(System.in);
        this.whiteTurn = true;
    }

    public void start() {
        while (true) {
            board.printboard();
            String turnColor = whiteTurn ? "białych" : "czarnych";

            if (check.isCheck(board, whiteTurn)) {
                System.out.println("Szach!");
            }
            if (check.isCheckMate(board, whiteTurn)) {
                System.out.println("Mat!");
                whiteTurn = !whiteTurn;
                turnColor = whiteTurn ? "białych" : "czarnych";
                System.out.println("Wygrana " + turnColor + "!");
                break;
            }
            if (check.isPat(board, whiteTurn)) {
                System.out.println("Pat!");
                break;
            }

            System.out.println("Teraz ruch " + turnColor);
            System.out.println("Podaj pole początkowe:");
            String start = scanner.nextLine();
            System.out.println("Podaj pole końcowe:");
            String end = scanner.nextLine();

            if (start.isEmpty() || end.isEmpty()) {
                System.out.println("Musisz podać ruch!");
                continue;
            }

            Square startSquare = board.getSquareFromNotation(start);

            if (startSquare == null) {
                System.out.println("Nieprawidłowe pole. Spróbuj ponownie.");
                continue;
            }
            
            Piece startPiece = startSquare.getPiece();

            if (startPiece == null || startPiece.isWhite() != whiteTurn) {
                System.out.println("Nieprawidłowy pionek. Spróbuj ponownie.");
                continue;
            }

            if (board.setMove(start, end)) {
                whiteTurn = !whiteTurn;
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
