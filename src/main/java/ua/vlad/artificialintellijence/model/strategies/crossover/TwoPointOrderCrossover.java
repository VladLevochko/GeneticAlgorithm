package ua.vlad.artificialintellijence.model.strategies.crossover;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 19.03.17.
 */
public class TwoPointOrderCrossover implements CrossoverStrategy {

    private final String DIFFERENT_SIZE_EXCEPTION_MESSAGE = "Chromosomes received" +
            " for crossover have a different size!";
    private final String OFFSPRING_LENGTH_EXCEPTION_MESSAGE = "Incorrect size of" +
            " offspring chromosome";

    @Override
    public List<Individual> apply(List<Pair<Individual>> pairs)
            throws CrossoverException, IllegalArgumentException {

        List<Individual> offsprings = new LinkedList<>();
        Individual i1;
        Individual i2;
        for (Pair<Individual> pair : pairs) {
            i1 = pair.getFirst();
            i2 = pair.getSecond();
            List<Gen> chromosome1 = i1.getChromosome();
            List<Gen> chromosome2 = i2.getChromosome();

            if (chromosome1.size() != chromosome2.size()) {
                throw new CrossoverException(DIFFERENT_SIZE_EXCEPTION_MESSAGE);
            }

            if (chromosome1.equals(chromosome2)) {
                offsprings.add(i1);
                offsprings.add(i2);
                continue;
            }

            int randomPoint1 = (int) (Math.random() * chromosome1.size());
            int randomPoint2 = (int) (Math.random() * chromosome2.size());
            int cutPoint1 = Math.min(randomPoint1, randomPoint2);
            int cutPoint2 = Math.max(randomPoint1, randomPoint2);

            List<Gen> firstOffspringChromosome = buildNewChromosome(
                    cutPoint1, cutPoint2, chromosome1, chromosome2
            );
            List<Gen> secondOffspringChromosome = buildNewChromosome(
                    cutPoint1, cutPoint2, chromosome2, chromosome1
            );

            if (firstOffspringChromosome.size() != chromosome1.size()
                    || secondOffspringChromosome.size() != chromosome1.size()) {
                throw new CrossoverException(OFFSPRING_LENGTH_EXCEPTION_MESSAGE);
            }

            offsprings.add(new Individual(
                    firstOffspringChromosome,
                    i1.getFitnessFunction(),
                    i1.getMutationStrategy())
            );
            offsprings.add(new Individual(
                    secondOffspringChromosome,
                    i1.getFitnessFunction(),
                    i1.getMutationStrategy()
            ));
        }

        return offsprings;
    }

    public List<Gen> buildNewChromosome(int cutPoint1, int cutPoint2,
                                   List<Gen> parent1, List<Gen> parent2) {
        List<Gen> parentPart = parent1.subList(cutPoint1, cutPoint2);
        List<Gen> newChromosome = new ArrayList<>();

        int numberBefore = cutPoint1;
        int numberAfter = parent1.size() - cutPoint2;
        int pos = 0;

        pos = insertGens(parent2, newChromosome, parentPart, pos, numberBefore);
        newChromosome.addAll(parentPart);
        insertGens(parent2, newChromosome, newChromosome, pos, numberAfter);

        return newChromosome;
    }

    public int insertGens(List<Gen> donor, List<Gen> acceptor, List<Gen> checkList,
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
