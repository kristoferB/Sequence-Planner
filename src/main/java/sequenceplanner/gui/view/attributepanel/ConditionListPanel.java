package sequenceplanner.gui.view.attributepanel;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sequenceplanner.condition.Condition;
import sequenceplanner.gui.controller.AttributeMouseAdapter;

/**
 * Panel showing a list of Conditions. 
 * @author Qw4z1
 */
public class ConditionListPanel extends JPanel implements IConditionListPanel {

    private HashMap<String, Condition> conditionList;
    private JPanel internalPanel;
    JLabel conditionLabel;
    OperationAttributeEditor editor;

    public ConditionListPanel(OperationAttributeEditor editor) {
        this.editor = editor;
        conditionList = new HashMap<String, Condition>();
        init();
    }

    private void init() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    @Override
    public void addCondition(String key, Condition condition) {
        conditionList.remove(key);
        conditionList.put(key, condition);
        updateList();

    }

    private void updateList() {
        System.out.println("updateList CLP");
        if (conditionList != null) {
            this.removeAll();
            for (String key : conditionList.keySet()) {
                if (conditionList.get(key) != null) {
                    System.out.println("kommer hit");
                    internalPanel = new JPanel();
                    internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.X_AXIS));
                    conditionLabel = new JLabel(key + " " + conditionList.get(key).toString());
                    conditionLabel.setVisible(true);
                    internalPanel.add(conditionLabel);
                    this.add(internalPanel);
                    internalPanel.setVisible(true);
                    addConditionListener(new AttributeMouseAdapter(editor));
                } else {
                    this.removeAll();
                    
                }
                this.updateUI();
            }

        } else {
            System.out.println("removeall");
            this.removeAll();
            this.repaint();
        }
    }

    @Override
    public void deleteCondition(String conditionValue){
            System.out.println("Delete: "+ conditionValue);
            //opData.removeCondition();
        
    }


    @Override
    public void removeCondition(Condition condition) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCondition(int i) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Condition condition) {
        return conditionList.containsValue(condition);
    }

    public void deleteCondition(Component conditionLabel) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    void clear() {
        System.out.println("clear2");
        this.removeAll();
        this.repaint();

}
    /*
     * Adds ActionListener to the conditions that are listed
     */
     public void addConditionListener(AttributeMouseAdapter l){
         conditionLabel.addMouseListener(l);
     }
}
