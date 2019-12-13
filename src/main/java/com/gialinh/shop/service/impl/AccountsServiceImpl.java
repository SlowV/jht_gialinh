package com.gialinh.shop.service.impl;

import com.gialinh.shop.service.AccountsService;
import com.gialinh.shop.domain.Accounts;
import com.gialinh.shop.repository.AccountsRepository;
import com.gialinh.shop.service.dto.AccountsDTO;
import com.gialinh.shop.service.mapper.AccountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Accounts}.
 */
@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

    private final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    public AccountsServiceImpl(AccountsRepository accountsRepository, AccountsMapper accountsMapper) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
    }

    /**
     * Save a accounts.
     *
     * @param accountsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountsDTO save(AccountsDTO accountsDTO) {
        log.debug("Request to save Accounts : {}", accountsDTO);
        Accounts accounts = accountsMapper.toEntity(accountsDTO);
        accounts = accountsRepository.save(accounts);
        return accountsMapper.toDto(accounts);
    }

    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAll(pageable)
            .map(accountsMapper::toDto);
    }


    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountsDTO> findOne(Long id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id)
            .map(accountsMapper::toDto);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
    }
}
