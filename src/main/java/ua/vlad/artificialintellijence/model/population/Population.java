package ua.vlad.artificialintellijence.model.population;

import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.individual.IndividualsFabric;
import ua.vlad.artificialintellijence.model.strategies.crossover.CrossoverException;
import ua.vlad.artificialintellijence.model.strategies.crossover.CrossoverStrategy;
import ua.vlad.artificialintellijence.model.strategies.selection.SelectionStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by vlad on 11.03.17.
 */
public class Population {

    private PopulationParameters parameters;
    private PriorityQueue<Individual> population;
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
        List<Individual> individuals = IndividualsFabric.generateIndividuals(
                initialIndividual, parameters.getPopulationSize());

        population = new PriorityQueue<>(
                Comparator.comparingDouble(Individual::getFitness).reversed());
        population.addAll(individuals);
    }

    public PriorityQueue<Individual> getIndividuals() {
        PriorityQueue<Individual> individuals = new PriorityQueue<>(
                Comparator.comparingDouble(Individual::getFitness));
        for (Individual individual : population) {
            individuals.add(individual.copy());
        }

        return individuals;
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

    public int size() {
        return population.size();
    }

    public void crossoverPopulation() throws CrossoverException {
        int individualIndex = 0;
        PriorityQueue<Individual> nextGeneration = new PriorityQueue<>(
                Comparator.comparingDouble(Individual::getFitness));
        for (Individual parent1 : population) {
            if (individualIndex > parameters.getElitismCount()) {
                Individual parent2;
                do {
                    parent2 = selectionStrategy.select(this);
                } while (parent1.equals(parent2));

                Individual offspring = crossoverStrategy.apply(parent1, parent2);
                nextGeneration.add(offspring);
            } else {
                nextGeneration.add(parent1);
            }

            individualIndex++;
        }

        population = nextGeneration;
        calculateFitness();
    }

    public void mutatePopulation() {
        int individualIndex = 0;
        for (Individual individual : population) {
            if (individualIndex++ > parameters.getElitismCount()
                    && Math.random() < parameters.getMutationRate()) {
                individual.mutate();
            }
        }
    }

    public Individual getFittestIndividual() {
        return population.peek();
    }
}
