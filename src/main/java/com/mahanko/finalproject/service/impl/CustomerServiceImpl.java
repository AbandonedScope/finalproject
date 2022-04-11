package com.mahanko.finalproject.service.impl;

import com.mahanko.finalproject.dao.CustomerDao;
import com.mahanko.finalproject.dao.impl.CustomerDaoImpl;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.service.CustomerService;
import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerServiceImpl implements CustomerService {
    private static final String PASSWORD_DIGEST_ALGORITHM = "MD5";
    private static final CustomerServiceImpl instance = new CustomerServiceImpl();

    private CustomerServiceImpl() {
    }

    public static CustomerServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        // FIXME: 09.04.2022 validate login, password + md5
        CustomerDao customerDao = CustomerDaoImpl.getInstance();
        //validation ...

        //encryption
        boolean isMatch = false;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(PASSWORD_DIGEST_ALGORITHM);
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            String encryptedPassword = DatatypeConverter.printHexBinary(digest);
            isMatch = customerDao.authenticate(login, encryptedPassword);
        } catch (NoSuchAlgorithmException | DaoException e) {
            throw new ServiceException(e);
        }

        return isMatch;
    }
}
