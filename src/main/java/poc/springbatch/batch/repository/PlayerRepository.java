package poc.springbatch.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.springbatch.batch.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}