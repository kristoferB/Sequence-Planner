/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sequenceplanner.editor;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 *
 * @author Evelina
 */
public class GlobPropC{

    private GlobPropV view;
    private GlobPropM model;

    GlobPropC(GlobPropM m, GlobPropV v){
        model = m;
        view = v;

//        view.addAddPropertyListener(new addPropertyListener());
        view.addEditorTreeModelListener(new EditorTreeModelListener());
    }


    class EditorTreeModelListener implements TreeModelListener {

        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            System.out.println("TreeModelEvent is generated");
            if(e.getTreePath().getLastPathComponent() instanceof IGlobalProperty){
                IGlobalProperty gp = (GlobalProperty) e.getTreePath().getLastPathComponent();
                System.out.println("Node changed");
            }

        }

        @Override
        public void treeNodesInserted(TreeModelEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void treeNodesRemoved(TreeModelEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void treeStructureChanged(TreeModelEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }



    }
/*    class addPropertyListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String prop = "";
            String[] values = new String[1];

            prop = view.getPropertyInput();
            values[0] = view.getValueInput();

            model.addProperty(prop, values);

//Testing if property and value is saved in model...
            LinkedList<GlobalProperty> gp = model.getGlobalProperties();
            for(int i = 0; i < gp.size(); i++){
                System.out.println(gp.get(i).getName());
                String[] val = gp.get(i).getValues();
                for(int j = 0; j < val.length; j++){
                    System.out.println(val[j]);
                }
            }
        }
    }*/

public static void main(String[] args){
    
    GlobPropM t_model = new GlobPropM();
    GlobPropV t_view = new GlobPropV(t_model);
    GlobPropC t_contr = new GlobPropC(t_model, t_view);


    JFrame frame = new JFrame();
    frame.setContentPane(t_view);
    frame.setSize(200, 200);

    frame.setTitle("Global Properties - test");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
}

}


