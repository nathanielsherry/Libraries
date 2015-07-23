package net.sciencestudio.chanje;


public interface ChangeBus extends ChangeSource {

    void broadcast(final Object message);

}