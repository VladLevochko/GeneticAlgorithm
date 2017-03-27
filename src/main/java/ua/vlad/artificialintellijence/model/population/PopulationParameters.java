package ua.vlad.artificialintellijence.model.population;

/**
 * Created by vlad on 12.03.17.
 */
public class PopulationParameters {

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public PopulationParameters(int populationSize, double mutationRate,
                                double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public int getElitismCount() {
        return elitismCount;
    }
}
