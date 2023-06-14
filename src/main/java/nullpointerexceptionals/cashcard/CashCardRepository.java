package nullpointerexceptionals.cashcard;

import org.springframework.data.repository.CrudRepository;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {

    // update below to 'name' or 'user' or whatever we use for card owner/user
    boolean existsById(Long id);
    
    

}
