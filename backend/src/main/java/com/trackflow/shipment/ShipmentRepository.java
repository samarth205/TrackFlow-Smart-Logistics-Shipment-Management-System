package com.trackflow.shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface ShipmentRepository extends JpaRepository<Shipment,Long>{
 @Query("select s from Shipment s where lower(s.trackingNumber) like lower(concat('%',:q,'%')) or lower(s.recipient) like lower(concat('%',:q,'%')) or lower(s.destination) like lower(concat('%',:q,'%'))") List<Shipment> search(String q);
 long countByStatus(ShipmentStatus status);
}
