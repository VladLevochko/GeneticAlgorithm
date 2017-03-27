package ua.vlad.artificialintellijence.model.strategies.fitness;

import ua.vlad.artificialintellijence.model.gen.Gen;

import java.util.List;

/**
 * Created by vlad on 19.03.17.
 */
@FunctionalInterface
public interface FitnessStrategy {

    double calculate(List<Gen> chromosome);
}
