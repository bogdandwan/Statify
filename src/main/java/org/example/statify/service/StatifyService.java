package org.example.statify.service;

import org.example.statify.model.SecondHalfGGModel;

import java.util.List;

public interface StatifyService {

    List<SecondHalfGGModel> getTopSecondHalfGG(Integer lastMatches, Integer limit);

}
