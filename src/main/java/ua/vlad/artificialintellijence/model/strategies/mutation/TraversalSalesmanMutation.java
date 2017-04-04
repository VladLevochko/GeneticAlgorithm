package ua.vlad.artificialintellijence.model.strategies.mutation;

import ua.vlad.artificialintellijence.model.gen.Gen;

import java.util.Collections;
import java.util.List;

/**
 * Created by vlad on 30.03.17.
 */
public class TraversalSalesmanMutation implements MutationStrategy {
    @Override
    public void mutate(List<Gen> chromosome) {
        int mutationPosition1 = (int) (Math.random() * chromosome.size());
        int mutationPosition2 = (int) (Math.random() * chromosome.size());

        Collections.swap(chromosome, mutationPosition1, mutationPosition2);
    }
}
