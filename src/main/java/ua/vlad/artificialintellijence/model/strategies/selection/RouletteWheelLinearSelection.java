package ua.vlad.artificialintellijence.model.strategies.selection;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;
import ua.vlad.artificialintellijence.model.population.PopulationParameters;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vlad on 27.03.17.
 */
public class RouletteWheelLinearSelection implements SelectionStrategy {

    private Random random;

    public RouletteWheelLinearSelection() {
        this.random = new Random();
    }

    @Override
    public List<Pair<Individual>> select(Population population) {
        PopulationParameters parameters = population.getParameters();
        List<Individual> individuals = population.getIndividuals();

        List<Pair<Individual>> pairs = new LinkedList<>();
        int individualIndex = 0;
        for (Individual individual : individuals) {
//            if (individualIndex > parameters.getPopulationSize()) {
//                break;
//            }
            if (individualIndex < parameters.getElitismCount()) {
                individualIndex++;
                continue;
            }

            double secondPosition = positionOnWheel(
                    population.getFitness(),
                    parameters.getElitismCount());
            Individual second = selectIndividual(secondPosition,
                    parameters, individuals);

            pairs.add(new Pair<>(individual, second));
            individualIndex++;
        }

        return pairs;
    }

    private double positionOnWheel(double populationFitness, double eliteFitness) {
        return random.nextDouble() * populationFitness - eliteFitness;
    }

    private Individual selectIndividual(double neededFitness,
                                        PopulationParameters parameters,
                                        List<Individual> individuals) {
        int fitnessSum = 0;
        int p = parameters.getElitismCount() + 1;
        while (fitnessSum < neededFitness && p < parameters.getPopulationSize()) {
            fitnessSum += individuals.get(p).getFitness();
            p++;
        }

        return individuals.get(p - 1);
    }
}
