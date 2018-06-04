package ie.myinfinity.demo.creditcard.dao.impl;

import ie.myinfinity.demo.creditcard.dao.CreditCardDao;
import ie.myinfinity.demo.creditcard.dtos.CreditCardDto;
import ie.myinfinity.demo.creditcard.dtos.CreditCardRequestDto;
import ie.myinfinity.demo.creditcard.entities.CreditCard;
import ie.myinfinity.demo.creditcard.entities.CreditCard_;
import ie.myinfinity.demo.creditcard.entities.User;
import ie.myinfinity.demo.creditcard.entities.User_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository("creditCardDao")
public class CreditCardDaoImpl implements CreditCardDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<CreditCardDto> findCreditCards(CreditCardRequestDto creditCardRequestDto) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCardDto> criteriaQuery=cb.createQuery(CreditCardDto.class);
        Root<CreditCard> creditCardRoot=criteriaQuery.from(CreditCard.class);
        List<Selection<?>> selections = new ArrayList<>(Arrays.asList(creditCardRoot.get(CreditCard_.id),creditCardRoot.get(CreditCard_.cardCode),
                creditCardRoot.get(CreditCard_.name),
                cb.function("MONTH", Integer.class, creditCardRoot.get(CreditCard_.expiryDate)),
                cb.function("YEAR", Integer.class, creditCardRoot.get(CreditCard_.expiryDate))
                ));

        List<Predicate> predicates = new ArrayList<>();

        String searchLike=creditCardRequestDto.getCardCode();
        if (!StringUtils.isBlank(searchLike)){
            predicates.add(cb.like(creditCardRoot.get(CreditCard_.cardCode), searchLike.toUpperCase()+"%"));
        }


        if ("USER".equalsIgnoreCase(creditCardRequestDto.getRole())){
            Join<CreditCard, User> userJoin=creditCardRoot.join(CreditCard_.user, JoinType.INNER);
            predicates.add(cb.equal(cb.upper(userJoin.get(User_.username)), creditCardRequestDto.getUsername().toUpperCase()));
        }

         criteriaQuery.multiselect(selections);
                 if(!predicates.isEmpty()) {
                 criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                 }
                 criteriaQuery.orderBy(cb.asc(creditCardRoot.get(CreditCard_.cardCode)));

        TypedQuery<CreditCardDto> typedQuery=entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }


    @Override
    public CreditCardDto findCreditCardById(long id) {

        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCardDto> criteriaQuery=cb.createQuery(CreditCardDto.class);
        Root<CreditCard> creditCardRoot=criteriaQuery.from(CreditCard.class);
        List<Selection<?>> selections = new ArrayList<>(Arrays.asList(creditCardRoot.get(CreditCard_.id),creditCardRoot.get(CreditCard_.cardCode),
                creditCardRoot.get(CreditCard_.name),
                cb.function("MONTH", Integer.class, creditCardRoot.get(CreditCard_.expiryDate)),
                cb.function("YEAR", Integer.class, creditCardRoot.get(CreditCard_.expiryDate))
        ));

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(creditCardRoot.get(CreditCard_.id), id));

        criteriaQuery.multiselect(selections);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<CreditCardDto> typedQuery=entityManager.createQuery(criteriaQuery);

        return typedQuery.getSingleResult();
    }
}
