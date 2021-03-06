package anikan.homework.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LocalizedIOServiceImpl implements LocalizedIOService{
    private final LocaleMessagesSource localeMessagesSource;
    private final IOService ioService;

    public LocalizedIOServiceImpl(LocaleMessagesSource localeMessagesSource, IOService ioService) {
        this.localeMessagesSource = localeMessagesSource;
        this.ioService = ioService;
    }

    @Override
    public void localizedPrintfWithLocalizationParams(String messageId, Object ...localizationParams){
        ioService.printf(localeMessagesSource.getMessage(messageId, localizationParams));
    }
    @Override
    public void printf(String s, Object... args){
        ioService.printf(s, args);
    }

    @Override
    public void localizedPrintln(String messageId){
        ioService.println(localeMessagesSource.getMessage(messageId));
    }

    @Override
    public void localizedPrint(String messageId){
        ioService.println(localeMessagesSource.getMessage(messageId));
    }
    @Override
    public String readLine(){
        return ioService.readLine();
    }

}
