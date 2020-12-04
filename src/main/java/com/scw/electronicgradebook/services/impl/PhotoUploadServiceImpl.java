package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.validators.SSRFValidator;
import com.scw.electronicgradebook.services.PhotoUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoUploadServiceImpl implements PhotoUploadService {

    @Value("${user.photo.maxSize}")
    private int userPhotoMaxSize;

    private final UserRepository userRepository;

    private final SSRFValidator ssrfValidator;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public void uploadPhoto(String url) {
        try {
            URL photoUrl = new URL(url);

            if (!ssrfValidator.isValid(photoUrl))
                throw new IllegalArgumentException("Photo url is incorrect");

            BufferedInputStream stream = new BufferedInputStream(photoUrl.openStream());

            byte[] image = stream.readNBytes(userPhotoMaxSize);
            if (stream.read() != -1)
                throw new IllegalArgumentException("Photo must be less 256 kb size");

            User currentUser = securityUtils.getCurrentUser();
            currentUser.setPhoto(image);

            userRepository.update(currentUser);
        } catch (Exception e) {
            log.error("Error while downloading image by link " + url);
            throw new IllegalArgumentException("Image url is incorrect");
        }
    }
}
