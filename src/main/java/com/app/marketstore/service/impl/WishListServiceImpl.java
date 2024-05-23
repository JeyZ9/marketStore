package com.app.marketstore.service.impl;

import com.app.marketstore.model.User;
import com.app.marketstore.model.WishList;
import com.app.marketstore.repository.WishListRepository;
import com.app.marketstore.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListServiceImpl(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @Override
    public void createWishList(WishList wishList){
        wishListRepository.save(wishList);
    }

    @Override
    public List<WishList> readWishList(User user){
        return wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
}
