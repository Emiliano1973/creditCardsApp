package ie.myinfinity.demo.creditcard.dao;

import ie.myinfinity.demo.creditcard.dtos.CreditCardDto;
import ie.myinfinity.demo.creditcard.dtos.CreditCardRequestDto;

import java.util.List;

public interface CreditCardDao {
    List<CreditCardDto> findCreditCards(CreditCardRequestDto creditCardRequestDto);

    /**
     * Return the creditcard by card code
     *
     * @return
     */
    CreditCardDto findCreditCardById(long id);




}
