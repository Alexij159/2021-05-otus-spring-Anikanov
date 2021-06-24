package anikan.homework.service;

import anikan.homework.config.LocaleProvider;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LocaleMessagesSource {
    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public LocaleMessagesSource(LocaleProvider localeProvider,
                                MessageSource messageSource) {

        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    public String getMessage(String messageId, Object... args){
        return messageSource.getMessage(messageId, args, localeProvider.getLocale());
    }

    public String getMessage(String messageId) {
        return getMessage(messageId, null);
    }


}
