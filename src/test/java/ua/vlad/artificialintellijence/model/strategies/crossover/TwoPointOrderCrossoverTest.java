package ua.vlad.artificialintellijence.model.strategies.crossover;

import org.junit.Test;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;
import ua.vlad.artificialintellijence.model.strategies.mutation.MutationStrategy;
import ua.vlad.artificialintellijence.model.strategies.mutation.TraversalSalesmanMutation;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad on 24.03.17.
 */
public class TwoPointOrderCrossoverTest {

    private final int TESTS_NUMBER = 1_000;
    private final int GENS_NUMBER = 100;

    private TwoPointOrderCrossover crossover;
    private Individual individual1;
    private Individual individual2;
    private List<Gen> chromosome1;
    private List<Gen> chromosome2;

    public TwoPointOrderCrossoverTest() {
        crossover = new TwoPointOrderCrossover();

        chromosome1 = new ArrayList<>();
        for (int i = 0; i < GENS_NUMBER; i++) {
            chromosome1.add(new Gen(i));
        }

        chromosome2 = new ArrayList<>(chromosome1);
        Collections.shuffle(chromosome2);


        MutationStrategy mutation = new TraversalSalesmanMutation();
        individual1 = new Individual(chromosome1, chromosome -> 0, mutation);
        individual2 = new Individual(chromosome2, chromosome -> 0, mutation);
    }

    @Test
    public void testApply() {
    }

    @Test
    public void testBuildNewChromosome() {
        int cutPoint1 = 10;
        int cutPoint2 = 80;
        for (int i = 0; i < TESTS_NUMBER; i++) {
            List<Gen> offspring1 = crossover.buildNewChromosome(cutPoint1, cutPoint2,
                    chromosome1, chromosome2);
            List<Gen> offspring2 = crossover.buildNewChromosome(cutPoint1, cutPoint2,
                    chromosome2, chromosome1);

            Set<Gen> gens1 = new HashSet<>(offspring1);
            Set<Gen> gens2 = new HashSet<>(offspring2);

            assertEquals(GENS_NUMBER, gens1.size());
            assertEquals(GENS_NUMBER, gens2.size());
        }
    }
}
