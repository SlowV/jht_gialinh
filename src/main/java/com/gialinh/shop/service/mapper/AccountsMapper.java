package com.gialinh.shop.service.mapper;

import com.gialinh.shop.domain.*;
import com.gialinh.shop.service.dto.AccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {


    @Mapping(target = "customers", ignore = true)
    @Mapping(target = "removeCustomer", ignore = true)
    Accounts toEntity(AccountsDTO accountsDTO);

    default Accounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Accounts accounts = new Accounts();
        accounts.setId(id);
        return accounts;
    }
}
