package ua.vlad.artificialintellijence.model.strategies.selection;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;
import ua.vlad.artificialintellijence.model.population.PopulationParameters;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vlad on 19.03.17.
 */
public class RouletteWheelSelection implements SelectionStrategy {

    private Random random;

    public RouletteWheelSelection() {
        random = new Random();
    }

    @Override
    public List<Pair<Individual>> select(Population population) {
        List<Individual> individuals = population.getIndividuals();
        PopulationParameters parameters = population.getParameters();
        double eliteFitness = 0;
        for (int i = 0; i < parameters.getElitismCount(); i++) {
            eliteFitness += individuals.get(i).getFitness();
        }

        int nonEliteIndividuals =
                parameters.getPopulationSize() - parameters.getElitismCount();
        int pairsNumber = nonEliteIndividuals / 2 + nonEliteIndividuals % 2;

        List<Pair<Individual>> pairs = new LinkedList<>();
        double neededFitness;
        for (int i = 0; i < pairsNumber; i++) {
            neededFitness = positionOnWheel(population.getFitness(), eliteFitness);
            Individual first = selectIndividual(neededFitness, parameters, individuals);
            Individual second;
            do {
                neededFitness = positionOnWheel(population.getFitness(), eliteFitness);
                second = selectIndividual(neededFitness, parameters, individuals);
            } while (first.equals(second));

            pairs.add(new Pair<>(first, second));
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
