package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static org.junit.Assert.*;

import android.location.Address;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;

public class AddressAdapterTest {

    @Test
    public void adressLineOfAnySizeGetsConcatedToOneString(){
        Locale locale = Locale.GERMANY;
        Address address = new Address(locale);
        address.setCountryName("Deutschland");
        address.setLocality("Berlin");
        address.setAddressLine(0, "Mauerpark");
        address.setAddressLine(1, "Berlin");
        address.setAddressLine(2, "Deutschland");
        ArrayList<Address> addresses = new ArrayList<>();

        AddressAdapter addressAdapter = new AddressAdapter(addresses);
        String concatedAddress = addressAdapter.addressLine(address);
        String expectedString = "Mauerpark, Berlin, Deutschland";
        assertEquals(expectedString, concatedAddress);
    }


}