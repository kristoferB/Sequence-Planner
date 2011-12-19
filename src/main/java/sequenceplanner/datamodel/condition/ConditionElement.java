package sequenceplanner.datamodel.condition;

/**
 * This class defines the elements that are used in a condition.
 * @author kbe
 */
public abstract class ConditionElement {

    private ConditionOperator nextOperator;
    private ConditionOperator prevOperator;

    protected ConditionElement() {
        nextOperator = null;
        prevOperator = null;
    }

    protected ConditionElement(ConditionOperator previousOperator, ConditionOperator nextOperator) {
        this.nextOperator = nextOperator;
        this.prevOperator = previousOperator;
    }

    public ConditionOperator getNextOperator() {
        return nextOperator;
    }

    public void setNextOperator(ConditionOperator next) {
        this.nextOperator = next;
    }

    public boolean hasNextOperator() {
        return this.nextOperator != null;
    }

    public ConditionOperator getPreviousOperator() {
        return prevOperator;
    }

    public void setPreviousOperator(ConditionOperator prev) {
        this.prevOperator = prev;
    }

    public boolean hasPrevioustOperator() {
        return this.prevOperator != null;
    }

    public ConditionElement getNextElement() {
        if (hasNextOperator()) {
            if (getNextOperator().hasNextElement()) {
                return getNextOperator().getNextElement();
            }
        }
        return null;
    }

    public ConditionElement getPreviousElement() {
        if (hasPrevioustOperator()) {
            if (getPreviousOperator().hasPreviousElement()) {
                return getPreviousOperator().getPreviousElement();
            }
        }
        return null;
    }

    public boolean hasNextElement() {
        return getNextElement() != null;
    }

    public boolean hasPreviousElement() {
        return getPreviousElement() != null;
    }

    public void clear() {
        nextOperator = null;
        prevOperator = null;
    }

    public abstract boolean isExpression();

    public abstract boolean isStatement();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

    @Override
    public ConditionElement clone() {
        if (this.isStatement()) {
            final ConditionStatement cs = (ConditionStatement) this;
            final String variable = cs.getVariable();
            final ConditionStatement.Operator operator = cs.getOperator();
            final String value = cs.getValue();
            return new ConditionStatement(variable, operator, value);
        } else if (this.isExpression()) {
            ConditionElement ce = ((ConditionExpression) this).getExpressionRoot();
            final ConditionExpression ceReturn = new ConditionExpression(ce.clone());
            while (ce.hasNextOperator() && ce.hasNextElement()) {
                final ConditionOperator nextCo = new ConditionOperator(ce.getNextOperator().getOperatorType());
                final ConditionElement nextCe = ce.getNextElement().clone();
                ceReturn.appendElement(nextCo, nextCe);
                ce = ce.getNextElement();
            }
            return ceReturn;
        }
        return null;
    }
}
