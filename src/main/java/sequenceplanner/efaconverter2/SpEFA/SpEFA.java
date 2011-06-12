/* Comment kb 100705
 * Had to create my own simplified EFA structure since the supremica version do not work!
 * I think it is a good idea anyway to have a clear interface to supremica.
 * 
 * This version handles two different types of guard and actions, one with 
 * strings (used when converting to supremica EFA, and one with Condition. 
 * Should be changed to only Condition!
 *
 *
 */

package sequenceplanner.efaconverter2.SpEFA;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import sequenceplanner.efaconverter2.condition.Condition;

/**
 * This class is a Extended Finite Automaton model. It consist of a set
 * of locations, a set of transistions 
 * @author kbe
 */
public class SpEFA {

    String efaName;
    SpLocation initialLocation;
    HashMap<String,SpLocation> locations;
    HashSet<SpTransition> transitions;
    Set<SpEvent> alphabet;


    public SpEFA(String name, String initialLocation) {
        init(name);
        setInitialLocation(initialLocation);
    }

    public SpEFA(String name){
        init(name);
    }

    private void init(String name){
        this.efaName = name;
        locations = new HashMap<String, SpLocation>();
        transitions = new HashSet<SpTransition>();
        alphabet = new HashSet<SpEvent>();
    }

    public SpLocation getInitialLocation(){
        return initialLocation;
    }

    public final void setInitialLocation(String name){
        SpLocation l = locations.get(name);
        if (l != null){
            initialLocation = l;
        } else{
            initialLocation = addLocation(name);
        }
    }

    public String getName(){
        return efaName;
    }

    public SpLocation addLocation(String locationName){
        if (!locations.containsKey(locationName)){
            SpLocation location = new SpLocation(locationName);
            addLocation(location);
        }
        return locations.get(locationName);
    }

    public void addLocation(SpLocation location){
        if (location == null) return;
        if (!locations.containsKey(location.getName())) locations.put(location.getName(), location);
        if (location.isInitialLocation()) initialLocation = location;
    }

    public void addLocation(SpLocation location, boolean initial){
        addLocation(location);
        if (initial) this.initialLocation = location;
    }
    
    // These addTransition should be handled in a better way! Fix later!

    public void addTransition(String event, SpLocation from, SpLocation to){
        addTransition(new SpTransition(new SpEvent(event), from, to, new Condition()));
    }

    public void addTransition(String event, String from, String to, String guard, String action){
        addTransition(new SpTransition(event, from, to, guard, action));
    }
    
    public void addTransition(SpTransition transition){
        transition = validateTransition(transition);
        this.alphabet.add(transition.getEvent());
        transition.getFrom().addOutTransition(transition);
        transition.getTo().addInTransition(transition);
        addLocation(transition.getFrom());
        addLocation(transition.getTo());
        transitions.add(transition);
    }

    // This method tries to keep the EFA consistent. But this must be fixed in a better way
    // redesign the EFA consitency evaluation.
    private SpTransition validateTransition(SpTransition transition){
         if (transition.getFrom() == null || transition.getTo() == null)
            throw new NullPointerException();
        if (locations.containsKey(transition.getFrom().getName()))
            transition.setFrom(locations.get(transition.getFrom().getName()));
        if (locations.containsKey(transition.getTo().getName()))
            transition.setTo(locations.get(transition.getTo().getName()));

         return transition;
    }



    public SpLocation getLocation(String name){
        return this.locations.get(name);
    }

    public Set<SpEvent> getAlphabet(){
        return this.alphabet;
    }

    public HashSet<SpTransition> getTransitions(){
        return transitions;
    }
    
    public Iterator<SpLocation> iterateLocations(){
        return locations.values().iterator();
    }
    
    public Iterator<SpTransition> iterateTransitions(){
        return transitions.iterator();
    }

    public Iterator<SpTransition> iterateSequenceTransitions(){
        return new SequenceTransitionIterator(initialLocation);
    }
    
    public Collection<SpLocation> getLocations(){
        return locations.values();
    }
    
    public boolean removeTransition(SpTransition transition){
        return transitions.remove(transition);
    }
    
    public void removeLocation(SpLocation location){
        transitions.removeAll(location.getInTransitions());
        transitions.removeAll(location.getOutTransitions());
        locations.remove(location.getName());
    }
    
    @Override
    public String toString(){
        return this.getName();
    }

    
    
    class SequenceTransitionIterator implements Iterator<SpTransition>{
        SpTransition next = null;

        public SequenceTransitionIterator(SpLocation initialLocation){
            if (initialLocation.getOutTransitions().size() > 1)
                throw new UnsupportedOperationException("Multiple outgoing transition");
            
            this.next = initialLocation.getOutTransitions().iterator().next();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public SpTransition next() {
        if (hasNext()){
                SpTransition tran = next;

                if (next.getTo().getOutTransitions().size() > 1)
                    throw new UnsupportedOperationException("Multiple outgoing transition");
                
                if (next.getTo().getOutTransitions().isEmpty())
                    next = null;
                else
                    next = next.getTo().getOutTransitions().iterator().next();
                
                return tran;
            }
            throw new NoSuchElementException("No more SpTransition");
        }

        // This method is a little bit scarry. Maybe we should do nothing?
        @Override
        public void remove() {
//            ConditionElement current = next;
//            if (current != null){
//                if (current.hasNextElement() && current.hasPreviousElement()) {
//                    ConditionElement p = current.getPreviousElement();
//                    ConditionElement n = current.getNextElement();
//                    ConditionOperator co;
//                    // Not sure about this but here OR is used if any of the operatores are or.
//                    if (current.getNextOperator().isOperationType(ConditionOperator.Type.OR)
//                            || current.getPreviousOperator().isOperationType(ConditionOperator.Type.OR))
//                    {
//                        co = new ConditionOperator(p, n, ConditionOperator.Type.OR);
//                    } else {
//                        co = new ConditionOperator(p, n, ConditionOperator.Type.AND);
//                    }
//                    p.setNextOperator(co);
//                    n.setPreviousOperator(co);
//                    next = p;
//                } else if (current.hasNextElement()) {
//                    ConditionElement n = current.getNextElement();
//                    n.setPreviousOperator(null);
//                    current.getNextOperator().clear();
//                    current.clear();
//                    next = n;
//                } else if (current.hasPreviousElement()) {
//                    ConditionElement prev = current.getPreviousElement();
//                    prev.setNextOperator(null);
//                    current.getPreviousOperator().clear();
//                    current.clear();
//                    next = prev;
//                } else {
//                    current.clear();
//                    next = null;
//                }
//            }
        }

    }    
}
