package anikan.homework.service;

import java.util.List;
import java.util.Objects;

public interface LocalizedIOService {
    void localizedPrintf(String s, Object ...args);

    void localizedPrintfWithLocalizationParams(String s, List<Object> localizationParams, Objects...args);

    void printf(String s, Object... args);

    void localizedPrintln(String s);

    void localizedPrint(String s);

    String readLine();
}
