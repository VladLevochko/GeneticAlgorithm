package ua.vlad.artificialintellijence.model;

import ua.vlad.artificialintellijence.model.population.Population;
import ua.vlad.artificialintellijence.model.strategies.crossover.CrossoverException;

/**
 * Created by vlad on 11.03.17.
 */
public class GeneticAlgorithm {

    private int generationsNumber;

    private Population population;

    public GeneticAlgorithm(Population population, int generationsNumber) {
        this.population = population;
        this.generationsNumber = generationsNumber;
    }

    public Population simulate() {
        for (int i = 0; i < generationsNumber; i++) {

            System.out.println("generation " + i);

            try {
                population.crossoverPopulation();
            } catch (CrossoverException e) {
                e.printStackTrace();
                break;
            }
            population.mutatePopulation();
        }

        return population;
    }

}
