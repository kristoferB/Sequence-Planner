package sequenceplanner.IO.EFA;

import java.util.Map;
import java.util.Set;
import org.supremica.external.avocades.common.EGA;
import sequenceplanner.visualization.algorithms.ISupremicaInteractionForVisualization;
import sequenceplanner.datamodel.condition.Condition;
import sequenceplanner.model.SOP.algorithms.ConditionsFromSopNode;
import sequenceplanner.model.data.ConditionData;
import sequenceplanner.model.data.OperationData;

/**
 * Help class for creation of transitions with Event, Guards, and Actions.<br/>
 * @author patrik
 */
public class SEGA extends EGA {

    public SEGA() {
        super();
    }

    public SEGA(String event) {
        super(event);
    }

    /**
     * To book <i>to</i> and unbook <i>from</i> if object is in <i>from</i><br/>
     * @param from object is here
     * @param to object should go here
     */
    public void addBasicPositionBookAndUnbook(String from, String to) {
        if (from.length() > "".length()) {
            andGuard(from + ">0");
            addAction(from + "-1");
        }
        if (to.length() > "".length()) {
            addAction(to + "+1");
        }
    }

    public String guardFromSPtoEFASyntaxTranslation(String ioGuard) {
        //Change all _i to ==0
        ioGuard = ioGuard.replaceAll("_i", "==0");
        //Change all _e to ==1
        ioGuard = ioGuard.replaceAll("_e", "==1");
        //Change all _f to ==2
        ioGuard = ioGuard.replaceAll("_f", "==2");
        //Change all A to &
        ioGuard = ioGuard.replaceAll("A", "&");
        //Change all V to |
        ioGuard = ioGuard.replaceAll("V", "|");

        return ioGuard;
    }

    public void addGuardBasedOnSPCondition(String iCondition, final String iOpVariablePrefix, final Set<Integer> iSet) {
        //Example of raw precondition 18_f A (143_iV19_f)

        //add precondition to guard
        if (!iCondition.equals("")) {

            //Change all ID to iOpVariablePrefix+ID
            for (final Integer i : iSet) {
                iCondition = iCondition.replaceAll(Integer.toString(i), iOpVariablePrefix + Integer.toString(i));
            }

            iCondition = guardFromSPtoEFASyntaxTranslation(iCondition);

            andGuard(iCondition);
        }
    }

    /**
     * Ands all conditions for parameter <p>iOpData</p> to this transition.<br/>
     * @param iOpData
     * @param iConditionType is given as {@link ConditionsFromSopNode.ConditionType}
     * @param iType is given as {@link ISupremicaInteractionForVisualization.Type}
     */
    public void addCondition(final OperationData iOpData, final ConditionsFromSopNode.ConditionType iConditionType, final ISupremicaInteractionForVisualization.Type iType, final Set<ConditionData> iConditionsToInclude) {
        final Map<ConditionData, Map<ConditionsFromSopNode.ConditionType, Condition>> map = iOpData.getConditions();
        for (final ConditionData sop : map.keySet()) {
            if (allowedNamePrefixForCondition(sop, iConditionsToInclude)) {
                final Map<ConditionsFromSopNode.ConditionType, Condition> innerMap = map.get(sop);

                if (innerMap.containsKey(iConditionType)) {

                    if (iType.equals(ISupremicaInteractionForVisualization.Type.LOOK_FOR_GUARD)) {
                        final String condition = "(" + innerMap.get(iConditionType).getGuard().toString() + ")";
                        if (!condition.equals("()")) {
                            andGuard(condition);
                        }
                    } else if (iType.equals(ISupremicaInteractionForVisualization.Type.LOOK_FOR_ACTION)) {
                        final String condition = innerMap.get(iConditionType).getAction().toString();
                        if (!condition.equals("()")) {
                            addAction(condition);
                        }
                    }
                }
            }
        }
    }

    private boolean allowedNamePrefixForCondition(final ConditionData iCondition, final Set<ConditionData> iConditionsToInclude) {
        if (iConditionsToInclude == null) {
            return true;
        }
        for (final ConditionData condition : iConditionsToInclude) {
            if (iCondition.equals(condition)) {
                return true;
            }
        }
        return false;
    }
}

