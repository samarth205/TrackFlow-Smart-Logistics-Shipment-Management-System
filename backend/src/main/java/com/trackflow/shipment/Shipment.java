package com.trackflow.shipment;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="shipments")
public class Shipment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String trackingNumber;
 @Column(nullable=false) private String recipient;
 @Column(nullable=false) private String origin;
 @Column(nullable=false) private String destination;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private ShipmentStatus status=ShipmentStatus.PENDING;
 private String carrier; private String estimatedDelivery; private Instant updatedAt=Instant.now();
 protected Shipment() {}
 public Shipment(String trackingNumber,String recipient,String origin,String destination,String carrier,String estimatedDelivery){this.trackingNumber=trackingNumber;this.recipient=recipient;this.origin=origin;this.destination=destination;this.carrier=carrier;this.estimatedDelivery=estimatedDelivery;}
 public void update(String recipient,String origin,String destination,String carrier,String estimatedDelivery,ShipmentStatus status){this.recipient=recipient;this.origin=origin;this.destination=destination;this.carrier=carrier;this.estimatedDelivery=estimatedDelivery;this.status=status;this.updatedAt=Instant.now();}
 public Long getId(){return id;} public String getTrackingNumber(){return trackingNumber;} public String getRecipient(){return recipient;} public String getOrigin(){return origin;} public String getDestination(){return destination;} public ShipmentStatus getStatus(){return status;} public String getCarrier(){return carrier;} public String getEstimatedDelivery(){return estimatedDelivery;} public Instant getUpdatedAt(){return updatedAt;}
}
