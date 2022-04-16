package com.tw.precharge.repository;

import com.tw.precharge.entity.Refundment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lexu
 */
public interface RefundmentRepository extends JpaRepository <Refundment, Integer> {

    Refundment getRefundmentById(Integer refId);

    List<Refundment> getRefundmentByCid(Integer cid);

}
