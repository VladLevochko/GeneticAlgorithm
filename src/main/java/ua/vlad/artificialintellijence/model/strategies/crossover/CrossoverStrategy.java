package ua.vlad.artificialintellijence.model.strategies.crossover;

import ua.vlad.artificialintellijence.model.individual.Individual;

/**
 * Created by vlad on 13.03.17.
 */
@FunctionalInterface
public interface CrossoverStrategy {

    Individual apply(Individual i1, Individual i2) throws CrossoverException;
}
