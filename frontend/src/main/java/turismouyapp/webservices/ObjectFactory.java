
package turismouyapp.webservices;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the turismouyapp.webservices package. 
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

    private final static QName _DtInscriptionTouristOuting_QNAME = new QName("http://ws.turismouyapp/schema", "DtInscriptionTouristOuting");
    private final static QName _DtTouristOuting_QNAME = new QName("http://ws.turismouyapp/schema", "DtTouristOuting");
    private final static QName _RepeatedInscriptionToTouristOutingFault_QNAME = new QName("http://ws.turismouyapp/schema", "RepeatedInscriptionToTouristOutingFault");
    private final static QName _RepeatedTouristOutingFault_QNAME = new QName("http://ws.turismouyapp/schema", "RepeatedTouristOutingFault");
    private final static QName _TouristOutingDoesNotExistFault_QNAME = new QName("http://ws.turismouyapp/schema", "TouristOutingDoesNotExistFault");
    private final static QName _ConsultTouristOutingData_QNAME = new QName("http://ws.turismouyapp/schema", "consultTouristOutingData");
    private final static QName _ConsultTouristOutingDataResponse_QNAME = new QName("http://ws.turismouyapp/schema", "consultTouristOutingDataResponse");
    private final static QName _InscriptionDataEntry_QNAME = new QName("http://ws.turismouyapp/schema", "inscriptionDataEntry");
    private final static QName _InscriptionDataEntryResponse_QNAME = new QName("http://ws.turismouyapp/schema", "inscriptionDataEntryResponse");
    private final static QName _ListDtInscriptionTouristOutingByTouristNickname_QNAME = new QName("http://ws.turismouyapp/schema", "listDtInscriptionTouristOutingByTouristNickname");
    private final static QName _ListDtInscriptionTouristOutingByTouristNicknameResponse_QNAME = new QName("http://ws.turismouyapp/schema", "listDtInscriptionTouristOutingByTouristNicknameResponse");
    private final static QName _ListOutingInscription_QNAME = new QName("http://ws.turismouyapp/schema", "listOutingInscription");
    private final static QName _ListOutingInscriptionResponse_QNAME = new QName("http://ws.turismouyapp/schema", "listOutingInscriptionResponse");
    private final static QName _OutingDataEntry_QNAME = new QName("http://ws.turismouyapp/schema", "outingDataEntry");
    private final static QName _OutingDataEntryResponse_QNAME = new QName("http://ws.turismouyapp/schema", "outingDataEntryResponse");
    private final static QName _UpdateOutingImageName_QNAME = new QName("http://ws.turismouyapp/schema", "updateOutingImageName");
    private final static QName _UpdateOutingImageNameResponse_QNAME = new QName("http://ws.turismouyapp/schema", "updateOutingImageNameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: turismouyapp.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DtInscriptionTouristOuting }
     * 
     */
    public DtInscriptionTouristOuting createDtInscriptionTouristOuting() {
        return new DtInscriptionTouristOuting();
    }

    /**
     * Create an instance of {@link DtTouristOuting }
     * 
     */
    public DtTouristOuting createDtTouristOuting() {
        return new DtTouristOuting();
    }

    /**
     * Create an instance of {@link RepeatedInscriptionToTouristOutingFault }
     * 
     */
    public RepeatedInscriptionToTouristOutingFault createRepeatedInscriptionToTouristOutingFault() {
        return new RepeatedInscriptionToTouristOutingFault();
    }

    /**
     * Create an instance of {@link RepeatedTouristOutingFault }
     * 
     */
    public RepeatedTouristOutingFault createRepeatedTouristOutingFault() {
        return new RepeatedTouristOutingFault();
    }

    /**
     * Create an instance of {@link TouristOutingDoesNotExistFault }
     * 
     */
    public TouristOutingDoesNotExistFault createTouristOutingDoesNotExistFault() {
        return new TouristOutingDoesNotExistFault();
    }

    /**
     * Create an instance of {@link ConsultTouristOutingData }
     * 
     */
    public ConsultTouristOutingData createConsultTouristOutingData() {
        return new ConsultTouristOutingData();
    }

    /**
     * Create an instance of {@link ConsultTouristOutingDataResponse }
     * 
     */
    public ConsultTouristOutingDataResponse createConsultTouristOutingDataResponse() {
        return new ConsultTouristOutingDataResponse();
    }

    /**
     * Create an instance of {@link InscriptionDataEntry }
     * 
     */
    public InscriptionDataEntry createInscriptionDataEntry() {
        return new InscriptionDataEntry();
    }

    /**
     * Create an instance of {@link InscriptionDataEntryResponse }
     * 
     */
    public InscriptionDataEntryResponse createInscriptionDataEntryResponse() {
        return new InscriptionDataEntryResponse();
    }

    /**
     * Create an instance of {@link ListDtInscriptionTouristOutingByTouristNickname }
     * 
     */
    public ListDtInscriptionTouristOutingByTouristNickname createListDtInscriptionTouristOutingByTouristNickname() {
        return new ListDtInscriptionTouristOutingByTouristNickname();
    }

    /**
     * Create an instance of {@link ListDtInscriptionTouristOutingByTouristNicknameResponse }
     * 
     */
    public ListDtInscriptionTouristOutingByTouristNicknameResponse createListDtInscriptionTouristOutingByTouristNicknameResponse() {
        return new ListDtInscriptionTouristOutingByTouristNicknameResponse();
    }

    /**
     * Create an instance of {@link ListOutingInscription }
     * 
     */
    public ListOutingInscription createListOutingInscription() {
        return new ListOutingInscription();
    }

    /**
     * Create an instance of {@link ListOutingInscriptionResponse }
     * 
     */
    public ListOutingInscriptionResponse createListOutingInscriptionResponse() {
        return new ListOutingInscriptionResponse();
    }

    /**
     * Create an instance of {@link OutingDataEntry }
     * 
     */
    public OutingDataEntry createOutingDataEntry() {
        return new OutingDataEntry();
    }

    /**
     * Create an instance of {@link OutingDataEntryResponse }
     * 
     */
    public OutingDataEntryResponse createOutingDataEntryResponse() {
        return new OutingDataEntryResponse();
    }

    /**
     * Create an instance of {@link UpdateOutingImageName }
     * 
     */
    public UpdateOutingImageName createUpdateOutingImageName() {
        return new UpdateOutingImageName();
    }

    /**
     * Create an instance of {@link UpdateOutingImageNameResponse }
     * 
     */
    public UpdateOutingImageNameResponse createUpdateOutingImageNameResponse() {
        return new UpdateOutingImageNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtInscriptionTouristOuting }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtInscriptionTouristOuting }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "DtInscriptionTouristOuting")
    public JAXBElement<DtInscriptionTouristOuting> createDtInscriptionTouristOuting(DtInscriptionTouristOuting value) {
        return new JAXBElement<DtInscriptionTouristOuting>(_DtInscriptionTouristOuting_QNAME, DtInscriptionTouristOuting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtTouristOuting }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtTouristOuting }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "DtTouristOuting")
    public JAXBElement<DtTouristOuting> createDtTouristOuting(DtTouristOuting value) {
        return new JAXBElement<DtTouristOuting>(_DtTouristOuting_QNAME, DtTouristOuting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RepeatedInscriptionToTouristOutingFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RepeatedInscriptionToTouristOutingFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "RepeatedInscriptionToTouristOutingFault")
    public JAXBElement<RepeatedInscriptionToTouristOutingFault> createRepeatedInscriptionToTouristOutingFault(RepeatedInscriptionToTouristOutingFault value) {
        return new JAXBElement<RepeatedInscriptionToTouristOutingFault>(_RepeatedInscriptionToTouristOutingFault_QNAME, RepeatedInscriptionToTouristOutingFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RepeatedTouristOutingFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RepeatedTouristOutingFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "RepeatedTouristOutingFault")
    public JAXBElement<RepeatedTouristOutingFault> createRepeatedTouristOutingFault(RepeatedTouristOutingFault value) {
        return new JAXBElement<RepeatedTouristOutingFault>(_RepeatedTouristOutingFault_QNAME, RepeatedTouristOutingFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TouristOutingDoesNotExistFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TouristOutingDoesNotExistFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "TouristOutingDoesNotExistFault")
    public JAXBElement<TouristOutingDoesNotExistFault> createTouristOutingDoesNotExistFault(TouristOutingDoesNotExistFault value) {
        return new JAXBElement<TouristOutingDoesNotExistFault>(_TouristOutingDoesNotExistFault_QNAME, TouristOutingDoesNotExistFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultTouristOutingData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConsultTouristOutingData }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "consultTouristOutingData")
    public JAXBElement<ConsultTouristOutingData> createConsultTouristOutingData(ConsultTouristOutingData value) {
        return new JAXBElement<ConsultTouristOutingData>(_ConsultTouristOutingData_QNAME, ConsultTouristOutingData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultTouristOutingDataResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConsultTouristOutingDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "consultTouristOutingDataResponse")
    public JAXBElement<ConsultTouristOutingDataResponse> createConsultTouristOutingDataResponse(ConsultTouristOutingDataResponse value) {
        return new JAXBElement<ConsultTouristOutingDataResponse>(_ConsultTouristOutingDataResponse_QNAME, ConsultTouristOutingDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InscriptionDataEntry }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InscriptionDataEntry }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "inscriptionDataEntry")
    public JAXBElement<InscriptionDataEntry> createInscriptionDataEntry(InscriptionDataEntry value) {
        return new JAXBElement<InscriptionDataEntry>(_InscriptionDataEntry_QNAME, InscriptionDataEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InscriptionDataEntryResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InscriptionDataEntryResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "inscriptionDataEntryResponse")
    public JAXBElement<InscriptionDataEntryResponse> createInscriptionDataEntryResponse(InscriptionDataEntryResponse value) {
        return new JAXBElement<InscriptionDataEntryResponse>(_InscriptionDataEntryResponse_QNAME, InscriptionDataEntryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListDtInscriptionTouristOutingByTouristNickname }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListDtInscriptionTouristOutingByTouristNickname }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "listDtInscriptionTouristOutingByTouristNickname")
    public JAXBElement<ListDtInscriptionTouristOutingByTouristNickname> createListDtInscriptionTouristOutingByTouristNickname(ListDtInscriptionTouristOutingByTouristNickname value) {
        return new JAXBElement<ListDtInscriptionTouristOutingByTouristNickname>(_ListDtInscriptionTouristOutingByTouristNickname_QNAME, ListDtInscriptionTouristOutingByTouristNickname.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListDtInscriptionTouristOutingByTouristNicknameResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListDtInscriptionTouristOutingByTouristNicknameResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "listDtInscriptionTouristOutingByTouristNicknameResponse")
    public JAXBElement<ListDtInscriptionTouristOutingByTouristNicknameResponse> createListDtInscriptionTouristOutingByTouristNicknameResponse(ListDtInscriptionTouristOutingByTouristNicknameResponse value) {
        return new JAXBElement<ListDtInscriptionTouristOutingByTouristNicknameResponse>(_ListDtInscriptionTouristOutingByTouristNicknameResponse_QNAME, ListDtInscriptionTouristOutingByTouristNicknameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOutingInscription }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListOutingInscription }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "listOutingInscription")
    public JAXBElement<ListOutingInscription> createListOutingInscription(ListOutingInscription value) {
        return new JAXBElement<ListOutingInscription>(_ListOutingInscription_QNAME, ListOutingInscription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOutingInscriptionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListOutingInscriptionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "listOutingInscriptionResponse")
    public JAXBElement<ListOutingInscriptionResponse> createListOutingInscriptionResponse(ListOutingInscriptionResponse value) {
        return new JAXBElement<ListOutingInscriptionResponse>(_ListOutingInscriptionResponse_QNAME, ListOutingInscriptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutingDataEntry }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OutingDataEntry }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "outingDataEntry")
    public JAXBElement<OutingDataEntry> createOutingDataEntry(OutingDataEntry value) {
        return new JAXBElement<OutingDataEntry>(_OutingDataEntry_QNAME, OutingDataEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutingDataEntryResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OutingDataEntryResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "outingDataEntryResponse")
    public JAXBElement<OutingDataEntryResponse> createOutingDataEntryResponse(OutingDataEntryResponse value) {
        return new JAXBElement<OutingDataEntryResponse>(_OutingDataEntryResponse_QNAME, OutingDataEntryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOutingImageName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateOutingImageName }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "updateOutingImageName")
    public JAXBElement<UpdateOutingImageName> createUpdateOutingImageName(UpdateOutingImageName value) {
        return new JAXBElement<UpdateOutingImageName>(_UpdateOutingImageName_QNAME, UpdateOutingImageName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOutingImageNameResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateOutingImageNameResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.turismouyapp/schema", name = "updateOutingImageNameResponse")
    public JAXBElement<UpdateOutingImageNameResponse> createUpdateOutingImageNameResponse(UpdateOutingImageNameResponse value) {
        return new JAXBElement<UpdateOutingImageNameResponse>(_UpdateOutingImageNameResponse_QNAME, UpdateOutingImageNameResponse.class, null, value);
    }

}
