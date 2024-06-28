package Player.Factory;

import Player.SumStrategy.*;
import java.util.ArrayList;
import java.util.List;

public class SumStrategyFactory {
    private static SumStrategyFactory instance;

    private SumStrategyFactory() {}

    public static SumStrategyFactory getInstance() {
        if (instance == null) {
            instance = new SumStrategyFactory();
        }
        return instance;
    }

    public List<SumStrategy> createSumStrategies() {
        List<SumStrategy> sumStrategies = new ArrayList<SumStrategy>();
        sumStrategies.add(new Option1SumStrategy());
        sumStrategies.add(new Option2SumStrategy());
        sumStrategies.add(new Option3SumStrategy());
        return sumStrategies;
    }
}


