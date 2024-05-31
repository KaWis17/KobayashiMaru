package org.example;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        UniversalChessInterface uci = new UniversalChessInterface();

        Scanner input = new Scanner(System.in);
        while(true) {
            String command = input.nextLine();

            if(command.equals("exit"))
                return;


            uci.dispatch(command);
        }
    }
}
