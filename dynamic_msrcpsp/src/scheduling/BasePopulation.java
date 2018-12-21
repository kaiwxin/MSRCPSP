package scheduling;
/**
 * 种群父类
 * @author XiongKai
 *
 */
public class BasePopulation {
    private BaseIndividual[] population;
    
    public BasePopulation(BaseIndividual[] pop){
        this.population=pop;
    }
    
    public int size(){
        return population.length;
    }

    public BaseIndividual[] getPopulation() {
        return population;
    }

    public void setPopulation(BaseIndividual[] population) {
        this.population = population;
    }
}
