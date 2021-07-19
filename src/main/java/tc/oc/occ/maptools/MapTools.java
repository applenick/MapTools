package tc.oc.occ.maptools;

import co.aikar.commands.BukkitCommandManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public class MapTools extends JavaPlugin {

  private BukkitCommandManager commands;
  private BukkitAudiences audience;

  @Override
  public void onEnable() {
    this.commands = new BukkitCommandManager(this);
    this.audience = BukkitAudiences.create(this);

    commands
        .getCommandContexts()
        .registerIssuerOnlyContext(Audience.class, c -> audience.sender(c.getSender()));

    commands.registerCommand(new VelocityCommand());
  }
}
