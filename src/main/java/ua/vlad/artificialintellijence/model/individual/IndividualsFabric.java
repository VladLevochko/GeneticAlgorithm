package ua.vlad.artificialintellijence.model.individual;

import ua.vlad.artificialintellijence.model.gen.Gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 12.03.17.
 */
public class IndividualsFabric {

    public static List<Individual> generateIndividuals(
            Individual initialIndividual, int populationSize) {
        List<Gen> initialChromosome = initialIndividual.getChromosome();
        List<Individual> population = new LinkedList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Gen> newChromosome = new ArrayList<>(initialChromosome.size());
            newChromosome.addAll(initialChromosome);
            Collections.shuffle(newChromosome);

            population.add(new Individual(
                    newChromosome,
                    initialIndividual.getFitnessFunction(),
                    initialIndividual.getMutationStrategy())
            );
        }

        return population;
    }
}
