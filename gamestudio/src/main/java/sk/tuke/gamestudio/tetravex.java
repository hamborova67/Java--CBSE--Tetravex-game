package sk.tuke.gamestudio;


import sk.tuke.gamestudio.game.tetravex.consoleUI.ConsoleUI;

public class tetravex {
    public static void main(String[] args) {
        int matrixsize = 2;

        ConsoleUI consoleUI = new ConsoleUI(matrixsize);
        consoleUI.start();
    }


}
