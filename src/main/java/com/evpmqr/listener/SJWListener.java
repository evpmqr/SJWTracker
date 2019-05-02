package com.evpmqr.listener;

import com.evpmqr.App;
import com.evpmqr.data.DataHandler;
import com.evpmqr.objects.User;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class SJWListener extends ListenerAdapter {
    private DataHandler dataHandler;

    public SJWListener(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if (message.contains("!leaderboard")) {
            List<User> list = dataHandler.getUsers().getUserList();
            list.sort((o1, o2) -> o2.getSjwPoints() - o1.getSjwPoints());

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(i + 1).append(". ").append(list.get(i).getName()).append(" Points: ").append(list.get(i).getSjwPoints()).append("\n");
            }
            sendMessage(stringBuilder.toString(), event);
        } else if (message.contains("!help")) {
            sendMessage("!<name> <message> <+/- 1>", event);
        } else if (message.startsWith("!")) {
            User user = dataHandler.fetchUser(event.getAuthor().getId());
            if (user == null) return;

            if (user.isAdmin() && message.equalsIgnoreCase("!updateNormie")) {
                updateNormieRank();
            } else if (user.canVote() || user.isAdmin()) {
                increment(event.getAuthor().getId(), message);
                user.setVotesLeft(user.getVotesLeft() - 1);
                // updateNormieRank();
            } else {
                sendMessage("You have no more sjw votes left.", event);
            }
        }
    }

    private void updateNormieRank() {
        Guild guild = App.getGuild();
        Role normie = guild.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("sjw")).findFirst().orElse(null);
        if (normie != null) {
            guild.getMembersWithRoles(normie).forEach(member -> {
                try {
                    guild.getController().removeSingleRoleFromMember(member, normie).submit().get(10, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            });

            List<User> collect = dataHandler.getUsers().getUserList()
                    .stream()
                    .sorted(Comparator.comparingInt(User::getSjwPoints).reversed())
                    .collect(Collectors.toList());

            Optional.ofNullable(collect.get(0)).ifPresent(user -> guild.getController().addRolesToMember(guild.getMemberById(user.getId()), normie).submit());
        }
        dataHandler.saveData();
    }

    private void increment(String id, String message) {
        // TODO
        String name = message.substring(1, message.indexOf(' ')).toLowerCase();
        String op = (message.contains("+")) ? "+" : "-";

        String comment = message.substring(message.indexOf(' ') + 1, message.indexOf(op));

        int amount = (op.equals("+")) ? 1 : -1;

        System.out.println(name);
        System.out.println(comment);
        System.out.println(amount);

        User user = dataHandler.fetchUser(name);
        if (user != null) {
            System.out.println(user.getName());
            user.setSjwPoints(user.getSjwPoints() + amount);
            dataHandler.saveData();
        }
    }

    private void sendMessage(String message, GuildMessageReceivedEvent event) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append(message);
        event.getChannel().sendMessage(messageBuilder.build()).queue();
    }
}
