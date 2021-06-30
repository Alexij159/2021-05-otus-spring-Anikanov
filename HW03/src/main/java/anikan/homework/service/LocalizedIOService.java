package anikan.homework.service;

import java.util.List;

public interface LocalizedIOService {

    void printf(String s, Object ...args);
    void printfWithParameterizedLocalization(String s, Object ...localizationParams);
    void printfWithoutLocalization(String s, Object... args);
    void println(String s);
    void print(String s);
    String readLine();
}
