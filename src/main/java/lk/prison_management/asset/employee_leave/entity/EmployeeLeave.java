package lk.prison_management.asset.employee_leave.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.employee_leave.entity.enums.LeaveType;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "EmployeeLeave" )
public class EmployeeLeave extends AuditEntity {

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm" )
  private LocalDateTime startAt;

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm" )
  private LocalDateTime endAt;

  private String dayCount, hourCount, remarks;

  @Enumerated( EnumType.STRING )
  private LeaveType leaveType;

  @ManyToOne
  private Employee employee;

}
