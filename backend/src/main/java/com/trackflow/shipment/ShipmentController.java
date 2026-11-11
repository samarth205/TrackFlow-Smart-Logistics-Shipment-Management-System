package com.trackflow.shipment;
import jakarta.validation.Valid; import jakarta.validation.constraints.NotBlank; import org.springframework.http.HttpStatus; import org.springframework.messaging.simp.SimpMessagingTemplate; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/shipments") public class ShipmentController {
 private final ShipmentRepository repo; private final SimpMessagingTemplate socket;
 public ShipmentController(ShipmentRepository repo,SimpMessagingTemplate socket){this.repo=repo;this.socket=socket;}
 public record ShipmentInput(@NotBlank String recipient,@NotBlank String origin,@NotBlank String destination,String carrier,String estimatedDelivery,ShipmentStatus status){}
 @GetMapping public List<Shipment> list(@RequestParam(required=false) String q){return q==null||q.isBlank()?repo.findAll():repo.search(q);}
 @GetMapping("/summary") public Map<String,Long> summary(){return Map.of("total",repo.count(),"inTransit",repo.countByStatus(ShipmentStatus.IN_TRANSIT),"delivered",repo.countByStatus(ShipmentStatus.DELIVERED),"delayed",repo.countByStatus(ShipmentStatus.DELAYED));}
 @GetMapping("/{id}") public Shipment get(@PathVariable Long id){return find(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Shipment create(@Valid @RequestBody ShipmentInput x){Shipment s=repo.save(new Shipment("TF-"+UUID.randomUUID().toString().substring(0,8).toUpperCase(),x.recipient(),x.origin(),x.destination(),x.carrier(),x.estimatedDelivery()));socket.convertAndSend("/topic/shipments",s);return s;}
 @PutMapping("/{id}") public Shipment update(@PathVariable Long id,@Valid @RequestBody ShipmentInput x){Shipment s=find(id);s.update(x.recipient(),x.origin(),x.destination(),x.carrier(),x.estimatedDelivery(),x.status()==null?s.getStatus():x.status());s=repo.save(s);socket.convertAndSend("/topic/shipments",s);return s;}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repo.delete(find(id));}
 private Shipment find(Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Shipment not found"));}
}
