package mentortools.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusWithModulesDto {

    private Long id;
    private String name;
    public Set<ModuleDto> modules;
}
