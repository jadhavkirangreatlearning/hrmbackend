package com.csi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int empId;

    @Size(min = 2, message = "Employee Name should be atleast 2 character")
    private String empName;

    @NotNull
    private String empAddress;

    @Column(unique = true)
    @Range(min = 1000000000, max = 9999999999L, message = "Employee Contact Number must be 10 digit")
    private long empContactNumber;

    private double empSalary;

    @Column(unique = true)
    @Range(min = 100000000000L,max = 999999999999L,message = "Emp UID must be 12 digit")
    private long empUID;

    @Column(unique = true)
    private String empPancard;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date empDOB;

    @Email(message = "Email Id must be valid")
    private String empEmailId;

    @Size(min = 4, message = "Password atlest 4 characters")
    private String empPassword;


}
