package anikan.homework.service;

import java.util.List;

public interface LocalizedIOService {

    public void printf(String s, Object ...args);

    public void printfWithParameterizedLocalization(String s, List<Object> localizationParams);

    public void printfWithoutLocalization(String s, Object... args);

    public void println(String s);

    public void print(String s);

    public String readLine();
}
