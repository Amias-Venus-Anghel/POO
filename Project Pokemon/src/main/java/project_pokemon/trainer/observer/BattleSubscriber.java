package project_pokemon.trainer.observer;

public interface BattleSubscriber {
    void getUpdate(String state);
    void setObserver(BattleObserver observer);
}
