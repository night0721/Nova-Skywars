package me.night0721.skywars.instances;

import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.*;
import me.night0721.skywars.kit.type.*;
import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.Kit;
import me.night0721.skywars.kit.KitType;
import me.night0721.skywars.kit.type.*;
import me.night0721.skywars.managers.ConfigManager;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {
    private final Skywars main;
    private final List<Location> spawn = new ArrayList<>();
    private GameState state;
    private final HashMap<UUID, Kit> kits;
    private CountDown countDown;
    private Game game;
    private boolean joinable;

    public Arena(Skywars main) {
        FileConfiguration config = main.getConfig();
        List<List<Double>> v = new ArrayList<>();
        Bukkit.createWorld(new WorldCreator(config.getString("arena.world")));
        for(Object a : config.getList("arena.points")) {
            v.add((List<Double>) a);
        }
        for(List<Double> points : v) {
                spawn.add(new Location(
                        Bukkit.getWorld(Objects.requireNonNull(config.getString("arena.world"))),
                        points.get(0),
                        points.get(1),
                        points.get(2),
                        ((Number) points.get(3)).floatValue(),
                        ((Number) points.get(4)).floatValue()
                ));
        }
        this.state = GameState.FINDING;
        this.kits = new HashMap<>();
        this.countDown = new CountDown(main, this);
        this.game = new Game(this);
        this.joinable = true;
        this.main = main;
    }

    /* Game */
    public void start() { game.start(); }

    public void reset() {
        if (state == GameState.LIVE) {
            this.joinable = false;
            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID id = p.getUniqueId();
                Player player = Bukkit.getPlayer(id);
                player.getInventory().clear();
                player.teleport(ConfigManager.getSpawnLocation());
                removeKit(player.getUniqueId());
            }
            World w = spawn.get(0).getWorld();
            String name = w.getName();
            Bukkit.getPlayer("MizukiHK").teleport(new Location(Bukkit.getWorld("baseskywars"),59.5, 65.5, 23.5));
            Bukkit.unloadWorld(w, false);
        } else if (state == GameState.COUNTINGDOWN) {
            state = GameState.FINDING;
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.teleport(ConfigManager.getSpawnLocation());
                player.closeInventory();
                removeKit(player.getUniqueId());
            }
        }
        state = GameState.FINDING;
        kits.clear();
        sendTitleToAll("", "");
        countDown.cancel();
        countDown = new CountDown(main, this);
        game = new Game(this);
    }




    /* Util */
    public void sendMsgToAll(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
    public void sendTitleToAll(String title, String subtitle) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle);
        }
    }

    /* Player */
    public void addPlayer() {
        if(state.equals(GameState.FINDING) && Bukkit.getOnlinePlayers().size() >= ConfigManager.getReqPlayers()) {
            countDown.start();
        }
    }
    public void removePlayer(Player player) {
        player.teleport(ConfigManager.getSpawnLocation());
        player.sendTitle("", "");
        removeKit(player.getUniqueId());
        if(state == GameState.COUNTINGDOWN && Bukkit.getOnlinePlayers().size() - 1 < ConfigManager.getReqPlayers()) {
            sendMsgToAll(ChatColor.RED + "There is no enough players. Countdown stopped until new player joins.");
            reset();
            return;
        }
        if(state == GameState.LIVE && Bukkit.getOnlinePlayers().size() - 1 < ConfigManager.getReqPlayers()) {
            sendMsgToAll(ChatColor.RED + "Game has ended as there is no enough players.");
            reset();
        }
        if(Bukkit.getOnlinePlayers().size() >= ConfigManager.getReqPlayers()) {
            countDown.start();
        }
    }
    public void teleportPlayerToGameSpawns() {
        int x = ThreadLocalRandom.current().nextInt(this.spawn.size());
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(spawn.get(x));
        }

    }
    public void setKit(UUID uuid, KitType kit) {
        removeKit(uuid);
        if(kit == KitType.CUPID) {
            kits.put(uuid, new CupidKit(main, uuid));
        } else if(kit == KitType.PROPHET) {
            kits.put(uuid, new ProphetKit(main, uuid));
        } else if(kit == KitType.THOR) {
            kits.put(uuid, new ThorKit(main, uuid));
        } else if(kit == KitType.VALKYRIE) {
            kits.put(uuid, new ValkyrieKit(main, uuid));
        } else if(kit == KitType.VILLAGER) {
            kits.put(uuid, new VillagerKit(main, uuid));
        } else if(kit == KitType.WOLF) {
            kits.put(uuid, new WolfKit(main, uuid));
        }
    }
    public void removeKit(UUID uuid) {
        if(kits.containsKey(uuid)) {
            kits.get(uuid).remove();
            kits.remove(uuid);
        }
    }
    public KitType getKitType(Player player) {
        return kits.containsKey(player.getUniqueId()) ? kits.get(player.getUniqueId()).getType() : null;
    }
    public boolean isJoinable() { return this.joinable; }
    public void toggleJoin() { this.joinable = !this.joinable; }
    public HashMap<UUID, Kit> getKits() {
        return kits;
    }
    public World getWorld() {
        return spawn.get(0).getWorld();
    }
    public Game getGame() {
        return game;
    }
    /* State */
    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }
    public static void deleteWorldFiles(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i = 0; i< files.length;i++) {
                if(files[i].isDirectory()) {
                    deleteWorldFiles(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }
    public static void copyWorld(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if (!target.exists())
                        target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                      out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
