/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sequenceplanner.efficientModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import sequenceplanner.efaconverter2.DefaultEFAConverter;
import sequenceplanner.efaconverter2.DefaultExport;
import sequenceplanner.efaconverter2.DefaultModelParser;
import sequenceplanner.efaconverter2.SpEFA.SpEFA;
import sequenceplanner.efaconverter2.SpEFA.SpEFAutomata;
import sequenceplanner.efaconverter2.SpEFA.SpLocation;
import sequenceplanner.efaconverter2.SpEFA.SpTransition;
import sequenceplanner.model.Model;
import sequenceplanner.model.TreeNode;
import sequenceplanner.model.data.OperationData;

/**
 *
 * @author shoaei
 */
public class OperationSequences {
    static Logger log = Logger.getLogger(OperationSequences.class);
    private Model model;
    private List<TreeNode> operationList;
    private List<List<String>> paths;

    public OperationSequences(Model model){
        this.model = model;
        operationList = new ArrayList<TreeNode>();
        paths = new ArrayList<List<String>>();
        init();
    }

    private void init() {
        // Get all operations in the model
        for (int i = 0; i < model.getOperationRoot().getChildCount(); ++i){
            DFS(model.getOperationRoot().getChildAt(i));
        }
    }

    public void run(){
        model.getOperationRoot().setNodeData(new OperationData("Project", 1000));
        DefaultModelParser parser = new DefaultModelParser(model);

        OperationGraph graph = buildGraph();

        /*
         * mode 0: Save no graph
         * mode 1: Save system graph (iteration zero)
         * mode 2: Saved each iteration graph
         */
        
        int mode = 0;
        graph.calculate(mode);
        paths = graph.getPaths();
        
        synchronize(paths, parser.getSpEFAutomata().getAutomatons());
        DefaultEFAConverter converter = new DefaultEFAConverter(parser.getSpEFAutomata());
        DefaultExport export = new DefaultExport(converter.getModule());
        export.save();

//        DialogEM dialogem = new DialogEM();
//        dialogem.createAndShow();
        //OperationGraph graph = new OperationGraph();
        //buildGraph(graph);
        // mode 0: Save no graph
        // mode 1: Save system graph (iteration zero)
        // mode 2: Saved each iteration graph
        //int mode = 0;
        //graph.calculate(mode);
        //paths = graph.getPaths();
        //createOperationView("Name of view", paths);
    }

    public int nbrOfOperation(){
        return operationList.size();
    }

    public int nbrOfPaths(){
        return paths.size();
    }

    private void print(Object o){
        System.err.println(o);
    }

    private void DFS(TreeNode node){
        if(node.getChildCount() > 0){
            for (int i = 0; i < node.getChildCount(); ++i){
                DFS(node.getChildAt(i));
            }
        }
        operationList.add(node);
    }

    private OperationGraph buildGraph() {
        OperationGraph graph = new OperationGraph();
        for(TreeNode op : operationList){
            graph.addState(Integer.toString(op.getId()));
        }

        for(TreeNode op : operationList){
            OperationData opData = (OperationData)op.getNodeData();
            LinkedList<LinkedList<Integer>> predecessors = opData.getPredecessors();
            for(LinkedList<Integer> predecessor : predecessors){
                if(predecessor.size() == 1)
                    graph.addEdge(Integer.toString(predecessor.iterator().next()), Integer.toString(op.getId()),
                                    Integer.toString(predecessor.iterator().next())+"to"+Integer.toString(op.getId()), 1.0);
            }
        }
        return graph;
    }

    private void synchronize(List<List<String>> paths, Collection<SpEFA> automatons) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private HashMap<String, Integer> updatLocationDB(SpEFAutomata automata){
        HashMap<String, Integer> locationDB = new HashMap<String, Integer>();
        for(SpEFA efa : automata.getAutomatons())
            for(SpLocation l : efa.getLocations())
                locationDB.put(l.getName(), l.getValue());
        return locationDB;
    }
    
    private SpEFA appendSpEFA(SpEFA firstSpEFA, SpEFA secondSpEFA){
        String efa1 = firstSpEFA.getName().replace("Seq_", "");
        String efa2 = secondSpEFA.getName().replace("Seq_", "");
        SpEFA result = new SpEFA("Seq_" + efa1 + efa2);
        
        return result;
    }
    
}
