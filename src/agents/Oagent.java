package agents;

import models.MoveTree;
import models.Value;

public class Oagent extends Agent{

    public Oagent(){
        this.agentValue = Value.O;
    }

    public Oagent(MoveTree moveTree) {
        super(moveTree);
        this.agentValue = Value.O;
    }

}
