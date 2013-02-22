package com.example.bookstore.domain.support;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.Address;
import com.example.bookstore.domain.Permission;
import com.example.bookstore.domain.Role;

/**
 * Builds {@link Account} domain objects
 * 
 * 
 * 
 * 
 */
@Component
public class AccountBuilder extends EntityBuilder<Account> {

    @Override
    void initProduct() {
        this.product = new Account();
    }

    public AccountBuilder credentials(String username, String password) {
        this.product.setUsername(username);
        this.product.setPassword(DigestUtils.sha256Hex(password + "{" + username + "}"));
        return this;
    }

    public AccountBuilder address(String city, String postalCode, String street, String houseNumber, String boxNumber,
            String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setHouseNumber(houseNumber);
        address.setPostalCode(postalCode);
        address.setBoxNumber(boxNumber);
        address.setCountry(country);

        this.product.setAddress(address);
        return this;
    }

    public AccountBuilder roleWithPermissions(Role role, Permission... permissions) {
        this.product.getRoles().add(role);

        for (Permission permission : permissions) {
            role.getPermissions().add(permission);
        }
        return this;
    }

    public AccountBuilder email(String email) {
        this.product.setEmailAddress(email);
        return this;
    }

    public AccountBuilder name(String firstName, String lastName) {
        this.product.setFirstName(firstName);
        this.product.setLastName(lastName);
        return this;
    }

    @Override
    Account assembleProduct() {
        return this.product;
    }
}