package me.drakosha.locationfromsignapi.signlistener;

import me.drakosha.locationfromsignapi.ymlutil.YmlUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;


public class SignListener implements Listener {

    @EventHandler
    public void signChangeEvent(SignChangeEvent event) {
        if (event.getLines() != null) {
            if (event.getLine(0).equals("point")){
                String path = YmlUtil.getPathToArraySign(event.getLines());

                BlockFace blockFace = getFacing(event.getBlock().getData());

                Location signLocation = event.getBlock().getLocation();
                signLocation.setX(signLocation.getX() + 0.5);
                signLocation.setZ(signLocation.getZ() + 0.5);
                signLocation.setYaw(getYawFromFace(blockFace));

                YmlUtil.writeDataConfig(path, signLocation);
            }
        }
    }
    @EventHandler
    public void signBlokeEvent(BlockBreakEvent event){
        if (event.getBlock().getState() instanceof Sign){
            Sign sign = (Sign) event.getBlock().getState();

            if (sign.getLine(0).equals("point") && sign.getLines().length > 1){
                String path = YmlUtil.getPathToArraySign(sign.getLines());
                YmlUtil.removeDataConfig(path);
            }
        }
    }

    public float getYawFromFace(BlockFace face) {
        switch (face) {
            case SOUTH:
                return 0.0f;
            case SOUTH_SOUTH_WEST:
                return 22.5f;
            case SOUTH_WEST:
                return 45.0f;
            case WEST_SOUTH_WEST:
                return 67.5f;
            case WEST:
                return 90.0f;
            case WEST_NORTH_WEST:
                return 112.5f;
            case NORTH_WEST:
                return 135.0f;
            case NORTH_NORTH_WEST:
                return 157.5f;
            case NORTH:
                return 180.0f;
            case NORTH_NORTH_EAST:
                return -157.5f;
            case NORTH_EAST:
                return -135.0f;
            case EAST_NORTH_EAST:
                return -112.5f;
            case EAST:
                return -90.0f;
            case EAST_SOUTH_EAST:
                return -67.5f;
            case SOUTH_EAST:
                return -45.0f;
            case SOUTH_SOUTH_EAST:
                return -22.5f;
            case UP:
            case DOWN:
            case SELF:
            default:
                return 0.0f;
        }
    }
    public BlockFace getFacing(byte data) {
        switch (data) {
            case 0:
                return BlockFace.SOUTH;
            case 1:
                return BlockFace.SOUTH_SOUTH_WEST;
            case 2:
                return BlockFace.SOUTH_WEST;
            case 3:
                return BlockFace.WEST_SOUTH_WEST;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.WEST_NORTH_WEST;
            case 6:
                return BlockFace.NORTH_WEST;
            case 7:
                return BlockFace.NORTH_NORTH_WEST;
            case 8:
                return BlockFace.NORTH;
            case 9:
                return BlockFace.NORTH_NORTH_EAST;
            case 10:
                return BlockFace.NORTH_EAST;
            case 11:
                return BlockFace.EAST_NORTH_EAST;
            case 12:
                return BlockFace.EAST;
            case 13:
                return BlockFace.EAST_SOUTH_EAST;
            case 14:
                return BlockFace.SOUTH_EAST;
            case 15:
                return BlockFace.SOUTH_SOUTH_EAST;
            default:
                return null;
        }
    }
}
