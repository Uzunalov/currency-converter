package com.umbrella.currencyconverter.dao;

import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.service.specification.CurrencyValueSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyValueRepository extends JpaRepository<CurrencyValue, String>, JpaSpecificationExecutor<CurrencyValue> {
    Optional<CurrencyValue> findByCalculationDateAndCurrencyId(LocalDate calculationDate, String currencyId);
    List<CurrencyValue> findAllByCalculationDate(LocalDate calculationDate);
}
