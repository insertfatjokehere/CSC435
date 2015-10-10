
package calculationwa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the calculationwa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Multiplcation_QNAME = new QName("http://CalculationWS/", "Multiplcation");
    private final static QName _SubtractionResponse_QNAME = new QName("http://CalculationWS/", "SubtractionResponse");
    private final static QName _AdditionResponse_QNAME = new QName("http://CalculationWS/", "AdditionResponse");
    private final static QName _Subtraction_QNAME = new QName("http://CalculationWS/", "Subtraction");
    private final static QName _Addition_QNAME = new QName("http://CalculationWS/", "Addition");
    private final static QName _MultiplcationResponse_QNAME = new QName("http://CalculationWS/", "MultiplcationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: calculationwa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AdditionResponse }
     * 
     */
    public AdditionResponse createAdditionResponse() {
        return new AdditionResponse();
    }

    /**
     * Create an instance of {@link Addition }
     * 
     */
    public Addition createAddition() {
        return new Addition();
    }

    /**
     * Create an instance of {@link MultiplcationResponse }
     * 
     */
    public MultiplcationResponse createMultiplcationResponse() {
        return new MultiplcationResponse();
    }

    /**
     * Create an instance of {@link Subtraction }
     * 
     */
    public Subtraction createSubtraction() {
        return new Subtraction();
    }

    /**
     * Create an instance of {@link SubtractionResponse }
     * 
     */
    public SubtractionResponse createSubtractionResponse() {
        return new SubtractionResponse();
    }

    /**
     * Create an instance of {@link Multiplcation }
     * 
     */
    public Multiplcation createMultiplcation() {
        return new Multiplcation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Multiplcation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculationWS/", name = "Multiplcation")
    public JAXBElement<Multiplcation> createMultiplcation(Multiplcation value) {
        return new JAXBElement<Multiplcation>(_Multiplcation_QNAME, Multiplcation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubtractionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculationWS/", name = "SubtractionResponse")
    public JAXBElement<SubtractionResponse> createSubtractionResponse(SubtractionResponse value) {
        return new JAXBElement<SubtractionResponse>(_SubtractionResponse_QNAME, SubtractionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdditionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculationWS/", name = "AdditionResponse")
    public JAXBElement<AdditionResponse> createAdditionResponse(AdditionResponse value) {
        return new JAXBElement<AdditionResponse>(_AdditionResponse_QNAME, AdditionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Subtraction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculationWS/", name = "Subtraction")
    public JAXBElement<Subtraction> createSubtraction(Subtraction value) {
        return new JAXBElement<Subtraction>(_Subtraction_QNAME, Subtraction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Addition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculationWS/", name = "Addition")
    public JAXBElement<Addition> createAddition(Addition value) {
        return new JAXBElement<Addition>(_Addition_QNAME, Addition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiplcationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://CalculationWS/", name = "MultiplcationResponse")
    public JAXBElement<MultiplcationResponse> createMultiplcationResponse(MultiplcationResponse value) {
        return new JAXBElement<MultiplcationResponse>(_MultiplcationResponse_QNAME, MultiplcationResponse.class, null, value);
    }

}
