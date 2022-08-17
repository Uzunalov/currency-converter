package com.umbrella.currencyconverter.service.specification;

import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrencyValueSpecification implements Specification<CurrencyValue> {
    private final CurrencyValuesFilterRequest filterRequest;

    public CurrencyValueSpecification(CurrencyValuesFilterRequest filterRequest) {
        this.filterRequest = filterRequest;
    }

    @Override
    public Predicate toPredicate(Root<CurrencyValue> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList =new ArrayList<>();

        if(Objects.nonNull(filterRequest.getDate())) {
            predicateList.add(criteriaBuilder.equal(root.get("calculationDate"), filterRequest.getDate()));
        }

        if(Objects.nonNull(filterRequest.getCurrencyCode())) {
            predicateList.add(criteriaBuilder.equal(root.get("currency").get("code"), filterRequest.getCurrencyCode()));
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}
