package anikan.homework.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class StreamsIOService implements IOService {
    private final Scanner scanner;
    private final PrintStream out;

    public StreamsIOService(InputStream in,
                            PrintStream out) {
        this.out = out;
        scanner = new Scanner(in);
    }

    public void printf(String s, Object ...args){
        out.printf(s,args);
    }

    public void println(String s){
        out.println(s);
    }

    public void print(String s) {
        out.println(s);
    }

    public String readLine(){
        return scanner.nextLine();
    }
}
