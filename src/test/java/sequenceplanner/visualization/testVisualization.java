/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sequenceplanner.visualization;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import sequenceplanner.efaconverter.ModelParser;
import sequenceplanner.efaconverter.OpNode;
import sequenceplanner.efaconverter.OperationSequencer;
import sequenceplanner.efaconverter.convertSeqToEFA;
import sequenceplanner.efaconverter.efamodel.SpEFAutomata;
import sequenceplanner.general.SP;
import sequenceplanner.model.data.OperationData;

/**
 *
 * @author patrik
 */
public class testVisualization {

    SP mSP = new SP();
    ModelParser mModelparser;
    OperationSequencer mOperationSequencer;

    public testVisualization() {
    }

//    @Test
    public void test1() {
        mSP.loadFromTemplateSOPXFile("resources/filesForTesting/KristoferTASEexample.sopx");

        this.mModelparser = new ModelParser(mSP.getModel());
        this.mOperationSequencer = new OperationSequencer(mModelparser);
        Set<OpNode> tops = mOperationSequencer.sequenceOperations();

        convertSeqToEFA seqToEFA = new convertSeqToEFA(tops, mModelparser);
        SpEFAutomata automata = seqToEFA.createSpEFA();
        seqToEFA.createWmodFile(automata);
    }

//    @Test
    public void test2() {
        OperationData opData;
        opData = mSP.insertOperation();
        opData.setName("Patrik");
        opData.setPrecondition("34=2");
        mModelparser = new ModelParser(mSP.getModel());
        for (OpNode opNode : mModelparser.getOperations()) {
            System.out.println(opNode.getName());
            assertTrue("true", opNode.getName().equals("Patrik"));
        }
    }

//    @Test
    public void test3() {
        mSP.loadFromSOPXFile("C:/Users/patrik/Desktop/precon.sopx");
        mModelparser = new ModelParser(mSP.getModel());
        for (OpNode opNode : mModelparser.getOperations()) {
            System.out.println(opNode.getName());
            OperationData opData = (OperationData) opNode.getTreeNode().getNodeData();
            System.out.println(" p " + opData.getRawPrecondition());
        }
    }
    
    @Test
    public void test4() {
        SpEFAutomata automata = new SpEFAutomata();
        automata.
    }

}
