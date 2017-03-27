package ua.vlad.artificialintellijence.model.strategies.selection;

import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;

/**
 * Created by vlad on 19.03.17.
 */
@FunctionalInterface
public interface SelectionStrategy {

    Individual select(Population population);
}
