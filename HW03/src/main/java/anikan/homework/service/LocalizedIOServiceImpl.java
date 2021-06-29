package anikan.homework.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "localizedIOService")
public class LocalizedIOServiceImpl implements LocalizedIOService {
    private final LocaleMessagesSource localeMessagesSource;
    private final IOService ioService;

    public LocalizedIOServiceImpl(LocaleMessagesSource localeMessagesSource, IOService ioService) {
        this.localeMessagesSource = localeMessagesSource;
        this.ioService = ioService;
    }

    public void printf(String s, Object ...args){
        ioService.printf(localeMessagesSource.getMessage(s),args);
    }

    public void printfWithParameterizedLocalization(String s, Object ...localizationParams){
        ioService.printf(localeMessagesSource.getMessage(s, localizationParams));
    }

    public void printfWithoutLocalization(String s, Object... args){
        ioService.printf(s, args);
    }

    public void println(String s){
        ioService.println(localeMessagesSource.getMessage(s));
    }

    public void print(String s){
        ioService.println(localeMessagesSource.getMessage(s));
    }

    public String readLine(){
        return ioService.readLine();
    }

}
