package ServerTurismouy;

import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException;
import turismouyapp.core.exceptions.RepeatedTouristOutingException;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServices {
    
    private Endpoint endpoint = null;
    
    //Constructor
    public WebServices(){}

    //Operaciones que quiero publicar

    @WebMethod(exclude = true)
    public void publicar(){
        endpoint = Endpoint.publish("http://localhost:8080/turismo", this);
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }
    
    
    //Metodos de user
    @WebMethod
    public DtUser consultUserData(String nicknameOrEmail) {
        turismouyapp.core.interfaces.IUserController a = new turismouyapp.core.controller.UserController();
        return a.consultUserData(nicknameOrEmail);
    }
    
    @WebMethod
    public DtUser consultUserDataByEmail(String nicknameOrEmail) {
        turismouyapp.core.interfaces.IUserController a = new turismouyapp.core.controller.UserController();
        return a.consultUserDataByEmail(nicknameOrEmail);
    }
    
    @WebMethod
    public void dataEntryUser(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException{
        turismouyapp.core.interfaces.IUserController a = new turismouyapp.core.controller.UserController();
        a.dataEntry(dtUser);
    }
    
    @WebMethod
    public void confirmRegistration(){
        turismouyapp.core.interfaces.IUserController a = new turismouyapp.core.controller.UserController();
        a.confirmRegistration();
    }
    
    @WebMethod
    public void modifyUserData(DtUser dtUser){
        turismouyapp.core.interfaces.IUserController a = new turismouyapp.core.controller.UserController();
        a.modifyUserData(dtUser);
    }
    
    @WebMethod
    public String[] listUsers(){
        turismouyapp.core.interfaces.IUserController a = new turismouyapp.core.controller.UserController();
        return a.listUsers();
    }
    
  //Metodos de tourist activity
    @WebMethod
    public List<DtActivityWithOutings> listTouristActivityData() throws ActivityDoesNotExistException{
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        return a.listTouristActivityData();
    }
    
    @WebMethod
    public String[] listTouristActivities() throws ActivityDoesNotExistException{
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        return a.listTouristActivities();
    }
    
    @WebMethod
    public void activityDataEntry(DtTouristActivity dtTouristActivity) throws ActivityDoesNotExistException, RepeatedActivityNameException {
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        a.activityDataEntry(dtTouristActivity);
    }
    
    @WebMethod
    public void modifyActivity(DtTouristActivity dtTouristActivity) {
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        a.modifyActivity(dtTouristActivity);
    }
    
    @WebMethod
    public String[] listTouristActivitiesByStatus(TouristActivityStatus status) {
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        return a.listTouristActivitiesByStatus(status);
    }
    
    @WebMethod
    public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException{
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        return a.consultTouristActivityData(activityName);
    }
    
    @WebMethod
    public String[] listTouristActivitiesBySupplierNickname(String nickname) {
        turismouyapp.core.interfaces.ITouristActivityController a = new turismouyapp.core.controller.TouristActivityController();
        return a.listTouristActivitiesBySupplierNickname(nickname);
    }
    
  //Metodos de tourist outings and inscriptions
    @WebMethod
    public void outingDataEntry(DtTouristOuting dtTouristOuting) throws RepeatedTouristOutingException {
        turismouyapp.core.interfaces.ITouristOutingAndInscriptionController  a = new turismouyapp.core.controller.TouristOutingAndInscriptionController ();
        a.outingDataEntry(dtTouristOuting);
    }
    
    @WebMethod
    public void updateOutingImageName(String outingName, String imageName){
        turismouyapp.core.interfaces.ITouristOutingAndInscriptionController  a = new turismouyapp.core.controller.TouristOutingAndInscriptionController ();
        a.updateOutingImageName(outingName, imageName);
    }
    
    @WebMethod
    public DtInscriptionTouristOuting[] listOutingInscription(String outingName){
        turismouyapp.core.interfaces.ITouristOutingAndInscriptionController  a = new turismouyapp.core.controller.TouristOutingAndInscriptionController ();
        return a.listOutingInscription(outingName);
    }
    
    @WebMethod
    public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException{
        turismouyapp.core.interfaces.ITouristOutingAndInscriptionController  a = new turismouyapp.core.controller.TouristOutingAndInscriptionController ();
        return a.consultTouristOutingData(outingName);
    }
    
    @WebMethod
    public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname,
			String outingName) throws RepeatedInscriptionToTouristOutingException{
        turismouyapp.core.interfaces.ITouristOutingAndInscriptionController  a = new turismouyapp.core.controller.TouristOutingAndInscriptionController ();
        a.inscriptionDataEntry(dtInscriptionOuting, userNickname, outingName);
    }
    
}
