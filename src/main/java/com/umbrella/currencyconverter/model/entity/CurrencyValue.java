package com.umbrella.currencyconverter.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "currency_values")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyValue {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "currency-value-id-generator")
    @GenericGenerator(
            name = "currency-value-id-generator",
            strategy = "com.umbrella.currencyconverter.model.entity.generator.CurrencyValueIdGenerator"
    )
    private String id;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "insert_date")
    private Date insertDate;

    @Column(name = "calculation_date")
    private LocalDate calculationDate;
}
