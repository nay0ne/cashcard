package nullpointerexceptionals.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    
    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        CashCard cashCard = findCashCard(requestedId);
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
   
      }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
            .path("cashcards/{id}")
            .buildAndExpand(savedCashCard.id())
            .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();

    }

    @GetMapping()
    public ResponseEntity<Iterable<CashCard>> findAll() {
        return ResponseEntity.ok(cashCardRepository.findAll());
    
    }


    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate) {
        CashCard cashCard = findCashCard(requestedId);
        if (cashCard != null) {
            CashCard updatedCashCard = new CashCard(requestedId, cashCardUpdate.amount(),
            cashCard.owner());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.notFound().build();
    }

    private CashCard findCashCard(Long requestedId) {
        return cashCardRepository.findById(requestedId).orElse(null);
   
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id) {
        CashCard cashCard = findCashCard(id);
        if (cashCard != null) {
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
   
   }

    
}

