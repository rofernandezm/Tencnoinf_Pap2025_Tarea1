package logic.controller;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import exceptions.RepeatedTouristOutingException;
import exceptions.TouristOutingDoesNotExistException;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtTouristOuting;
import logic.entity.Inscription;
import logic.entity.TouristOuting;
import logic.handler.TouristActivityHandler;
import logic.handler.TouristOutingAndInscrptionHandler;
import logic.interfaces.ITouristOutingAndInscriptionController;
import exceptions.RepeatedInscriptionToTouristOutingException;

public class TouristOutingAndInscriptionController implements ITouristOutingAndInscriptionController {

	private String outingName;

	public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException {

		TouristOutingAndInscrptionHandler toih = TouristOutingAndInscrptionHandler.getIntance();
		TouristOuting to = toih.getTouristOutingByName(outingName);
		if (to != null)
			return new DtTouristOuting(to.getOutingName(), to.getMaxNumTourists(), to.getDeparturePoint(),
					to.getDepartureDate(), to.getDischargeDate(), to.getActivity().getActivityName());
		else
			throw new TouristOutingDoesNotExistException("La salida turistica de nombre " + outingName + " no existe");
	}

	public void outingDataEntry(DtTouristOuting dtTouristOuting) throws RepeatedTouristOutingException {

		TouristOutingAndInscrptionHandler toaih = TouristOutingAndInscrptionHandler.getIntance();
		if (toaih.existOutingName(dtTouristOuting.getOutingName())) {
			throw new RepeatedTouristOutingException("Ya existe una salida turistica con ese nombre.");
		}

		TouristOuting newTouristOuting = new TouristOuting(dtTouristOuting);
		newTouristOuting.setActivity(
				TouristActivityHandler.getIntance().getTouristActivityByName(dtTouristOuting.getActivityName()));

		toaih.addTouristOuting(newTouristOuting);
	}

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname,
			String outingName) throws RepeatedInscriptionToTouristOutingException {

		TouristOutingAndInscrptionHandler mto = TouristOutingAndInscrptionHandler.getIntance();
		Boolean inscription = mto.existInscription(userNickname, outingName);

		if (inscription)
			throw new RepeatedInscriptionToTouristOutingException(
					"La inscripcion para este usuario en esta salida ya esta registrada. Por favor, ingrese un nuevo nombre");

		Inscription inscriptionToAdd = new Inscription(dtInscriptionOuting);
		TouristOuting to = mto.getTouristOutingByName(outingName);

		mto.addInscription(userNickname, to, inscriptionToAdd);

	}

	public DtInscriptionTouristOuting[] listOutingInscription(String outingName) {
		Set<Inscription> inscriptionsSet = TouristOutingAndInscrptionHandler.getIntance()
				.getInscriptionsByTouristOuting(outingName);

		if (inscriptionsSet == null || inscriptionsSet.isEmpty()) {
			return new DtInscriptionTouristOuting[0];
		}

		DtInscriptionTouristOuting[] result = new DtInscriptionTouristOuting[inscriptionsSet.size()];

		int i = 0;

		for (Inscription ins : inscriptionsSet) {
			DtTouristOuting dtoOuting = ins.getTouristOuting().getDtTouristOuting();
			result[i++] = new DtInscriptionTouristOuting(ins.getNumTourists(), ins.getTotalRegistrationCost(),
					ins.getInscriptionDate(), dtoOuting);
		}

		return result;
	}

	public void modifyOutingName(String outingName) {
	}

	public void modifyMaxTourist(int maxTourist) {
	}

	public void modifyExitPoint(String exitPoint) {
	}

	public void modifydateTime(LocalDateTime dateTime) {
	}

	public String getOutingName() {
		return outingName;
	}

	public String[] listTouristOutings() throws TouristOutingDoesNotExistException {

		String[] rtn = TouristOutingAndInscrptionHandler.getIntance().listTouristOutings();

		if (rtn == null)
			throw new TouristOutingDoesNotExistException("No existen salidas turisticas registradas.");

		return rtn;

	}

	public float inscriptionTotalCost(float touristActivityCost, int numTourists) {
		return touristActivityCost * numTourists;
	}

}
