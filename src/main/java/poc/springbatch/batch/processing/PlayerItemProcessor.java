package poc.springbatch.batch.processing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import poc.springbatch.batch.model.Player;
import poc.springbatch.batch.model.mapper.PlayerBatchMapper;
import poc.springbatch.batch.processing.model.PlayerCSV;

@Component
@Slf4j
public class PlayerItemProcessor implements ItemProcessor<PlayerCSV, Player> {

    private PlayerBatchMapper playerBatchMappermapper;

    PlayerItemProcessor(PlayerBatchMapper playerBatchMappermapper) {
        this.playerBatchMappermapper = playerBatchMappermapper;
    }

    @Override
    public Player process(final PlayerCSV playerCSV) throws Exception {
        log.info("Converting: " + playerCSV.getName());

        Player playerEntity = playerBatchMappermapper.toPlayerEntity(playerCSV);

        return Player.builder()
                .name(playerEntity.getName().toUpperCase())
                .position(playerEntity.getPosition())
                .jerseyNumber(playerEntity.getJerseyNumber())
                .build();
    }

}
