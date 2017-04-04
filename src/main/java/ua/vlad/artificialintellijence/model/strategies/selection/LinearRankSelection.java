package ua.vlad.artificialintellijence.model.strategies.selection;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;
import ua.vlad.artificialintellijence.model.population.PopulationParameters;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 27.03.17.
 */
public class LinearRankSelection implements SelectionStrategy {
    @Override
    public List<Pair<Individual>> select(Population population) {
        PopulationParameters parameters = population.getParameters();
        List<Individual> individuals = population.getIndividuals();
        List<Pair<Individual>> pairs = new LinkedList<>();

        for (int i = 0; i < parameters.getPopulationSize(); i += 2) {
            pairs.add(new Pair<>(individuals.get(i), individuals.get(i + 1)));
        }

        return pairs;
    }
}
