package ua.vlad.artificialintellijence.model.population;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.individual.IndividualsFabric;
import ua.vlad.artificialintellijence.model.strategies.crossover.CrossoverException;
import ua.vlad.artificialintellijence.model.strategies.crossover.CrossoverStrategy;
import ua.vlad.artificialintellijence.model.strategies.selection.SelectionStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vlad on 11.03.17.
 */
public class Population {

    private PopulationParameters parameters;
    private List<Individual> population;
    private CrossoverStrategy crossoverStrategy;
    private SelectionStrategy selectionStrategy;
    private int fitness;


    /**
     * Builder for population
     */
    public static class PopulationBuilder {

        private PopulationParameters parameters;
        private Individual individual;
        private CrossoverStrategy crossover;
        private SelectionStrategy selection;

        public PopulationBuilder() {

        }

        public PopulationBuilder populationParameters(PopulationParameters parameters) {
            this.parameters = parameters;
            return this;
        }

        public PopulationBuilder initialIndividual(Individual individual) {
            this.individual = individual;
            return this;
        }

        public PopulationBuilder crossoverStrategy(CrossoverStrategy crossover) {
            this.crossover = crossover;
            return this;
        }

        public PopulationBuilder selectionStrategy(SelectionStrategy selection) {
            this.selection = selection;
            return this;
        }

        public Population buildPopulation() {
            return new Population(this);
        }
    }


    private Population(PopulationBuilder builder) {
        this.parameters = builder.parameters;
        generatePopulation(builder.individual);
        this.crossoverStrategy = builder.crossover;
        this.selectionStrategy = builder.selection;
        calculateFitness();
    }

    private void generatePopulation(Individual initialIndividual) {
        population = IndividualsFabric.generateIndividuals(
                initialIndividual, parameters.getPopulationSize());
        sortPopulation();
    }

    public List<Individual> getIndividuals() {
        List<Individual> individuals = new ArrayList<>();
        for (Individual individual : population) {
            individuals.add(individual.copy());
        }

        return individuals;
    }

    public PopulationParameters getParameters() {
        return this.parameters;
    }

    private void calculateFitness() {
        fitness = 0;
        for (Individual individual : population) {
            fitness += individual.getFitness();
        }
    }

    public int getFitness() {
        return this.fitness;
    }

    public void crossoverPopulation() throws CrossoverException {
        List<Pair<Individual>> pairs = selectionStrategy.select(this);
        List<Individual> newGeneration = new ArrayList<>(
                population.subList(0, parameters.getElitismCount())
        );
        newGeneration.addAll(crossoverStrategy.apply(pairs));

        population = newGeneration;
        sortPopulation();
        calculateFitness();
    }

    public void mutatePopulation() {
        for (Individual individual : population) {
            if (Math.random() < parameters.getMutationRate()) {
                individual.mutate();
                System.out.println("mutate");
            }
        }
        sortPopulation();
    }

    public Individual getFittestIndividual() {
        return population.get(0);
    }

    private void sortPopulation() {
        this.population.sort(
                Comparator.comparingDouble(Individual::getFitness)//.reversed()
        );
    }
}
