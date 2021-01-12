package lk.prison_management.asset.process_management.proformance_evaluation.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.process_management.proformance_evaluation.entity.enums.PerformanceEvaluationStatus;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "PerformanceEvaluation" )
public class PerformanceEvaluation extends AuditEntity {
  private String remark;

  @Enumerated( EnumType.STRING )
  private PerformanceEvaluationStatus performanceEvaluationStatus;

  @ManyToOne
  private Employee employee;
}
