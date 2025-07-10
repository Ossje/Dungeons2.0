package ossje.dungeon2.utils.game.tasks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;
import ossje.dungeon2.utils.game.GameManager;
import ossje.dungeon2.utils.game.states.EndingState;

import java.util.List;


@RequiredArgsConstructor
public class GameTask extends BukkitRunnable {

    @Getter @Setter
    private int time = 180; // 3 minutes
    private final GameManager gameManger;

    @Override
    public void run(){
        if(time == 0){
            gameManger.setState(new EndingState());
            this.cancel();
        }

        time--;
    }
}
