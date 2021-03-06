package lk.prison_management.asset.punishment.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prison_management.asset.censure.entitiy.Censure;
import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.common_asset.model.enums.LiveOrDead;
import lk.prison_management.asset.punishment.entity.enums.PunishmentType;
import lk.prison_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Punishment")
public class Punishment extends AuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private PunishmentType punishmentType;

    @OneToMany(mappedBy = "punishment")
    private List< Censure > censures;


//    @Enumerated( EnumType.STRING )
//    private LiveOrDead liveOrDead;
}
