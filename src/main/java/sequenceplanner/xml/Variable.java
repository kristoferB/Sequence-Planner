//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.15 at 04:02:06 em CEST 
//


package sequenceplanner.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute ref="{}id use="required""/>
 *       &lt;attribute ref="{}name"/>
 *       &lt;attribute name="maxValue" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="minValue" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="intialValue" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "variable")
public class Variable {

    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "maxValue", required = true)
    protected int maxValue;
    @XmlAttribute(name = "minValue", required = true)
    protected int minValue;
    @XmlAttribute(name = "intialValue", required = true)
    protected int intialValue;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the maxValue property.
     * 
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     */
    public void setMaxValue(int value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the minValue property.
     * 
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Sets the value of the minValue property.
     * 
     */
    public void setMinValue(int value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the intialValue property.
     * 
     */
    public int getIntialValue() {
        return intialValue;
    }

    /**
     * Sets the value of the intialValue property.
     * 
     */
    public void setIntialValue(int value) {
        this.intialValue = value;
    }

}
