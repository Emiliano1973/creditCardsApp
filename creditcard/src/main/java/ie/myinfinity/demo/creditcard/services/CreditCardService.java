package ie.myinfinity.demo.creditcard.services;

import ie.myinfinity.demo.creditcard.dtos.CreditCardDto;
import ie.myinfinity.demo.creditcard.dtos.CreditCardRequestDto;
import ie.myinfinity.demo.creditcard.exceptions.CreditCardException;

import java.util.List;

public interface CreditCardService {


    void save(CreditCardDto creditCardDto, String username) throws CreditCardException;

    void update(CreditCardDto creditCardDto) throws CreditCardException;

    void deleteCreditCard(long id);

    List<CreditCardDto> getCreditCards(CreditCardRequestDto creditCardRequestDto);

    CreditCardDto getCreditCardById(long id);


}

