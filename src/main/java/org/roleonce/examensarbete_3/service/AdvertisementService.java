package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.model.Advertisement;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Transactional
    public List<Advertisement> getAdvertisementsByUser(CustomUser user) {
        return advertisementRepository.findByOwner(user);
    }

    @Transactional
    public void saveAdvertisement(Advertisement advertisement) {
        advertisementRepository.save(advertisement);
    }
}
