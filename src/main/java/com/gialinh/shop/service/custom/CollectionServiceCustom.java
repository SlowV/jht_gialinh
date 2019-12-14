package com.gialinh.shop.service.custom;

import com.gialinh.shop.domain.Collection;
import com.gialinh.shop.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceCustom {
    @Autowired
    CollectionRepository collectionRepository;
    public Collection save(Collection collection){
        return collectionRepository.save(collection);
    }

    public Page<Collection> collections(int page, int size){
        return collectionRepository.findAll(PageRequest.of(page, size));
    }

    public List<Collection> findAllNoPage(){
        return collectionRepository.findAll();
    }
}
