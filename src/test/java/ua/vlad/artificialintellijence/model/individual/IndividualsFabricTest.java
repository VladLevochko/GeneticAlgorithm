package ua.vlad.artificialintellijence.model.individual;

import org.junit.Test;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.strategies.mutation.TraversalSalesmanMutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad on 27.03.17.
 */
public class IndividualsFabricTest {

    private final int GENS_NUMBER = 10;
    private final int INDIVIDUALS_NUMBER = 100;

    private Individual individual;
    private List<Gen> chromosome;

    public IndividualsFabricTest() {
        chromosome = new ArrayList<>();
        for (int i = 0; i < GENS_NUMBER; i++) {
            chromosome.add(new Gen(i));
        }
        individual = new Individual(chromosome, l -> 0, new TraversalSalesmanMutation());
    }

    @Test
    public void generateIndividualsTest() {
        List<Individual> individuals = IndividualsFabric
                .generateIndividuals(individual, INDIVIDUALS_NUMBER);
        Set<Gen> gens = new HashSet<>();
        for (Individual i : individuals) {
            gens.clear();
            gens.addAll(i.getChromosome());
            assertEquals(GENS_NUMBER, gens.size());
        }
    }
}
