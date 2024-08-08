package com.michelesalvucci.prenations.web.rest;
// import com.michelesalvucci.nations.broker.KafkaProducer;
// import com.michelesalvucci.nations.service.NationService;
// import com.michelesalvucci.nations.service.dto.NationDTO;
// import java.util.Optional;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// @RestController
// @RequestMapping("/api/pre-nations-kafka")
// @Slf4j
// public class PNKafkaResource {
//     private final KafkaProducer kafkaProducer;
//     private final NationService nationService;
//     public PNKafkaResource(KafkaProducer kafkaProducer, NationService nationService) {
//         this.kafkaProducer = kafkaProducer;
//         this.nationService = nationService;
//     }
//     @PostMapping("/send-nation")
//     public ResponseEntity<String> publish(@RequestParam("nation") String nationName) {
//         log.debug("REST request the nation : {} to send to Kafka topic ", nationName);
//         Optional<NationDTO> nationOpt = nationService.findOne(nationName);
//         if (nationOpt.isPresent()) {
//             kafkaProducer.sendNation(nationOpt.get());
//             return ResponseEntity.ok().body("Nation sent");
//         }
//         return ResponseEntity.badRequest().body("No nation with name " + nationName);
//     }
//     // private static final String PRODUCER_BINDING_NAME = "binding-out-0";
//     // private static final Logger log = LoggerFactory.getLogger(PNKafkaResource.class);
//     // private final KafkaConsumer kafkaConsumer;
//     // private final StreamBridge streamBridge;
//     // public PNKafkaResource(StreamBridge streamBridge, KafkaConsumer kafkaConsumer) {
//     //     this.streamBridge = streamBridge;
//     //     this.kafkaConsumer = kafkaConsumer;
//     // }
//     // @PostMapping("/publish")
//     // public void publish(@RequestParam("message") String message) {
//     //     log.debug("REST request the message : {} to send to Kafka topic ", message);
//     //     streamBridge.send(PRODUCER_BINDING_NAME, message);
//     // }
//     // @GetMapping("/register")
//     // public ResponseBodyEmitter register(Principal principal) {
//     //     return kafkaConsumer.register(principal.getName());
//     // }
//     // @GetMapping("/unregister")
//     // public void unregister(Principal principal) {
//     //     kafkaConsumer.unregister(principal.getName());
//     // }
// }
