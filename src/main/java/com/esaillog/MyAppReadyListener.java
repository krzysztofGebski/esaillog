package com.esaillog;

import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyAppReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final SailorRepository sailorRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        sailorRepository.save(new Sailor(null, "Krzysztof", "Gębski", "kg@mail.com"));
        sailorRepository.save(new Sailor(null, "Agata", "Betleja", "ab@mail.com"));
        sailorRepository.save(new Sailor(null, "Jakub", "Wiewióra", "jw@mail.com"));
        sailorRepository.save(new Sailor(null, "Adam", "Nowak", "jw@mail.com"));
    }
}
