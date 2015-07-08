package com.example.bookstore.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.support.AccountBuilder;
import com.example.bookstore.service.AccountService;
import com.example.bookstore.service.AuthenticationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringMvcTestLoginControllerTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private AccountService accountService;

    private Account account;

    @Before
    public void setup() throws Exception {
        this.account = new AccountBuilder() {
            {
                address("Herve", "4650", "Rue de la station", "1", null, "Belgium");
                credentials("john", "secret");
                name("John", "Doe");
            }
        }.build(true);

        Mockito.when(this.accountService.login("john", "secret")).thenReturn(this.account);
    }

    @Test
    public void testHandleLogin() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.loginController).build();
        mockMvc.perform(post("/login").param("username", "john").param("password", "secret"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(LoginController.ACCOUNT_ATTRIBUTE, this.account))
                .andExpect(redirectedUrl("/index.htm"));
    }

    @After
    public void verify() throws AuthenticationException {
        Mockito.verify(this.accountService, VerificationModeFactory.times(1)).login("john", "secret");
        Mockito.reset();
    }

    @Configuration
    static class LoginControllerTestConfiguration {

        @Bean
        public AccountService accountService() {
            return Mockito.mock(AccountService.class);
        }

        @Bean
        public LoginController loginController() {
            return new LoginController();
        }
    }
}
