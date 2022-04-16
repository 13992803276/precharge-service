package com.tw.precharge.repository;

import com.tw.precharge.entity.Chargement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lexu
 */
public interface ChargementRepository extends JpaRepository<Chargement, Integer> {

    Chargement getChargementById(Integer chargeId);

    List<Chargement> getChargementByCid(Integer cid);

}
