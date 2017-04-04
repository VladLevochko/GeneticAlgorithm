package ua.vlad.artificialintellijence.model.strategies.crossover;

import ua.vlad.artificialintellijence.model.Pair;
import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.individual.Individual;

import java.util.*;

/**
 * Created by vlad on 28.03.17.
 */
public class PartiallyMappedCrossover implements CrossoverStrategy {

    private final String DIFFERENT_SIZE_EXCEPTION_MESSAGE = "Chromosomes received" +
            " for crossover have a different size!";
    private Random random;

    public PartiallyMappedCrossover() {
        this.random = new Random();
    }

    @Override
    public List<Individual> apply(List<Pair<Individual>> pairs) throws CrossoverException {
        List<Individual> offsprings = new ArrayList<>();
        for (Pair<Individual> pair : pairs) {

            Individual i1 = pair.getFirst();
            Individual i2 = pair.getSecond();
            List<Gen> chromosome1 = i1.getChromosome();
            List<Gen> chromosome2 = i2.getChromosome();

            if (chromosome1.size() != chromosome2.size()) {
                throw new CrossoverException(DIFFERENT_SIZE_EXCEPTION_MESSAGE);
            }

            int random1 = random.nextInt(chromosome1.size());
            int random2 = random.nextInt(chromosome1.size());

            int cutPoint1 = Math.min(random1, random2);
            int cutPoint2 = Math.max(random1, random2);

            Map<Gen, Gen> relationMap = getRelationMap(chromosome1, chromosome2,
                    cutPoint1, cutPoint2);

            offsprings.add(new Individual(
                    buildOffspringChromosome(chromosome1, chromosome2,
                            cutPoint1, cutPoint2, relationMap),
                    i1.getFitnessFunction(),
                    i1.getMutationStrategy()
            ));
        }

        return offsprings;
    }

    public Map<Gen, Gen> getRelationMap(List<Gen> chromosome1, List<Gen> chromosome2,
                                        int cutPosition1, int cutPosition2) {
        Map<Gen, Gen> relationMap = new HashMap<>();
        for (int i = cutPosition1; i < cutPosition2; i++) {
            if (chromosome1.get(i).equals(chromosome2.get(i))) {
                continue;
            }
            if (relationMap.containsKey(chromosome1.get(i)) &&
                    relationMap.containsKey(chromosome2.get(i))) {
                Gen first = relationMap.get(chromosome1.get(i));
                Gen second = relationMap.get(chromosome2.get(i));

                relationMap.remove(chromosome1.get(i));
                relationMap.remove(chromosome2.get(i));

                relationMap.put(first, second);
                relationMap.put(second, first);
            } else if (relationMap.containsKey(chromosome1.get(i))) {
                removeRedundant(relationMap, chromosome1.get(i), chromosome2.get(i));
            } else if (relationMap.containsKey(chromosome2.get(i))) {
                removeRedundant(relationMap, chromosome2.get(i), chromosome1.get(i));
            } else {
                relationMap.put(chromosome1.get(i), chromosome2.get(i));
                relationMap.put(chromosome2.get(i), chromosome1.get(i));
            }
        }

        return relationMap;
    }

    public void removeRedundant(Map<Gen, Gen> relationMap,
                                Gen redundantGen, Gen neededGen) {
        if (!relationMap.containsKey(redundantGen)) {
            return;
        }
        Gen g = relationMap.get(redundantGen);
        relationMap.put(g, neededGen);
        relationMap.put(neededGen, g);
        relationMap.remove(redundantGen);
    }

    public List<Gen> buildOffspringChromosome(List<Gen> parent1, List<Gen> parent2,
                                         int cutPoint1, int cutPoint2,
                                         Map<Gen, Gen> relationMap) {
        List<Gen> offspring = new ArrayList<>();
        boolean oneParentPart = cutPoint1 <= cutPoint2;
        if (oneParentPart) {
            for (int i = 0; i < cutPoint1; i++) {
                offspring.add(relationMap.getOrDefault(parent1.get(i), parent1.get(i)));
            }
            offspring.addAll(parent2.subList(cutPoint1, cutPoint2));
            for (int i = cutPoint2; i < parent1.size(); i++) {
                offspring.add(relationMap.getOrDefault(parent1.get(i), parent1.get(i)));
            }
        } else {
            offspring.addAll(parent1.subList(0, cutPoint2));
            for (int i = cutPoint2; i < cutPoint1; i++) {
                offspring.add(relationMap.getOrDefault(parent1.get(i), parent1.get(i)));
            }
            offspring.addAll(parent1.subList(cutPoint1, parent2.size()));
        }

        return offspring;
    }
}
