package ua.vlad.artificialintellijence.model.strategies.crossover;

import org.junit.Test;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;

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
//        Collections.copy(chromosome2, chromosome1);
        Collections.shuffle(chromosome2);
//        for (int i = GENS_NUMBER - 1; i >= 0; i--) {
//            chromosome2.add(new Gen(i));
//        }

        individual1 = new Individual(chromosome1, chromosome -> 0);
        individual2 = new Individual(chromosome2, chromosome -> 0);
    }

    @Test
    public void testApply() {
        for (int i = 0; i < TESTS_NUMBER; i++) {
            Individual offspring = null;
            try {
                offspring = crossover.apply(individual1, individual2);
            } catch (CrossoverException e) {
                e.printStackTrace();
            }


            Set<Gen> gens = new HashSet<>();
            List<Gen> offspringChromosome = offspring.getChromosome();
            gens.addAll(offspringChromosome);

            assertEquals(chromosome1.size(), gens.size());
        }
    }
}
