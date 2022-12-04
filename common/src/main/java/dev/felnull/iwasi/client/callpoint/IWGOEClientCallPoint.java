package dev.felnull.iwasi.client.callpoint;

import dev.felnull.iwasi.client.model.IWGModels;
import dev.felnull.iwasi.client.model.pointer.ModelPointerManager;
import dev.felnull.otyacraftengine.client.callpoint.ClientCallPoint;
import dev.felnull.otyacraftengine.client.callpoint.ClientResourceListenerRegister;
import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;

@ClientCallPoint.Sign
public class IWGOEClientCallPoint implements ClientCallPoint {
    @Override
    public void onModelRegistry(ModelRegister register) {
        IWGModels.init(register);
    }

    @Override
    public void onResourceListenerRegistry(ClientResourceListenerRegister register) {
        register.registerReloadListener(ModelPointerManager.getInstance());
    }
}
