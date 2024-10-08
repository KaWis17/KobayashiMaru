package org.example;

import java.util.Scanner;

// Application entrypoint
public class App {
    public static void main( String[] args ) {
        UciReceiver uci = new UciReceiver();

        Scanner input = new Scanner(System.in);
        while(true) {
            String command = input.nextLine();

            if(command.equals("quit")) {
                input.close();
                System.exit(0);
            }

            uci.dispatch(command);
        }
    }
}
