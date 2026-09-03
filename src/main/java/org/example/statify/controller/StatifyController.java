package org.example.statify.controller;

import lombok.RequiredArgsConstructor;
import org.example.statify.model.SecondHalfGGModel;
import org.example.statify.service.StatifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatifyController {

    private final StatifyService statifyService;

    @GetMapping("/second-half-gg")
    public List<SecondHalfGGModel> getTopSecondHalfGG(
            @RequestParam(defaultValue = "20") Integer lastMatches,
            @RequestParam(defaultValue = "10") Integer limit) {

        return statifyService.getTopSecondHalfGG(lastMatches, limit);
    }

}
