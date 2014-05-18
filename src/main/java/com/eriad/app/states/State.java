package com.eriad.app.states;

import com.eriad.app.App;
import com.eriad.app.stuff.ICGenerator;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class State {

    private final List<ICGenerator> generators;

    public State() { generators = createList(); }

    public abstract int[] getStateCodes();

    public void process() {

//        ExecutorService        executors  = Executors.newFixedThreadPool(generators.size());
        Class<? extends State> stateClass = this.getClass();

        for (ICGenerator generator : generators) {
//            executors.execute(
//                    () -> App.createApp(stateClass)
//                             .process(generator)
//            );
            App.createApp(stateClass).process(generator);
        }

    }

    public int getBeginAge() { return 22;  }
    public int getEndAge()   { return 114; }

    public List<ICGenerator> createList() {

        int beginAge     = getBeginAge();
        int endAge       = getEndAge();
        int[] stateCodes = getStateCodes();

        List<ICGenerator> generators = new ArrayList<>((endAge - beginAge) * stateCodes.length);

        for (int i = beginAge; i <= endAge; i++) {
            int year = new DateTime().minusYears(i).getYear();
            for (int code : stateCodes) {
                generators.add(new ICGenerator(year, code));
            }
        }

        return generators;
    }
}
