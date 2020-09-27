package poc.springbatch.batch.processing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import poc.springbatch.batch.model.Player;
import poc.springbatch.batch.repository.PlayerRepository;

import java.util.List;

@Component
@Slf4j
public class PlayerItemWriter implements ItemWriter<Player> {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerItemWriter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void write(List<? extends Player> players) throws Exception {
        log.info("Saving Players...");
        playerRepository.saveAll(players);
    }

}
