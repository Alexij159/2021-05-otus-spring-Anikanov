package anikan.homework.service;

import java.util.List;
import java.util.Objects;

public interface LocalizedIOService {

    void localizedPrintfWithLocalizationParams(String s, Object ...localizationParams);

    void printf(String s, Object... args);

    void localizedPrintln(String s);

    void localizedPrint(String s);

    String readLine();
}
