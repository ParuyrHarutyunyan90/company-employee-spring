package am.itspace.companyEmployeeSpring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private double salary;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String position;
    @Column(name = "profile_pic")
    private String profilePic;
    @ManyToOne
    private Company company;


}
