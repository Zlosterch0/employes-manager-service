package com.DDimov.employee_service.dto;

import lombok.Data;

@Data
public class AddressDTO {
  private String street;
  private String streetNumber;
  private String zipCode;
  private String country;
}
