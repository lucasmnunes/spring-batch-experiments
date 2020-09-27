package poc.springbatch.batch.processing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import poc.springbatch.batch.model.Player;

@Component
@Slf4j
public class PlayerItemProcessor implements ItemProcessor<Player, Player> {

    @Override
    public Player process(final Player player) throws Exception {
        log.info("Converting: " + player.getName());

        return Player.builder()
                .name(player.getName().toUpperCase())
                .position(player.getPosition().toUpperCase())
                .jerseyNumber(player.getJerseyNumber())
                .build();
    }

}
