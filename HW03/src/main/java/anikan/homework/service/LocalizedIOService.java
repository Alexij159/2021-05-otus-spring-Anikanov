package anikan.homework.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LocalizedIOService {
    private final LocaleMessagesSource localeMessagesSource;
    private final IOService ioService;

    public LocalizedIOService(LocaleMessagesSource localeMessagesSource, IOService ioService) {
        this.localeMessagesSource = localeMessagesSource;
        this.ioService = ioService;
    }

    void printf(String s, Object ...args){
        ioService.printf(localeMessagesSource.getMessage(s),args);
    }

    void printfWithParameterizedLocalization(String s, List<Object> localizationParams, Objects ...args){
        ioService.printf(localeMessagesSource.getMessage(s, localizationParams.toArray()), args);
    }
    
    void printfWithoutLocalization(String s, Object... args){
        ioService.printf(s, args);
    }
    
    void println(String s){
        ioService.println(localeMessagesSource.getMessage(s));
    }

    void print(String s){
        ioService.println(localeMessagesSource.getMessage(s));
    }

    String readLine(){
        return ioService.readLine();
    }

}
