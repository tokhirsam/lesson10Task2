package uz.pdp.video.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.video.entity.Address;
import uz.pdp.video.entity.Group;
import uz.pdp.video.entity.Subject;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    String firstName;
    String lastName;
    Integer addressId;
    Integer groupId;
    List<Integer> subjectsId;
}
