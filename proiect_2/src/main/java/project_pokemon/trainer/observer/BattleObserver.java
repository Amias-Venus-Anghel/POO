package project_pokemon.trainer.observer;

import java.util.ArrayList;

public class BattleObserver {
    private String state;
    private ArrayList<BattleSubscriber> subscribers = new ArrayList<>();

    public BattleObserver() {
    }

    public void addSubscriber(BattleSubscriber pt) {
        subscribers.add(pt);
        pt.setObserver(this);
    }

    public void notify(String state) {
        this.state = state;
        notifySubscribers();
    }

    private void notifySubscribers() {
        for (BattleSubscriber sub : subscribers) {
            sub.getUpdate(state);
        }
        this.state = "";
    }
}
