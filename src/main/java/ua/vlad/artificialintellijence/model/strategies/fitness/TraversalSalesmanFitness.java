package ua.vlad.artificialintellijence.model.strategies.fitness;

import ua.vlad.artificialintellijence.model.gen.Gen;

import java.awt.*;
import java.util.List;

/**
 * Created by vlad on 19.03.17.
 */
public class TraversalSalesmanFitness implements FitnessStrategy {

    @Override
    public double calculate(List<Gen> chromosome) {
        if (chromosome == null || chromosome.size() < 1) {
            throw new IllegalArgumentException();
        }

        double fitness = 0;

        Gen prev = chromosome.get(chromosome.size() - 1);
        for (Gen cur : chromosome) {
            double dx = ((Point) cur.getValue()).getX() - ((Point) prev.getValue()).getX();
            double dy = ((Point) cur.getValue()).getY() - ((Point) prev.getValue()).getY();

            fitness += Math.sqrt(dx * dx + dy * dy);
            prev = cur;
        }

        return fitness;
    }
}
