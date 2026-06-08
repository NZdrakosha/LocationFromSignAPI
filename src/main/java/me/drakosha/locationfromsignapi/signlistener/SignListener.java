package me.drakosha.locationfromsignapi.signlistener;

import lombok.var;
import me.drakosha.locationfromsignapi.json.JsonUtil;
import me.drakosha.locationfromsignapi.location.CustomLocationFormat;
import org.bukkit.GameMode;
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
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
                    return;
                }
                String path = getPathToArraySign(event.getLines());
                if (JsonUtil.getElementByPath(path) != null){
                    event.getPlayer().sendMessage("sign with this description already exist");
                    event.setCancelled(true);
                    return;
                }

                BlockFace blockFace = getFacing(event.getBlock().getData());

                Location signLocation = event.getBlock().getLocation();

                var clf = new CustomLocationFormat(
                        signLocation.getX() + 0.5,
                        signLocation.getY(),
                        signLocation.getZ() + 0.5,
                        getYawFromFace(blockFace)
                );

                JsonUtil.setJsonObjectByPath(path, clf);
            }
        }
    }
    @EventHandler
    public void signBrokeEvent(BlockBreakEvent event){
        if (event.getBlock().getState() instanceof Sign){
            Sign sign = (Sign) event.getBlock().getState();
            if (sign.getLine(0).equals("point") && sign.getLines().length > 1){
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
                    return;
                }

                String path = getPathToArraySign(sign.getLines());

                JsonUtil.removeJsonObjectByPath(path);

            }
        }
    }

    public float getYawFromFace(BlockFace face) {
        switch (face) {
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
            case SOUTH:
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
    private String getPathToArraySign(String[] lines){
        if (lines == null) return "";
        StringBuilder path = new StringBuilder();
        for (int i = 1; i < lines.length; i++){
            String s = lines[i];
            if (s.isEmpty()) break;
            path.append(s);
        }
        return path.toString();
    }
}
