package homework.service;

public interface IOService {
    void printf(String s, Object... args);

    void println(String s);

    void print(String s);

    String readLine();
}
