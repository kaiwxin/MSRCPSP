package evolutionary_algorithms.differential_evolution_and_greedy_algorithm.initial_population;

import project.Project;
import scheduling.BasePopulation;

public interface InitialPopulation {
    public abstract BasePopulation generate(Project project);
}
