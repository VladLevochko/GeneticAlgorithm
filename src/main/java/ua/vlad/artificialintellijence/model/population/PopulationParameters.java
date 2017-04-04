package ua.vlad.artificialintellijence.model.population;

/**
 * Created by vlad on 12.03.17.
 */
public class PopulationParameters {

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public static class Builder {

        private int populationSize;
        private double mutationRate;
        private double crossoverRate;
        private int elitismCount;

        public Builder populationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public Builder mutationRate(double mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public Builder crossoverRate(double crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        public Builder elitismCount(int elitismCount) {
            this.elitismCount = elitismCount;
            return this;
        }

        public PopulationParameters build() {
            return new PopulationParameters(populationSize, mutationRate,
                    crossoverRate, elitismCount);
        }
    }

    private PopulationParameters(int populationSize, double mutationRate,
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
