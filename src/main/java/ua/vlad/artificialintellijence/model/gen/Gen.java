package ua.vlad.artificialintellijence.model.gen;

/**
 * Created by vlad on 11.03.17.
 */
public class Gen <T> {

    private T gen;

    public Gen(T gen) {
        this.gen = gen;
    }

    public T getValue() {
        return gen;
    }

    public Gen<T> copy() {
        return new Gen<>(gen);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gen<?> gen1 = (Gen<?>) o;

        return gen != null ? gen.equals(gen1.gen) : gen1.gen == null;
    }

    @Override
    public int hashCode() {
        return gen != null ? gen.hashCode() : 0;
    }
}
