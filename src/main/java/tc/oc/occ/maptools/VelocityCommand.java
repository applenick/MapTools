package tc.oc.occ.maptools;

import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class VelocityCommand extends BaseCommand {

  @CommandAlias("velocity|vel")
  @Description("Apply velocity to a player")
  @CommandPermission("occ.maptools.velocity")
  @CommandCompletion("* * * @players")
  @Syntax("[x] [y] [z] (player)")
  public void velocity(
      Audience viewer,
      Player sender,
      double x,
      double y,
      double z,
      @Optional OnlinePlayer targetPlayer) {
    Player target =
        (targetPlayer != null && sender.hasPermission("occ.maptools.velocity.others")
            ? targetPlayer.getPlayer()
            : sender);
    boolean self = target.equals(sender);
    Vector velocity = new Vector(x, y, z);
    TextComponent.Builder message = text().append(text("Applying Velocity"));
    if (!self) {
      message.append(space()).append(text(target.getDisplayName()));
    }
    message
        .append(text(" ("))
        .append(text(String.format("%.2f", velocity.getX()), getVelocityColor(velocity.getX())))
        .append(text(", "))
        .append(text(String.format("%.2f", velocity.getY()), getVelocityColor(velocity.getY())))
        .append(text(", "))
        .append(text(String.format("%.2f", velocity.getZ()), getVelocityColor(velocity.getZ())))
        .append(text(")"))
        .color(NamedTextColor.GRAY)
        .build();
    viewer.sendMessage(message.build());
    target.setVelocity(velocity);
  }

  private TextColor getVelocityColor(double value) {
    return value > 0 ? NamedTextColor.GREEN : NamedTextColor.RED;
  }
}
