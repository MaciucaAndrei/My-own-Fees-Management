package project.licenta.cdi;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;

public class CDIContainerThread implements Runnable {

    private CdiContainer cdiContainer;

    @Override
    public void run() {
        initCdiContainer();
    }

    private void initCdiContainer() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        cdiContainer.getContextControl().startContexts();
    }

}
