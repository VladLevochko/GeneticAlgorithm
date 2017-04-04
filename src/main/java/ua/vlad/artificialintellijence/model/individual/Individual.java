package ua.vlad.artificialintellijence.model.individual;

import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.strategies.fitness.FitnessStrategy;
import ua.vlad.artificialintellijence.model.strategies.mutation.MutationStrategy;

import java.util.*;

/**
 * Created by vlad on 12.03.17.
 */
public class Individual {

    private List<Gen> chromosome;
    private FitnessStrategy fitnessFunction;
    private MutationStrategy mutationStrategy;
    private double fitness;

    public Individual(List<Gen> chromosome,
                      FitnessStrategy fitnessFunction,
                      MutationStrategy mutationStrategy) {
        this.chromosome = chromosome;
        this.fitnessFunction = fitnessFunction;
        this.mutationStrategy = mutationStrategy;
        calculateFitness();
    }

    public List<Gen> getChromosome() {
        List<Gen> chromosomeCopy = new ArrayList<>();
        for (Gen gen : chromosome) {
            chromosomeCopy.add(gen.copy());
        }

        return chromosomeCopy;
    }

    public FitnessStrategy getFitnessFunction() {
        return fitnessFunction;
    }

    public MutationStrategy getMutationStrategy() {
        return mutationStrategy;
    }

    private void calculateFitness() {
        this.fitness = fitnessFunction.calculate(chromosome);
    }

    public double getFitness() {
        return this.fitness;
    }

    public void mutate() {
        mutationStrategy.mutate(this.chromosome);
    }

    public Individual copy() {
        return new Individual(getChromosome(), fitnessFunction, mutationStrategy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;

        if (Double.compare(that.fitness, fitness) != 0) return false;

        if (chromosome == null) {
            return that.chromosome == null;
        }

        return this.chromosome.containsAll(that.chromosome);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = chromosome != null ? chromosome.hashCode() : 0;
        temp = Double.doubleToLongBits(fitness);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
