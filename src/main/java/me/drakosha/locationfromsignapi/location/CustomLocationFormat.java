package me.drakosha.locationfromsignapi.location;

import lombok.Getter;

@Getter
public class CustomLocationFormat {
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    public CustomLocationFormat(double x, double y, double z, float yaw){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
    }
}
