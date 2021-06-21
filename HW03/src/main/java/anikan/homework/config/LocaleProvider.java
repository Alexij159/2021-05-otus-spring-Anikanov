package anikan.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static java.util.Objects.isNull;

@Component
public class LocaleProvider {
    private final Locale locale;

    public LocaleProvider(@Value("${locale:}")Locale locale) {
        if (isNull(locale))
            this.locale = Locale.getDefault();
        else
            this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
