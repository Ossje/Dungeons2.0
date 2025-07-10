package ossje.dungeon2.utils.game.states;

import ossje.dungeon2.utils.dugeongen.DungeonManager;
import ossje.dungeon2.utils.game.GameState;

public class DeletingState extends GameState {
    @Override
    public void onEnable() {
        this.manager.getDungeonManager().EndGame();
    }

}
