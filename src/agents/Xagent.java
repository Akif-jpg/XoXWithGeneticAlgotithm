package agents;

import models.MoveTree;
import models.Value;

public class Xagent extends Agent{
    public Xagent(){
        this.agentValue = Value.X;
    }

    public Xagent(MoveTree moveTree){
        super(moveTree);
        this.agentValue = Value.X;
    }

}
