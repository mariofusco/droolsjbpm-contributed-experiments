//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.05.21 at 09:59:49 PM BST 
//


package mismo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AUS_EMPLOYER_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AUS_EMPLOYER_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="_ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="_Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="_StreetAddress" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="_City" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="_State" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="_PostalCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="_TelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CurrentEmploymentMonthsOnJob" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CurrentEmploymentTimeInLineOfWorkYears" type="{}AUS_MISMONumeric" />
 *       &lt;attribute name="CurrentEmploymentYearsOnJob" type="{}AUS_MISMONumeric" />
 *       &lt;attribute name="EmploymentBorrowerSelfEmployedIndicator" type="{}AUS_MISMOIndicatorType" />
 *       &lt;attribute name="EmploymentCurrentIndicator" type="{}AUS_MISMOIndicatorType" />
 *       &lt;attribute name="EmploymentPositionDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EmploymentPrimaryIndicator" type="{}AUS_MISMOIndicatorType" />
 *       &lt;attribute name="IncomeEmploymentMonthlyAmount" type="{}AUS_MISMOMoney" />
 *       &lt;attribute name="PreviousEmploymentEndDate" type="{}AUS_MISMODateTime" />
 *       &lt;attribute name="PreviousEmploymentStartDate" type="{}AUS_MISMODateTime" />
 *       &lt;attribute name="SpecialBorrowerEmployerRelationshipType" type="{}AUS_SpecialBorrowerEmployerRelationshipTypeEnumerated" />
 *       &lt;attribute name="SpecialBorrowerEmployerRelationshipTypeOtherDescription" type="{}AUS_MISMOIndicatorType" />
 *       &lt;attribute name="EmployedAbroadIndicator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="_Country" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AUS_EMPLOYER_Type")
public class AUSEMPLOYERType {

