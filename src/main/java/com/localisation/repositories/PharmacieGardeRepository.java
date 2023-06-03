package com.localisation.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.localisation.entities.Pharmacy;
import com.localisation.entities.PharmacieGarde;

public interface PharmacieGardeRepository extends JpaRepository<PharmacieGarde, Integer> {
	public List<PharmacieGarde> findByGarde_Type(String nomGarde);
	// public List<Object[]> findByGarde_TypeAndPharmacie_Zone_Nom(String
	// periode,String nomZone);

	List<PharmacieGarde> findAllByPharmacy(Pharmacy pharmacie);

	@Query("select pg from PharmacieGarde pg where current_date() between pg.startDate and pg.endDate")
	List<PharmacieGarde> findAllPharmacieDispo();

	@Query("select pg from PharmacieGarde pg where current_date() between pg.startDate and pg.endDate and pg.garde.id=:para")
	List<PharmacieGarde> findAllPharmacieDispoByGarde(@Param("para") int para);

	List<PharmacieGarde> findAllByStartDateBeforeAndEndDateAfter(LocalDate currentDate1, LocalDate currentDate2);

	List<PharmacieGarde> findAllByGarde_IdAndStartDateBeforeAndEndDateAfter(int gardeId, LocalDate currentDate1,
			LocalDate currentDate2);

	List<PharmacieGarde> findAllByGardeType(String period);


	@Query("select distinct pg.pharmacy from PharmacieGarde pg where current_date() between pg.startDate and pg.endDate")
	List<Pharmacy> findAllPharmacieDispo2();
}
