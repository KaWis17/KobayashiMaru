package org.example;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        UniversalChessEngine uci = new UniversalChessEngine();

        Scanner input = new Scanner(System.in);
        while(true) {
            String command = input.nextLine();

            if(command.equals("quit"))
                return;

            uci.dispatch(command);
        }
    }
}
