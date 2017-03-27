package ua.vlad.artificialintellijence.model.strategies.selection;

import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.population.Population;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by vlad on 19.03.17.
 */
public class RouletteWheelSelection implements SelectionStrategy {

    Random random;

    public RouletteWheelSelection() {
        random = new Random();
    }

    @Override
    public Individual select(Population population) {
        PriorityQueue<Individual> individuals = population.getIndividuals();
        int randomNumber = random.nextInt() % population.getFitness();
        int fitnessSum = 0;
        Individual parent = individuals.peek();

        while (fitnessSum < randomNumber && individuals.size() > 0) {
            parent = individuals.poll();
            fitnessSum += parent.getFitness();
        }

        return parent;
    }
}
