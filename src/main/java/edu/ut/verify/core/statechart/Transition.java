package edu.ut.verify.core.statechart;

import edu.ut.verify.core.event.Event;
import edu.ut.verify.core.state.State;

/**
 * Created by Jerry Wang on 2018/11/7.
 */
public class Transition {

    State fromState;

    State toState;

    Event event;

    public Transition(){}

    public Transition(State fromState, State toState, Event event) {
        this.fromState = fromState;
        this.toState = toState;
        this.event = event;
    }

    public State getFromState() {
        return fromState;
    }

    public void setFromState(State fromState) {
        this.fromState = fromState;
    }

    public State getToState() {
        return toState;
    }

    public void setToState(State toState) {
        this.toState = toState;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getEventName(){
        return event.getName();
    }
}
