package com.savchuk.secondlab.service;

import com.savchuk.secondlab.constans.ErrorMessage;
import com.savchuk.secondlab.dto.ShopRequestDto;
import com.savchuk.secondlab.entity.EntityStatus;
import com.savchuk.secondlab.entity.Shop;
import com.savchuk.secondlab.repository.ShopRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    public ShopService(ShopRepository shopRepository, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
    }

    public List<Shop> findAll() {
        return shopRepository.findAll().stream().filter
                (e -> e.isStatus() == EntityStatus.ACTIVE.getStatus()).collect(Collectors.toList());
    }

    public Shop findOneById(Long id) {
        return shopRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NO_SUCH_SHOP_FOUND_BY_ID + id));
    }

    public Shop findOneByName(String name) throws NotFoundException {
        return shopRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(ErrorMessage.NO_SUCH_SHOP_FOUND_BY_NAME + name));
    }

    public void delete(Long id) throws NotFoundException {
        if (!shopRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_SHOP_ID + id);
        }
        shopRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void update(Long id, ShopRequestDto shopRequestDto) throws NotFoundException {
        if (!shopRepository.existsById(id)) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_SHOP_FOUND_BY_ID + id);
        }
        Shop oldShop = shopRepository.findById(id).get();
        oldShop.setCity(shopRequestDto.getCity());
        oldShop.setId(id);
        oldShop.setName(shopRequestDto.getName());
        oldShop.setCountry(shopRequestDto.getCountry());
        shopRepository.save(oldShop);
    }

    public void create(ShopRequestDto shopRequestDto) {
        Shop newShop = modelMapper.map(shopRequestDto, Shop.class);
        shopRepository.save(newShop);
    }
}
