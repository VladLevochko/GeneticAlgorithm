package ua.vlad.artificialintellijence.model.individual;

import ua.vlad.artificialintellijence.model.gen.Gen;
import ua.vlad.artificialintellijence.model.strategies.fitness.FitnessStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 12.03.17.
 */
public class Individual {

    private List<Gen> chromosome;
    private FitnessStrategy fitnessFunction;
    private double fitness;

    public Individual(List<Gen> chromosome,
                      FitnessStrategy fitnessFunction) {
        this.chromosome = chromosome;
        this.fitnessFunction = fitnessFunction;
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

    private void calculateFitness() {
        this.fitness = fitnessFunction.calculate(chromosome);
    }

    public double getFitness() {
        return this.fitness;
    }

    public void mutate() {
        int mutationPosition1 = (int) (Math.random() * chromosome.size());
        int mutationPosition2 = (int) (Math.random() * chromosome.size());

        Gen temporary = chromosome.get(mutationPosition1);
        chromosome.set(mutationPosition1, chromosome.get(mutationPosition2));
        chromosome.set(mutationPosition2, temporary);
    }

    public Individual copy() {
        return new Individual(getChromosome(), fitnessFunction);
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

//        return chromosome != null ? chromosome.equals(that.chromosome) : that.chromosome == null;
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
