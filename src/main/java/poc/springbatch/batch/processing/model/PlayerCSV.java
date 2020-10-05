package poc.springbatch.batch.processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCSV {

    private Integer id;

    private String name;

    private String position;

    private Integer jerseyNumber;

}
