package ua.vlad.artificialintellijence.model.individual;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.strategies.fitness.TraversalSalesmanFitness;
import ua.vlad.artificialintellijence.model.strategies.mutation.TraversalSalesmanMutation;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad on 24.03.17.
 */
public class IndividualTest {

    private final int TESTS_NUMBER = 10000;

    private Individual individual;

    List<Point> points;
    List<Gen> chromosome;

    public IndividualTest() {
        points = Arrays.asList(
                new Point(1, 2),
                new Point(2, 2),
                new Point(3, 3));

        chromosome = points.stream()
                .map(Gen::new)
                .collect(Collectors.toList());

        this.individual = new Individual(chromosome, l -> 0, new TraversalSalesmanMutation());
    }

    @Test
    public void mutateTest() {
        Set<Gen> gensBefore = new HashSet<>(chromosome);
        for (int i = 0; i < TESTS_NUMBER; i++) {
            individual.mutate();

            List<Gen> mutatedChromosome = individual.getChromosome();
            Set<Gen> gensAfter = new HashSet<>(mutatedChromosome);

            assertEquals(gensBefore, gensAfter);
        }
    }
}
