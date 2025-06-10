package ossje.dungeon2.utils.game.tasks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import ossje.dungeon2.utils.game.GameManager;
import ossje.dungeon2.utils.game.states.ActiveState;

import java.util.List;


@RequiredArgsConstructor
public class StartingTask extends BukkitRunnable {

    private final List<Integer> timebc = List.of(60, 30, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

    @Getter @Setter
    private int time = 60;
    private final GameManager gameManger;

    @Override
    public void run(){
        if(timebc.contains(time)){
            Bukkit.broadcastMessage(String.valueOf(time));
        }

        if(time == 0){
            gameManger.setState(new ActiveState());
            this.cancel();
        }

        time--;
    }
}
