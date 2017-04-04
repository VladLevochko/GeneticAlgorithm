package ua.vlad.artificialintellijence.model.strategies.crossover;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.individual.Individual;

import java.util.List;

/**
 * Created by vlad on 13.03.17.
 */
@FunctionalInterface
public interface CrossoverStrategy {

    List<Individual> apply(List<Pair<Individual>> pairs) throws CrossoverException;
}
