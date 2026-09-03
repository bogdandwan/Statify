package org.example.statify.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.statify.model.SecondHalfGGModel;
import org.example.statify.repository.StatifyRepository;
import org.example.statify.service.StatifyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatifyServiceImpl implements StatifyService {


    private final StatifyRepository statifyRepository;


    @Override
    public List<SecondHalfGGModel> getTopSecondHalfGG(Integer lastMatches, Integer limit) {

        return statifyRepository.findTopSecondHalfGG(lastMatches, limit).stream()
                .map(
                        result ->
                                new SecondHalfGGModel(
                                        result.getTeamId(),
                                        result.getTeamApiId(),
                                        result.getTeamName(),
                                        result.getGgCount(),
                                        result.getMatchesCount(),
                                        result.getPercentage()))
                .toList();
    }
}

