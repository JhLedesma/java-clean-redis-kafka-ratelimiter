package com.tengo.challenge.percentage.domain;

import java.util.Optional;

public interface PercentageCache {

    Integer save(Integer percentage);

    Optional<Integer> getPercentage();

    Optional<Integer> getLastUsedPercentage();
}
