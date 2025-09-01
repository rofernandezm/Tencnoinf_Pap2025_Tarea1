package logic.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


import exceptions.ActivityDoesNotExistException;
import exceptions.RepeatedTouristOutingException;
import exceptions.TouristOutingDoesNotExistException;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.dto.DtUser;
import logic.entity.Inscription;
import logic.entity.TouristOuting;
import logic.handler.TouristActivityHandler;
import logic.handler.TouristOutingAndInscrptionHandler;
import logic.interfaces.ITouristOutingAndInscriptionController;

public class TouristOutingAndInscriptionController implements ITouristOutingAndInscriptionController {
	
	private String outingName;
	private DtTouristOuting dtNewTouristOuting;
	
	public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException{
		
		TouristOutingAndInscrptionHandler toih = TouristOutingAndInscrptionHandler.getIntance();
        TouristOuting to = toih.getTouristOutingByName(outingName);                    
        if (to != null) 
            return new DtTouristOuting(to.getOutingName() , to.getMaxNumTourists(), to.getDeparturePoint(), to.getDepartureDate(), to.getDischargeDate());
        else
            throw new TouristOutingDoesNotExistException("La salida turistica de nombre " + outingName + " no existe");
	}
	
	public void outingDataEntry(DtTouristOuting dtTouristOuting, String activityName) throws RepeatedTouristOutingException {

 		this.dtNewTouristOuting = dtTouristOuting;
 		
 		TouristOutingAndInscrptionHandler mto = TouristOutingAndInscrptionHandler.getIntance();
		String tourisOutingName = dtTouristOuting.getTipName();
        TouristOuting to = mto.getTouristOutingByName(tourisOutingName);
        
        if (to != null)
            throw new RepeatedTouristOutingException("La salida de nombre " + tourisOutingName + " ya esta registrada. Por favor, ingrese un nuevo nombre");
		
        int maxNumTourists = dtTouristOuting.getMaxNumTourists();
        String departurePoint = dtTouristOuting.getDeparturePoint();
        LocalDateTime departureDate = dtTouristOuting.getDepartureDate();
        LocalDate dischargeDate = dtTouristOuting.getDischargeDate();
        
        to = new TouristOuting(tourisOutingName, maxNumTourists, departurePoint, departureDate, dischargeDate);
        mto.addTouristOuting(to);
	}

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname, String outingName) {
	}
	
	public void cancelOutingRegistration() {	
	}

//	public void confirmOutingRegistration() {
//	} NOT NEDEED
	
	public DtInscriptionTouristOuting[] listOutingInscription(String outingName) {
		  Set<Inscription> inscriptionsSet = TouristOutingAndInscrptionHandler.getIntance().getInscriptionsByTouristOuting(outingName);

			    if (inscriptionsSet == null || inscriptionsSet.isEmpty()) {
			        return new DtInscriptionTouristOuting[0];
			    }

			    DtInscriptionTouristOuting[] result = new DtInscriptionTouristOuting[inscriptionsSet.size()];
			   
			    int i = 0;

			    for (Inscription ins : inscriptionsSet) {
			        DtTouristOuting dtoOuting = ins.getTouristOuting().toDT();
			        result[i++] = new DtInscriptionTouristOuting(
			            ins.getNumTourists(),
			            ins.getTotalRegistrationCost(),
			            ins.getInscriptionDate(),
			            dtoOuting
			        );
			    }

			    return result;
	}
	
	public void modifyOutingName (String outingName) {
	}
	
	public void modifyMaxTourist(int maxTourist) {	
	}
	
	public void modifyExitPoint (String exitPoint) {	
	}
	
	public void modifydateTime (LocalDateTime dateTime) {	
	}
	
	public String getOutingName() {
		return outingName;
	}
	
	public String[] listTouristOutings() throws TouristOutingDoesNotExistException{
		
		String[] rtn = TouristOutingAndInscrptionHandler.getIntance().listTouristOutings();
		
		if(rtn == null) throw new TouristOutingDoesNotExistException("No existen salidas turisticas registradas.");
		
		return rtn;
	
	}

}
