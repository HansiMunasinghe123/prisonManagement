package lk.prison_management.asset.performance_evaluation_approval.controller;


import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee.service.EmployeeService;
import lk.prison_management.asset.employee_file.service.EmployeeFilesService;
import lk.prison_management.asset.performance_evaluation_approval.entity.PerformanceEvaluationApproval;
import lk.prison_management.asset.performance_evaluation_approval.service.PerformanceEvaluationApprovalService;
import lk.prison_management.asset.performance_evaluation_request.entity.PerformanceEvaluationRequest;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.Apprecial;
import lk.prison_management.asset.performance_evaluation_request.entity.enums.PerformanceEvaluationStatus;
import lk.prison_management.asset.performance_evaluation_request.service.PerformanceEvaluationRequestService;
import lk.prison_management.asset.user.service.UserService;
import lk.prison_management.util.service.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/performanceEvaluationApproval" )
public class PerformanceEvaluationApprovalController {
  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;
  private final UserService userService;
  private final PerformanceEvaluationRequestService performanceEvaluationRequestService;
  private final PerformanceEvaluationApprovalService performanceEvaluationApprovalService;
  private final EmailService emailService;

  public PerformanceEvaluationApprovalController(EmployeeService employeeService,
                                                 EmployeeFilesService employeeFilesService,
                                                 UserService userService,
                                                 PerformanceEvaluationRequestService performanceEvaluationRequestService, PerformanceEvaluationApprovalService performanceEvaluationApprovalService, EmailService emailService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.userService = userService;
    this.performanceEvaluationRequestService = performanceEvaluationRequestService;
    this.performanceEvaluationApprovalService = performanceEvaluationApprovalService;
    this.emailService = emailService;
  }

  @RequestMapping
  public String employeePage(Model model) {
    Employee employeeUser =
        userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();
    List< Employee > employees = new ArrayList<>();

    for ( Employee employee : performanceEvaluationRequestService.findBySupervisor(employeeUser) ) {
      employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
      employees.add(employee);
    }
    model.addAttribute("employees", employees);
    return "performanceEvaluation/performanceEvaluation";
  }

  //Send on employee details
  @GetMapping( "/approve/{id}" )
  public String employeeView(@PathVariable( "id" ) Integer id, Model model) {
    return commonThing(model, new PerformanceEvaluationApproval(), employeeService.findById(id));
  }

  private String commonThing(Model model, PerformanceEvaluationApproval performanceEvaluationApproval,
                             Employee employee) {
    performanceEvaluationApproval.setPerformanceEvaluationRequest(employee.getPerformanceEvaluationRequests().get(employee.getPerformanceEvaluationRequests().size() - 1));
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", true);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("performanceEvaluationApproval", performanceEvaluationApproval);
    model.addAttribute("apprecials", Apprecial.values());
    return "performanceEvaluation/addPerformanceEvaluationApproval";
  }

  @PostMapping( "/save" )
  public String save(@ModelAttribute PerformanceEvaluationApproval performanceEvaluationApproval,
                     BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      Employee employee =
          employeeService.findById(performanceEvaluationApproval.getPerformanceEvaluationRequest().getEmployee().getId());
      commonThing(model, performanceEvaluationApproval, employee);
    }

    PerformanceEvaluationRequest performanceEvaluationRequest =
        performanceEvaluationApproval.getPerformanceEvaluationRequest();
    performanceEvaluationRequest.setPerformanceEvaluationStatus(PerformanceEvaluationStatus.APPROVED);
    performanceEvaluationApproval.setPerformanceEvaluationRequest(performanceEvaluationRequest);

    PerformanceEvaluationApproval performanceEvaluationApprovalDb = performanceEvaluationApprovalService.persist(performanceEvaluationApproval);

   Employee employee =  performanceEvaluationApprovalDb.getPerformanceEvaluationRequest().getEmployee();
    if (employeeService.findById(employee.getId()).getOfficeEmail() != null) {
      StringBuilder message = new StringBuilder("Performance Apprecial was evaluated \n Thanks \n\n"+ performanceEvaluationApprovalDb.getCreatedBy());

      emailService.sendEmail(employeeService.findById(employee.getId()).getOfficeEmail(),
                             "Performance Apprecial was evaluated." , message.toString());

    /*  if (performanceEvaluationRequestSaved.getEmployee().getContactOne() != null) {
        try {
          String mobileNumber = purchaseOrderSaved.getSupplier().getContactOne().substring(1, 10);
          twilioMessageService.sendSMS("+94" + mobileNumber, "There is immediate PO from " +
                  "Samarasingher Super \nPlease Check Your Email Form Further Details");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }*/
    }



    return "redirect:/home";
  }
}
