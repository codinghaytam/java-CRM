package org.example.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CurrentUserTest {
    @Test
    public void testLoggedInAdmin() {
        String admin = "testAdmin";
        CurrentUser.setLoggedInAdmin(admin);
        assertEquals(admin, CurrentUser.getLoggedInAdmin());
    }

    @Test
    public void testLoggedInCommercial() {
        String commercial = "testCommercial";
        CurrentUser.setLoggedInCommercial(commercial);
        assertEquals(commercial, CurrentUser.getLoggedInCommercial());
    }
}