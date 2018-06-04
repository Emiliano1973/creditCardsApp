package ie.myinfinity.demo.creditcard.services.impl;

import ie.myinfinity.demo.creditcard.dao.CreditCardDao;
import ie.myinfinity.demo.creditcard.dao.repositories.CreditCardRepository;
import ie.myinfinity.demo.creditcard.dao.repositories.UserRepository;
import ie.myinfinity.demo.creditcard.dtos.CreditCardDto;
import ie.myinfinity.demo.creditcard.dtos.CreditCardRequestDto;
import ie.myinfinity.demo.creditcard.entities.CreditCard;
import ie.myinfinity.demo.creditcard.exceptions.CreditCardException;
import ie.myinfinity.demo.creditcard.services.CreditCardService;
import ie.myinfinity.demo.creditcard.utils.CreditCardsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service("creditCardService")
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("creditCardDao")
    private CreditCardDao creditCardDao;


    /**
     * iNSERT A NEW card
     * @param creditCardDto
     * @param username
     * @throws CreditCardException
     */
    @Override
    @Transactional
    public void save(CreditCardDto creditCardDto, String username)  throws CreditCardException {
        CreditCard creditCard=null;
        // CHECK IF cARD CORDE IS VALID
        if (!CreditCardsUtils.isVCalidateCreditCardNumber(creditCardDto.getCardCode())){
            throw  new CreditCardException("Credit card "+creditCardDto.getCardCode()+" is not valid");
        }
       //check the credit card is already present
        long count=creditCardRepository.countByCardCode(creditCardDto.getCardCode());
        if (count>0){
            throw  new CreditCardException("Credit card "+creditCardDto.getCardCode()+" is already present");
        }
        creditCard=new CreditCard();
        creditCard.setCardCode(creditCardDto.getCardCode());
        creditCard.setUser(this.userRepository.findByUsernameIgnoreCase(username).get());

        creditCard.setName(creditCardDto.getName());
        int expiryMonth=creditCardDto.getExpiryMonth();
        int expiryYear=creditCardDto.getExpiryYear();
        LocalDate expiryDate=LocalDate.now().withMonth(expiryMonth).withYear(expiryYear).with(TemporalAdjusters.lastDayOfMonth());;
        creditCard.setExpiryDate(expiryDate);
        this.creditCardRepository.save(creditCard);

    }


    /**
     * update the card
     * @param creditCardDto
     * @throws CreditCardException
     */
    @Override
    @Transactional
    public void update(CreditCardDto creditCardDto) throws CreditCardException {
        if (!CreditCardsUtils.isVCalidateCreditCardNumber(creditCardDto.getCardCode())){
            throw  new CreditCardException("Credit card "+creditCardDto.getCardCode()+" is not valid");
        }
        /// /Check if other record have the new credit card code
        long countCreditCardNotSameId=creditCardRepository.countCardCodeIdNotEquals(creditCardDto.getCardCode(), creditCardDto.getId());

        if(countCreditCardNotSameId>0){
            throw  new CreditCardException("Credit card "+creditCardDto.getCardCode()+" is already present in another record");

        }
        CreditCard creditCard=creditCardRepository.findById(creditCardDto.getId()).orElseThrow(()->new CreditCardException("Credit card not present"));


        creditCard.setCardCode(creditCardDto.getCardCode());
        creditCard.setName(creditCardDto.getName());
        int expiryMonth=creditCardDto.getExpiryMonth();
        int expiryYear=creditCardDto.getExpiryYear();
        LocalDate expiryDate=LocalDate.now().withMonth(expiryMonth).withYear(expiryYear).with(TemporalAdjusters.lastDayOfMonth());;
        creditCard.setExpiryDate(expiryDate);

    }


    /*
    get a list of cards by code (search like)
     */
    @Override
    @Transactional
    public List<CreditCardDto> getCreditCards(CreditCardRequestDto creditCardRequestDto) {
        return this.creditCardDao.findCreditCards(creditCardRequestDto);
    }

    @Override
    public CreditCardDto getCreditCardById(long id) {
        return this.creditCardDao.findCreditCardById(id);
    }


    @Override
    @Transactional
    public void deleteCreditCard(long id) {
        this.creditCardRepository.deleteById(id);
    }


}
