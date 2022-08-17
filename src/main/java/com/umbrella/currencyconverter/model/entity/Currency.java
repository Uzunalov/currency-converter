package com.umbrella.currencyconverter.model.entity;

import com.umbrella.currencyconverter.config.PostgresSQLEnumType;
import com.umbrella.currencyconverter.enums.CurrencyType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = "pgsql_enum", typeClass = PostgresSQLEnumType.class)
public class Currency {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "currency-id-generator")
    @GenericGenerator(
            name = "currency-id-generator",
            strategy = "com.umbrella.currencyconverter.model.entity.generator.CurrencyIdGenerator"
    )
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "nominal")
    private String nominal;

    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    @Column(name = "type")
    private CurrencyType type;
}
