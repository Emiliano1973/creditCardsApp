package ie.myinfinity.demo.creditcard.dao.repositories;

import ie.myinfinity.demo.creditcard.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {


@Query("select count(cd.id)  from CreditCard cd where cd.cardCode =:cardCode and cd.id<>:id")
    long countCardCodeIdNotEquals(@Param("cardCode") String cardCode, @Param("id") long id);

    Optional<CreditCard> findByCardCode(String cardCode);

    long countByCardCode(String cardCode);

}
