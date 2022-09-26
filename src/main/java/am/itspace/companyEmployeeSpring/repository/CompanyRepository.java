package am.itspace.companyEmployeeSpring.repository;

import am.itspace.companyEmployeeSpring.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository  extends JpaRepository<Company, Integer> {
}
