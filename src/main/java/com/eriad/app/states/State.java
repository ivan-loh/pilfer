package com.eriad.app.states;

import com.eriad.app.App;
import com.eriad.app.stuff.ICGenerator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    private final Logger            LOG         = LoggerFactory.getLogger(this.getClass() + "::" + this.toString());
    private final List<ICGenerator> generators;

    public State() { generators = createList(); }

    public abstract int[] getStateCodes();

    public void process() {

        App app = App.createApp();
        for (ICGenerator generator : generators) {

            LOG.trace("Begin Processing");
            LOG.trace("State:     " + getClass().getName());
            LOG.trace("Year:      " + generator.getYear());
            LOG.trace("StateCode: " + generator.getStateCode());

            app.process(generator);
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
