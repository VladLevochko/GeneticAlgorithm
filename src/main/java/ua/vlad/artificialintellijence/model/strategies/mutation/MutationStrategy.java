package ua.vlad.artificialintellijence.model.strategies.mutation;

import ua.vlad.artificialintellijence.model.gen.Gen;

import java.util.List;

/**
 * Created by vlad on 30.03.17.
 */
public interface MutationStrategy {
    void mutate(List<Gen> chromosome);
}
