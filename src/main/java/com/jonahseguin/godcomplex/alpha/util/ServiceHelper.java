package com.jonahseguin.godcomplex.alpha.util;

import com.google.inject.Inject;
import com.jonahseguin.godcomplex.alpha.Alpha;


public class ServiceHelper {

    private final Alpha alpha;

    @Inject
    public ServiceHelper(Alpha alpha) {
        this.alpha = alpha;
    }

    public boolean start(Service service) {
        if (service != null) {
            if (!service.isRunning()) {
                boolean success = service.start();
                if (success) {
                    alpha.getLogger().info("Started service: " + service.getClass().getSimpleName());
                }
                else {
                    alpha.getServer().getPluginManager().disablePlugin(alpha);
                    alpha.getLogger().severe("Aborting startup, failed to start service: " + service.getClass().getSimpleName());
                }
                return success;
            }
            else {
                alpha.getLogger().info("Skipping startup of service " + service.getClass().getSimpleName() + ": it is already running");
            }
        }
        else {
            alpha.getLogger().info("Skipping startup of service " + service.getClass().getSimpleName() + ": it is null");
        }
        return true;
    }

    public boolean shutdown(Service service) {
        if (service != null) {
            if (service.isRunning()) {
                boolean success = service.shutdown();
                if (success) {
                    alpha.getLogger().info("Shutdown service: " + service.getClass().getSimpleName());
                }
                else {
                    alpha.getLogger().severe("Failed to shutdown service: " + service.getClass().getSimpleName());
                }
                return success;
            }
            else {
                alpha.getLogger().info("Skipping shutdown of service " + service.getClass().getSimpleName() + ": it is not running");
            }
        }
        else {
            alpha.getLogger().info("Skipping shutdown of service " + service.getClass().getSimpleName() + ": it is null");
        }
        return true;
    }



}
