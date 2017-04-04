package ua.vlad.artificialintellijence.model.strategies.selection;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;

import java.util.List;

/**
 * Created by vlad on 19.03.17.
 */
@FunctionalInterface
public interface SelectionStrategy {

    List<Pair<Individual>> select(Population population);
}
