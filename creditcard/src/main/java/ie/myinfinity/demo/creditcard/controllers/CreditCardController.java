package ie.myinfinity.demo.creditcard.controllers;


import ie.myinfinity.demo.creditcard.dtos.CreditCardDto;
import ie.myinfinity.demo.creditcard.dtos.CreditCardRequestDto;
import ie.myinfinity.demo.creditcard.dtos.ResponseDto;
import ie.myinfinity.demo.creditcard.exceptions.CreditCardException;
import ie.myinfinity.demo.creditcard.security.auth.JwtAuthenticationToken;
import ie.myinfinity.demo.creditcard.security.model.UserContext;
import ie.myinfinity.demo.creditcard.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/creditcards")
@RestController
public class CreditCardController  extends AbstractController {

    @Autowired
    @Qualifier("creditCardService")
    private CreditCardService creditCardService;

    /**
     * Return creditCards by code searchLike
     *
     * @param searchLike
     * @param principal
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCreditCards(@RequestParam(value = "searchLike", required = false) String searchLike, Principal principal) {

        UserContext userContext = ((JwtAuthenticationToken) principal).getPrincipal();
        CreditCardRequestDto creditCardRequestDto = new CreditCardRequestDto(userContext.getUsername(), userContext.getUserRole(), searchLike);
        List<CreditCardDto> creditCards = creditCardService.getCreditCards(creditCardRequestDto);
        return new ResponseEntity<List<CreditCardDto>>(creditCards, HttpStatus.OK);
    }


    /**
     * Return creditCard by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCreditCard(@PathVariable(value = "id") long id) {
        CreditCardDto creditCard = creditCardService.getCreditCardById(id);
        return new ResponseEntity<CreditCardDto>(creditCard, HttpStatus.OK);
    }


    /**
     *Save the new credit card
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCreditCard(@RequestBody CreditCardDto creditCard, Principal principal) {

        UserContext userContext = ((JwtAuthenticationToken) principal).getPrincipal();

        try {
            this.creditCardService.save(creditCard, userContext.getUsername());
        } catch (CreditCardException e) {
            return geCustomErrorType(HttpStatus.CONFLICT.value(), "api/creditcards", "Save credit card failed",
                    e.getMessage(), LocalDateTime.now());
        }
        return new ResponseEntity<ResponseDto>(new ResponseDto("Credit card " + creditCard.getCardCode() + " created",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }


    /**
     * update the credit card
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCreditCard(@PathVariable(value = "id") long id,
                                              @RequestBody CreditCardDto creditCard) {
        creditCard.setId(id);
        try {
            this.creditCardService.update(creditCard);
        } catch (CreditCardException e) {
            return geCustomErrorType(HttpStatus.CONFLICT.value(), "api/creditcards", "Update credit card failed",
                    e.getMessage(), LocalDateTime.now());
        }
        return new ResponseEntity<ResponseDto>(new ResponseDto("Credit card update",
                HttpStatus.OK.value()), HttpStatus.OK);

    }

    /**
     *delete the credit
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCreditCard(@PathVariable(value = "id") long id) {

            this.creditCardService.deleteCreditCard(id);
        return new ResponseEntity<ResponseDto>(new ResponseDto("Credit card deleted",
                HttpStatus.OK.value()), HttpStatus.OK);

    }


}










