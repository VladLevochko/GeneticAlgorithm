package ua.vlad.artificialintellijence.model.strategies.crossover;

import org.junit.Ignore;
import org.junit.Test;
import ua.vlad.artificialintellijence.model.gen.Gen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad on 28.03.17.
 */
public class PartiallyMappedCrossoverTest {

    private final String TEST_FILE = "/home/vlad/IdeaProjects/ArtificialIntelligence/src/test/java/ua/vlad/artificialintellijence/model/strategies/crossover/test3.in";

    private int chromosomeSize;
    private int relationMapSize;
    private int cutPoint1;
    private int cutPoint2;

    private List<Gen> parent1;
    private List<Gen> parent2;
    private List<Gen> expectedOffspring;
    private PartiallyMappedCrossover  crossover;
    private Map<Gen, Gen> expectedRelationMap;

    public PartiallyMappedCrossoverTest() {
        crossover = new PartiallyMappedCrossover();

        try (Scanner in = new Scanner(new FileInputStream(new File(TEST_FILE)))) {
            chromosomeSize = in.nextInt();
            cutPoint1 = in.nextInt();
            cutPoint2 = in.nextInt();
            parent1 = readChromosome(in, chromosomeSize);
            parent2 = readChromosome(in, chromosomeSize);
            expectedOffspring = readChromosome(in, chromosomeSize);
            relationMapSize = in.nextInt();
            expectedRelationMap = readRelationMap(in, relationMapSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Gen> readChromosome(Scanner in, int chromosomeSize) {
        List<Gen> chromosome = new ArrayList<>();
        for (int i = 0; i < chromosomeSize; i++) {
            chromosome.add(new Gen(in.nextInt()));
        }

        return chromosome;
    }

    private Map<Gen, Gen> readRelationMap(Scanner in, int relationsNumber) {
        Map<Gen, Gen> relationMap = new HashMap<>();
        for (int i = 0; i < relationsNumber; i++) {
            Gen gen1 = new Gen(in.nextInt());
            Gen gen2 = new Gen(in.nextInt());
            relationMap.put(gen1, gen2);
            relationMap.put(gen2, gen1);
        }

        return relationMap;
    }

    @Test
    public void testBuildOffspringChromosome() {
        List<Gen> offspring = crossover
                .buildOffspringChromosome(parent1, parent2,
                        cutPoint1, cutPoint2, expectedRelationMap);

        assertEquals(expectedOffspring, offspring);
    }

    @Test
    public void testBuildOffspringOfEqualParents() {
        List<Gen> offspring = crossover
                .buildOffspringChromosome(parent1, parent1,
                        cutPoint1, cutPoint2, new HashMap<>());

        assertEquals(parent1, offspring);
    }

    @Test
    public void testGetRelationMap() {
        Map<Gen, Gen> relationMap = crossover.getRelationMap(parent1, parent2,
                cutPoint1, cutPoint2);

        assertEquals(expectedRelationMap, relationMap);
    }

    @Test
    public void testGetRelationMapOfEqualParents() {
        Map<Gen, Gen> relationMap = crossover.getRelationMap(parent1, parent1,
                cutPoint1, cutPoint2);

        assertEquals(new HashMap<>(), relationMap);
    }


    @Ignore("had used to find test")
    @Test
    public void testIntegrityBuildingOffsprings() {
        int chromosomeSize = 10;
        List<Gen> parent1 = IntStream.rangeClosed(1, chromosomeSize)
                .mapToObj(value -> new Gen(value))
                .collect(Collectors.toList());
        List<Gen> parent2 = new ArrayList<>(parent1);
        Collections.shuffle(parent1);
        Collections.shuffle(parent2);

        Set<Gen> expectedSet = new HashSet<>(parent1);
        Map<Gen, Gen> relationMap;

        System.out.println("parent1");
        parent1.forEach(value -> System.out.print(value.getValue() + " "));
        System.out.println();
        System.out.println("parent2");
        parent2.forEach(value -> System.out.print(value.getValue() + " "));
        System.out.println();

        List<Gen> offspring;
        for (int i = 0; i < chromosomeSize; i++) {
            for (int j = i; j < chromosomeSize; j++) {
                System.out.println(i + " " + j);
                relationMap = crossover.getRelationMap(parent1, parent2, i, j);
                offspring = crossover.buildOffspringChromosome(
                        parent1, parent2, i, j, relationMap);

                Set<Gen> actualSet = new HashSet<>(offspring);
                assertEquals(expectedSet, actualSet);
            }
        }
    }
}