package com.hex.pc.network;

public interface NetComunicaiton {

    public abstract void sendMessage(String msg);

    public abstract void run();

    public abstract void kill();

}
