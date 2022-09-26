package am.itspace.companyEmployeeSpring.controller;

import am.itspace.companyEmployeeSpring.entity.Company;
import am.itspace.companyEmployeeSpring.entity.Employee;
import am.itspace.companyEmployeeSpring.repository.CompanyRepository;
import am.itspace.companyEmployeeSpring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Value("${company.employees.images.folder}")
    private String folderPath;

    @GetMapping("/employees")
    public String employees(ModelMap modelMap) {
        List<Employee> employees = employeeRepository.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("employeeImage") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File imageFile = new File(folderPath + fileName);
            file.transferTo(imageFile);
            employee.setProfilePic(fileName);
        }
        Company company = employee.getCompany();
        company.setSize(company.getSize() + 1);
        employeeRepository.save(employee);
        companyRepository.save(company);
        return "redirect:/employees";
    }

    @GetMapping("/employees/delete")
    public String deleteEmployee(@RequestParam("id") int id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee = employeeOptional.get();
        Company company = employee.getCompany();
        company.setSize(company.getSize() - 1);
        companyRepository.save(company);
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + fileName);
        return IOUtils.toByteArray(inputStream);

    }


}
