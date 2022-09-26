package am.itspace.companyEmployeeSpring.controller;

import am.itspace.companyEmployeeSpring.entity.Company;
import am.itspace.companyEmployeeSpring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/companies")
    public String companies(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "companies";
    }
    @GetMapping("/companies/delete")
    public String deleteCompany(@RequestParam("id") int id) {
        companyRepository.deleteById(id);
         return "redirect:/companies";
    }

    @GetMapping("/companies/add")
    public String companiesPage() {
        return "addCompany";
    }
    @PostMapping("/companies/add")
    public String addCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }
}
