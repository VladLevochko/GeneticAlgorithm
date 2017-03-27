package ua.vlad.artificialintellijence.model.strategies.crossover;

import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 19.03.17.
 */
public class TwoPointOrderCrossover implements CrossoverStrategy {

    private final String DIFFERENT_SIZE_EXCEPTION_MESSAGE = "Chromosomes received" +
            " for crossover have a different size!";
    private final String SAME_EXCEPTION_MESSAGE = "Chromosomes received for " +
            "crossover are same";
    private final String OFFSPRING_LENGTH_EXCEPTION_MESSAGE = "incorrect size of" +
            " offspring chromosome";

    @Override
    public Individual apply(Individual i1, Individual i2)
            throws CrossoverException, IllegalArgumentException {
        if (i1 == null || i2 == null) {
            throw new IllegalArgumentException();
        }

        List<Gen> chromosome1 = i1.getChromosome();
        List<Gen> chromosome2 = i2.getChromosome();

        if (chromosome1.size() != chromosome2.size()) {
            throw new CrossoverException(DIFFERENT_SIZE_EXCEPTION_MESSAGE);
        }

        if (chromosome1.equals(chromosome2)) {
            return i1.copy();
        }

        int randomPoint1 = (int) (Math.random() * chromosome1.size());
        int randomPoint2 = (int) (Math.random() * chromosome2.size());
        int cutPoint1 = Math.min(randomPoint1, randomPoint2);
        int cutPoint2 = Math.max(randomPoint1, randomPoint2);

        List<Gen> parentPart = chromosome1.subList(cutPoint1, cutPoint2);
        List<Gen> newChromosome = new ArrayList<>();

        int numberBefore = cutPoint1;
        int numberAfter = chromosome1.size() - cutPoint2;
        int pos = 0;

        pos = insert(chromosome2, newChromosome, parentPart, pos, numberBefore);
        newChromosome.addAll(parentPart);
        insert(chromosome2, newChromosome, newChromosome, pos, numberAfter);

        if (newChromosome.size() != chromosome1.size()
                | newChromosome.size() != chromosome2.size()) {
            throw new CrossoverException(OFFSPRING_LENGTH_EXCEPTION_MESSAGE);
        }

        return new Individual(newChromosome, i1.getFitnessFunction());
    }

    private int insert(List<Gen> donor, List<Gen> acceptor, List<Gen> checkList,
                        int donorPosition, int number) {
        while (number > 0 && donorPosition < donor.size()) {
            if (!checkList.contains(donor.get(donorPosition))) {
                acceptor.add(donor.get(donorPosition));
                number--;
            }
            donorPosition++;
        }

        return donorPosition;
    }
}