    @XmlAttribute(name = "_ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "_Name")
    protected String name;
    @XmlAttribute(name = "_StreetAddress")
    protected String streetAddress;
    @XmlAttribute(name = "_City")
    protected String city;
    @XmlAttribute(name = "_State")
    protected String state;
    @XmlAttribute(name = "_PostalCode")
    protected String postalCode;
    @XmlAttribute(name = "_TelephoneNumber")
    protected String telephoneNumber;
    @XmlAttribute(name = "CurrentEmploymentMonthsOnJob")
    protected String currentEmploymentMonthsOnJob;
    @XmlAttribute(name = "CurrentEmploymentTimeInLineOfWorkYears")
    protected String currentEmploymentTimeInLineOfWorkYears;
    @XmlAttribute(name = "CurrentEmploymentYearsOnJob")
    protected String currentEmploymentYearsOnJob;
    @XmlAttribute(name = "EmploymentBorrowerSelfEmployedIndicator")
    protected String employmentBorrowerSelfEmployedIndicator;
    @XmlAttribute(name = "EmploymentCurrentIndicator")
    protected String employmentCurrentIndicator;
    @XmlAttribute(name = "EmploymentPositionDescription")
    protected String employmentPositionDescription;
    @XmlAttribute(name = "EmploymentPrimaryIndicator")
    protected String employmentPrimaryIndicator;
    @XmlAttribute(name = "IncomeEmploymentMonthlyAmount")
    protected String incomeEmploymentMonthlyAmount;
    @XmlAttribute(name = "PreviousEmploymentEndDate")
    protected String previousEmploymentEndDate;
    @XmlAttribute(name = "PreviousEmploymentStartDate")
    protected String previousEmploymentStartDate;
    @XmlAttribute(name = "SpecialBorrowerEmployerRelationshipType")
    protected AUSSpecialBorrowerEmployerRelationshipTypeEnumerated specialBorrowerEmployerRelationshipType;
    @XmlAttribute(name = "SpecialBorrowerEmployerRelationshipTypeOtherDescription")
    protected String specialBorrowerEmployerRelationshipTypeOtherDescription;
    @XmlAttribute(name = "EmployedAbroadIndicator")
    protected String employedAbroadIndicator;
    @XmlAttribute(name = "_Country")
    protected String country;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
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
     * Gets the value of the streetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the value of the streetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetAddress(String value) {
        this.streetAddress = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the telephoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Sets the value of the telephoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephoneNumber(String value) {
        this.telephoneNumber = value;
    }

    /**
     * Gets the value of the currentEmploymentMonthsOnJob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentEmploymentMonthsOnJob() {
        return currentEmploymentMonthsOnJob;
    }

    /**
     * Sets the value of the currentEmploymentMonthsOnJob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentEmploymentMonthsOnJob(String value) {
        this.currentEmploymentMonthsOnJob = value;
    }

    /**
     * Gets the value of the currentEmploymentTimeInLineOfWorkYears property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentEmploymentTimeInLineOfWorkYears() {
        return currentEmploymentTimeInLineOfWorkYears;
    }

    /**
     * Sets the value of the currentEmploymentTimeInLineOfWorkYears property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentEmploymentTimeInLineOfWorkYears(String value) {
        this.currentEmploymentTimeInLineOfWorkYears = value;
    }

    /**
     * Gets the value of the currentEmploymentYearsOnJob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentEmploymentYearsOnJob() {
        return currentEmploymentYearsOnJob;
    }

    /**
     * Sets the value of the currentEmploymentYearsOnJob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentEmploymentYearsOnJob(String value) {
        this.currentEmploymentYearsOnJob = value;
    }

    /**
     * Gets the value of the employmentBorrowerSelfEmployedIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentBorrowerSelfEmployedIndicator() {
        return employmentBorrowerSelfEmployedIndicator;
    }

    /**
     * Sets the value of the employmentBorrowerSelfEmployedIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentBorrowerSelfEmployedIndicator(String value) {
        this.employmentBorrowerSelfEmployedIndicator = value;
    }

    /**
     * Gets the value of the employmentCurrentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentCurrentIndicator() {
        return employmentCurrentIndicator;
    }

    /**
     * Sets the value of the employmentCurrentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentCurrentIndicator(String value) {
        this.employmentCurrentIndicator = value;
    }

    /**
     * Gets the value of the employmentPositionDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentPositionDescription() {
        return employmentPositionDescription;
    }

    /**
     * Sets the value of the employmentPositionDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentPositionDescription(String value) {
        this.employmentPositionDescription = value;
    }

    /**
     * Gets the value of the employmentPrimaryIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentPrimaryIndicator() {
        return employmentPrimaryIndicator;
    }

    /**
     * Sets the value of the employmentPrimaryIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentPrimaryIndicator(String value) {
        this.employmentPrimaryIndicator = value;
    }

    /**
     * Gets the value of the incomeEmploymentMonthlyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeEmploymentMonthlyAmount() {
        return incomeEmploymentMonthlyAmount;
    }

    /**
     * Sets the value of the incomeEmploymentMonthlyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeEmploymentMonthlyAmount(String value) {
        this.incomeEmploymentMonthlyAmount = value;
    }

    /**
     * Gets the value of the previousEmploymentEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreviousEmploymentEndDate() {
        return previousEmploymentEndDate;
    }

    /**
     * Sets the value of the previousEmploymentEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreviousEmploymentEndDate(String value) {
        this.previousEmploymentEndDate = value;
    }

    /**
     * Gets the value of the previousEmploymentStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreviousEmploymentStartDate() {
        return previousEmploymentStartDate;
    }

    /**
     * Sets the value of the previousEmploymentStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreviousEmploymentStartDate(String value) {
        this.previousEmploymentStartDate = value;
    }

    /**
     * Gets the value of the specialBorrowerEmployerRelationshipType property.
     * 
     * @return
     *     possible object is
     *     {@link AUSSpecialBorrowerEmployerRelationshipTypeEnumerated }
     *     
     */
    public AUSSpecialBorrowerEmployerRelationshipTypeEnumerated getSpecialBorrowerEmployerRelationshipType() {
        return specialBorrowerEmployerRelationshipType;
    }

    /**
     * Sets the value of the specialBorrowerEmployerRelationshipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AUSSpecialBorrowerEmployerRelationshipTypeEnumerated }
     *     
     */
    public void setSpecialBorrowerEmployerRelationshipType(AUSSpecialBorrowerEmployerRelationshipTypeEnumerated value) {
        this.specialBorrowerEmployerRelationshipType = value;
    }

    /**
     * Gets the value of the specialBorrowerEmployerRelationshipTypeOtherDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialBorrowerEmployerRelationshipTypeOtherDescription() {
        return specialBorrowerEmployerRelationshipTypeOtherDescription;
    }

    /**
     * Sets the value of the specialBorrowerEmployerRelationshipTypeOtherDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialBorrowerEmployerRelationshipTypeOtherDescription(String value) {
        this.specialBorrowerEmployerRelationshipTypeOtherDescription = value;
    }

    /**
     * Gets the value of the employedAbroadIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployedAbroadIndicator() {
        return employedAbroadIndicator;
    }

    /**
     * Sets the value of the employedAbroadIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployedAbroadIndicator(String value) {
        this.employedAbroadIndicator = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

}
