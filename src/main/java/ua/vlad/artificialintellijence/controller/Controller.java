package ua.vlad.artificialintellijence.controller;

import ua.vlad.artificialintellijence.Constants;
import ua.vlad.artificialintellijence.model.GeneticAlgorithm;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;
import ua.vlad.artificialintellijence.model.population.PopulationParameters;
import ua.vlad.artificialintellijence.model.strategies.crossover.PartiallyMappedCrossover;
import ua.vlad.artificialintellijence.model.strategies.crossover.TwoPointOrderCrossover;
import ua.vlad.artificialintellijence.model.strategies.fitness.TraversalSalesmanFitness;
import ua.vlad.artificialintellijence.model.strategies.mutation.TraversalSalesmanMutation;
import ua.vlad.artificialintellijence.model.strategies.selection.LinearRankSelection;
import ua.vlad.artificialintellijence.model.strategies.selection.RouletteWheelLinearSelection;
import ua.vlad.artificialintellijence.model.strategies.selection.RouletteWheelSelection;
import ua.vlad.artificialintellijence.view.View;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by vlad on 11.03.17.
 */
public class Controller {

    private View view;
    private GeneticAlgorithm geneticAlgorithm;
    private Population population;

    public Controller(View view) {
        this.view = view;
        this.view.setController(this);
    }

    public void processData(List<Point> citiesPositions,
                            int generationsNumber,
                            int populationSize) {
        initializeGeneticAlgorithm(citiesPositions, generationsNumber, populationSize);
        geneticAlgorithm.simulate();
        view.displayResult(retrieveResult());
    }

    private void initializeGeneticAlgorithm(List<Point> citiesPositions,
                                           int generationsNumber,
                                            int populationSize) {
        List<Gen> initialChromosome = citiesPositions.stream()
                .map(Gen::new)
                .collect(Collectors.toList());

        population = new Population.PopulationBuilder()
                .populationParameters(new PopulationParameters.Builder()
                        .populationSize(populationSize)
                        .crossoverRate(Constants.CROSSOVER_RATE)
                        .mutationRate(Constants.MUTATION_RATE)
                        .elitismCount(Constants.ELITISM_RATE)
                        .build())
                .initialIndividual(new Individual(
                        initialChromosome,
                        new TraversalSalesmanFitness(),
                        new TraversalSalesmanMutation()))
                .crossoverStrategy(new PartiallyMappedCrossover())
                .selectionStrategy(new RouletteWheelLinearSelection())
                .buildPopulation();

        geneticAlgorithm = new GeneticAlgorithm(population, generationsNumber);
    }

    private List<Point> retrieveResult() {
        List<Gen> fittestChromosome = population
                .getFittestIndividual()
                .getChromosome();
        return fittestChromosome.stream()
                .map(gen -> (Point) gen.getValue())
                .collect(Collectors.toList());
    }

}
