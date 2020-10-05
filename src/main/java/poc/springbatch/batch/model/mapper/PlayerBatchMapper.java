package poc.springbatch.batch.model.mapper;

import org.springframework.stereotype.Component;
import poc.springbatch.batch.enumeration.PlayerPosition;
import poc.springbatch.batch.model.Player;
import poc.springbatch.batch.processing.model.PlayerCSV;

@Component
public class PlayerBatchMapper {

    public Player toPlayerEntity(PlayerCSV playerCSV) {
        //TODO Melhorar solução para PlayerPosition Enum
        return Player.builder()
                .name(playerCSV.getName())
                .position(PlayerPosition.valueOf(playerCSV.getPosition().toUpperCase()))
                .jerseyNumber(playerCSV.getJerseyNumber())
                .build();
    }

}
