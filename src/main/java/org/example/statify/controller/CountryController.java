package org.example.statify.controller;

import lombok.RequiredArgsConstructor;
import org.example.statify.service.CountryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService  countryService;

    @PostMapping("/country")
    public void importCountries() {

        System.out.println("COUNTRY CONTROLLER CALLED");

        countryService.importCountries();
    }

}
