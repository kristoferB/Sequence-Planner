package sequenceplanner.efaconverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Methods to enhance a tree built up from {@link RVNode}s.
 * @author patrik
 */
public class RVNodeToolbox {

    final static String OPERATION = "operation";
    final RVNode mRoot = new RVNode();
    HashMap<String, Set<String>> mEventStateSetMap = null;
    HashMap<String, HashMap<RVNode, Set<String>>> mEventOperationLocationSetMap = null;
    String mStateNameExplanation = "";

    public RVNodeToolbox() {
    }

    public RVNode addOperation(OpNode iOpNode) {
        //Create new node
        RVNode newNode = new RVNode(iOpNode);
        //set parent
        newNode.mParent = mRoot;
        //set node type;
        newNode.nodeType = OPERATION;
        //set child relation
        mRoot.mChildren.add(newNode);

        return newNode;
    }

    public void findOperatoinRelations(RVNode iRvNode1, RVNode iRvNode2) {

    }

    public void findEventOperationRelations() {
        //For result
        mEventOperationLocationSetMap = new HashMap<String, HashMap<RVNode, Set<String>>>();

        //Create a map between the serial order of an operation in the state name and it's id.
        HashMap<Integer, RVNode> serialnrOperationMap = new HashMap<Integer, RVNode>();
        final String[] operationNames = mStateNameExplanation.split("\\|\\|");
        for (int i = 0; i < operationNames.length; ++i) {
            final String operationId = operationNames[i].replaceAll("o", "");
            final RVNode rvNode = mRoot.getChildWithStringId(operationId);
            serialnrOperationMap.put(i, rvNode);
        }

        //Loop all events to find what operation locations that are present
        for (String key : mEventStateSetMap.keySet()) {
            Set<String> stateNameSet = mEventStateSetMap.get(key);

            //Init of map where result is stored, add to RVNode (operation)
            HashMap<RVNode, Set<String>> opLocationSetMap = getOperationLocationSetMapForEvent(key);

            mEventOperationLocationSetMap.put(key, new HashMap<RVNode, Set<String>>());
            for (RVNode operation : serialnrOperationMap.values()) {
                mEventOperationLocationSetMap.get(key).put(operation, new HashSet<String>());
                opLocationSetMap.put(operation, new HashSet<String>());
            }

            //Loop all states for event and store locations for each operation
            for (String stateName : stateNameSet) {
                final String[] opLocations = stateName.split("\\.");
                for (int i = 0; i < opLocations.length; ++i) {
                    final String opLocation = opLocations[i];
                    mEventOperationLocationSetMap.get(key).get(serialnrOperationMap.get(i)).add(opLocation);
                    opLocationSetMap.get(serialnrOperationMap.get(i)).add(opLocation);
                }
            }

//            System.out.println(key);
//            System.out.println(mEventOperationLocationSetMap.get(key).toString());
        }
    }

    private HashMap<RVNode, Set<String>> getOperationLocationSetMapForEvent(String iKey) {
        //Work with name
        iKey = iKey.replaceAll("e", "");
        String eventType = "down";
        if (iKey.contains("up")) {
            eventType = "up";
        }
        final String operationId = iKey.replaceAll(eventType, "");

        //Create storage for event
        final RVNode rvNode = mRoot.getChildWithStringId(operationId);
        rvNode.mEventOperationLocationSetMap.put(eventType, new HashMap<RVNode, Set<String>>());

        return rvNode.mEventOperationLocationSetMap.get(eventType);
    }
}
