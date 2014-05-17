package com.eriad.app.states;

import com.eriad.app.App;
import com.eriad.app.stuff.ICGenerator;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    // TODO: Fill in statecodes from http://www.jpn.com.my/state_code.htm

    private final List<ICGenerator> generators;

    public State() {
        generators = createList();
    }

    public abstract int[] getStateCodes();

    public void process() {

        App app = App.createApp();
        for (ICGenerator generator : generators) {

            System.out.println();
            System.out.println("Begin Processing");
            System.out.println(this.getClass().getName());
            System.out.println("Year: " + generator.getYear());
            System.out.println("StateCode: " + generator.getStateCode());

            app.process(generator);
        }

    }

    public int getBeginAge() { return 22; };
    public int getEndAge()   {return 114; };

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
