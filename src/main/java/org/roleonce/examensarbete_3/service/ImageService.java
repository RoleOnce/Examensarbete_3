package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.model.Advertisement;
import org.roleonce.examensarbete_3.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Advertisement saveImage(MultipartFile file, String firstName, String lastName, String description) throws IOException {
        String type = file.getContentType();
        byte[] bytes = file.getBytes();

        Advertisement advertisement = new Advertisement();
        advertisement.setFirstName(firstName);
        advertisement.setLastName(lastName);
        advertisement.setDescription(description);
        advertisement.setType(type);
        advertisement.setImage(bytes);

        return imageRepository.save(advertisement);
    }

}
