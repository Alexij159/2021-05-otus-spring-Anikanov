package homework.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static java.util.Objects.nonNull;


public class StreamsIOService implements IOService {
    private final Scanner scanner;
    private final PrintStream out;

    public StreamsIOService(InputStream in,
                            PrintStream out) {
        if (nonNull(out))
            this.out = out;
        else
            this.out = System.out;
        if (nonNull(in))
            scanner = new Scanner(in);
        else
            scanner = new Scanner(System.in);
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
